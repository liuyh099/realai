package cn.realai.online.core.bussiness.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.realai.online.core.bo.BatchRequestStructure;
import cn.realai.online.core.bo.ExperimentBO;
import cn.realai.online.core.bo.TrainResultRedisKey;
import cn.realai.online.core.bussiness.ModelCallBussiness;
import cn.realai.online.core.entity.BatchRecord;
import cn.realai.online.core.entity.Experiment;
import cn.realai.online.core.entity.MLock;
import cn.realai.online.core.entity.VariableData;
import cn.realai.online.core.service.BatchRecordService; 
import cn.realai.online.core.service.ExperimentService;
import cn.realai.online.core.service.VariableDataService;
import cn.realai.online.tool.modelcallthreadpool.BaseBatchTask;
import cn.realai.online.tool.modelcallthreadpool.BatchTaskOfCombo;
import cn.realai.online.tool.modelcallthreadpool.BatchTaskOfHetro;
import cn.realai.online.tool.modelcallthreadpool.BatchTaskOfHomo;
import cn.realai.online.tool.modelcallthreadpool.BatchTaskOfPSI;
import cn.realai.online.tool.modelcallthreadpool.BatchTaskOfPersonalInfo;
import cn.realai.online.tool.modelcallthreadpool.ModelCallPool;
import cn.realai.online.tool.modelcallthreadpool.TrainTask;
import cn.realai.online.tool.redis.RedisClientTemplate;
import cn.realai.online.util.SpringContextUtils;

/**
 * @author lyh
 */
@Service
public class ModelCallBussinessImpl implements ModelCallBussiness {

    private static Logger logger = LoggerFactory.getLogger(ModelCallBussinessImpl.class);

    @Autowired
    private RedisClientTemplate redisClientTemplate;

    @Autowired
    private ExperimentService experimentService;

    @Autowired
    private VariableDataService variableDataService;
    
    @Autowired
    private BatchRecordService batchRecordService;

    /*
     * 处理每日跑批任务
     */
    @Override
    public void runBatchDaily(Long experimentId, String redisKey, String type, String date) {
    	Long batchId = getBatchId(experimentId, date);
        BaseBatchTask bbt;
        
        if (BatchRequestStructure.TYPE_PERSONALCOMBORESULTSET.equals(type)) {
        	bbt = new BatchTaskOfCombo(experimentId, batchId, redisKey);
        } else if (BatchRequestStructure.TYPE_PERSONALHETRORESULTSET.equals(type)) {
        	bbt = new BatchTaskOfHetro(experimentId,batchId, redisKey);
        } else if (BatchRequestStructure.TYPE_PERSONALHOMORESULTSET.equals(type)) {
        	bbt = new BatchTaskOfHomo(experimentId,batchId, redisKey);
        } else if (BatchRequestStructure.TYPE_PERSONALINFORMATION.equals(type)) {
        	bbt = new BatchTaskOfPersonalInfo(experimentId,batchId, redisKey);
        } else if (BatchRequestStructure.TYPE_PSICHECKRESULT.equals(type)) {
        	bbt = new BatchTaskOfPSI(experimentId,batchId, redisKey);
        } else {
        	logger.error("BatchTask run. 跑批数据类型不能识别. type{}, experimentId{}", type, experimentId);
        	return ;
        }
        
        ModelCallPool.modelCallPool.execute(bbt);
    }
    
    private Long getBatchId(Long experimentId, String date) {
		
		BatchRecordService batchRecordService = SpringContextUtils.getBean(BatchRecordService.class);
		
        BatchRecord batchRecord = batchRecordService.getBatchRecordOfDaily(experimentId, date, BatchRecord.BATCH_TYPE_DAILY);
        if (batchRecord == null) {
        	throw new RuntimeException("batchRecord getBatchId. 批次创建错误. eid = " + experimentId + " date =+ " + date);
        }
		return batchRecord.getId();
	}
    
    @Override
	public void runBatchOffline(Long experimentId, String redisKey, String type, String downUrl, Long batchId) {
    	BaseBatchTask bbt;
    	if (BatchRequestStructure.TYPE_PERSONALCOMBORESULTSET.equals(type)) {
        	bbt = new BatchTaskOfCombo(experimentId,batchId, redisKey);
        } else if (BatchRequestStructure.TYPE_PERSONALHETRORESULTSET.equals(type)) {
        	bbt = new BatchTaskOfHetro(experimentId,batchId, redisKey);
        } else if (BatchRequestStructure.TYPE_PERSONALHOMORESULTSET.equals(type)) {
        	bbt = new BatchTaskOfHomo(experimentId,batchId, redisKey);
        } else if (BatchRequestStructure.TYPE_PERSONALINFORMATION.equals(type)) {
        	bbt = new BatchTaskOfPersonalInfo(experimentId,batchId, redisKey);
        } else if (BatchRequestStructure.TYPE_DOENURL.equals(type)) {
        	if (downUrl != null && !"".equals(downUrl)) {
        		batchRecordService.maintainDownUrl(batchId, downUrl);
        	}
        	return ;
        } else {
        	logger.error("BatchTask run. 跑批数据类型不能识别. type{}, experimentId{}", type, experimentId);
        	return ;
        }
    	
    	ModelCallPool.modelCallPool.execute(bbt);
    	
    }

    /*
     * 预处理回调
     */
    @Override
    public int preprocessCallback(Long experimentId, String redisKey) {
    	logger.info("ModelCallBussinessImpl preprocessCallback. 预处开始， experimentId{}, redisKey{} ", experimentId, redisKey);
        ExperimentBO experimentbo = experimentService.selectExperimentById(experimentId);
        if (experimentbo == null) {
            logger.error("ModelCallBussinessImpl preprocessCallback. 预处理实验不存在. experimentId{}, redisKey{}", experimentId, redisKey);
            return 0;
        }
        String str = redisClientTemplate.get(redisKey);
        if (str == null || "".equals(str)) {
            logger.warn("ModelCallBussinessImpl preprocessCallback. 预处理RedisKey为空. experimentId{}, redisKey{}", experimentId, redisKey);
            return 0;
        }

        List<VariableData> vdList = JSON.parseArray(str, VariableData.class);
        for (VariableData vd : vdList) {
            if (experimentId != vd.getExperimentId()) {
                logger.error("ModelCallBussinessImpl preprocessCallback. 预处理结果不属于指定的实验. experimentId{}, redisKey{}, vd{}",
                        experimentId, redisKey, JSON.toJSONString(vd));
                throw new RuntimeException("");
            }
            vd.setDelete(VariableData.DELETE_NO);
        }

        int ret = variableDataService.insertVariableDataList(vdList);

        //修改实验的预处理状态
        ret = experimentService.updatePreprocessStatus(experimentId, Experiment.PREFINISH_YES);

        redisClientTemplate.delete(redisKey);
        logger.info("ModelCallBussinessImpl preprocessCallback. 预处正常结束， experimentId{}, redisKey{} ", experimentId, redisKey);
        return ret;
    }

    /*
     * 模型训练回调
     */
    @Override
    public void trainCallback(Long experimentId, TrainResultRedisKey redisKey) {
        TrainTask trainTask = new TrainTask(experimentId, redisKey);
        ModelCallPool.modelCallPool.execute(trainTask);
    }

    /*
     * 错误处理
     */
    @Override
    public void errorDealWith(Long experimentId, String errMsg) {
    	//释放掉锁
    	if (experimentId != null ) {
            MLock mlock = experimentService.getExperimentTrainMLockInstance(experimentId);
            mlock.unLock();
    	}
    	experimentService.maintainErrorMsg(experimentId, Experiment.STATUS_TRAINING_ERROR, errMsg);
    }

}

