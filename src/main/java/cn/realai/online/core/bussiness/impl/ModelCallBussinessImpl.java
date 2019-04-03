package cn.realai.online.core.bussiness.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.realai.online.common.Constant;
import cn.realai.online.core.bo.ExperimentBO;
import cn.realai.online.core.bo.TrainResultRedisKey;
import cn.realai.online.core.bo.model.BatchRequestBase;
import cn.realai.online.core.bussiness.ModelCallBussiness;
import cn.realai.online.core.entity.BatchRecord;
import cn.realai.online.core.entity.Experiment;
import cn.realai.online.core.entity.MLock;
import cn.realai.online.core.entity.VariableData;
import cn.realai.online.core.service.BatchRecordService; 
import cn.realai.online.core.service.ExperimentService;
import cn.realai.online.core.service.MLockService;
import cn.realai.online.core.service.VariableDataService;
import cn.realai.online.tool.modelcallthreadpool.BaseBatchTask;
import cn.realai.online.tool.modelcallthreadpool.BatchTaskOfCombo;
import cn.realai.online.tool.modelcallthreadpool.BatchTaskOfDone;
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

    @Autowired
    private MLockService mLockService;
    
    /*
     * 处理每日跑批任务
     */
    @Override
    public void runBatchDaily(Long experimentId, String redisKey, String type, String date) {
    	Long batchId = getBatchId(experimentId, date);
        BaseBatchTask bbt;
        
        if (BatchRequestBase.TYPE_PERSONALCOMBORESULTSET.equals(type)) {
        	bbt = new BatchTaskOfCombo(experimentId, batchId, redisKey);
        } else if (BatchRequestBase.TYPE_PERSONALHETRORESULTSET.equals(type)) {
        	bbt = new BatchTaskOfHetro(experimentId,batchId, redisKey);
        } else if (BatchRequestBase.TYPE_PERSONALHOMORESULTSET.equals(type)) {
        	bbt = new BatchTaskOfHomo(experimentId,batchId, redisKey);
        } else if (BatchRequestBase.TYPE_PERSONALINFORMATION.equals(type)) {
        	bbt = new BatchTaskOfPersonalInfo(experimentId,batchId, redisKey);
        } else if (BatchRequestBase.TYPE_PSICHECKRESULT.equals(type)) {
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
	public void runBatchOffline(Long experimentId, String redisKey, String type, String downUrl, 
			Long batchId, Boolean done) {
    	BaseBatchTask bbt;
    	if (BatchRequestBase.TYPE_PERSONALCOMBORESULTSET.equals(type)) {
        	bbt = new BatchTaskOfCombo(experimentId, batchId, redisKey);
        } else if (BatchRequestBase.TYPE_PERSONALHETRORESULTSET.equals(type)) {
        	bbt = new BatchTaskOfHetro(experimentId, batchId, redisKey);
        } else if (BatchRequestBase.TYPE_PERSONALHOMORESULTSET.equals(type)) {
        	bbt = new BatchTaskOfHomo(experimentId, batchId, redisKey);
        } else if (BatchRequestBase.TYPE_PERSONALINFORMATION.equals(type)) {
        	bbt = new BatchTaskOfPersonalInfo(experimentId, batchId, redisKey);
        } else if (BatchRequestBase.TYPE_DONE.equals(type)) {
        	bbt = new BatchTaskOfDone(experimentId, batchId, done);
        } else if (BatchRequestBase.TYPE_DOENURL.equals(type)) {
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
            vd.setDelete(VariableData.DELETE_NO);
        }

        int ret = variableDataService.insertVariableDataList(vdList);

        //修改实验的预处理状态
        ret = experimentService.maintainPreprocessStatus(experimentId, Experiment.PREFINISH_YES, "");

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
    public void trainErrorDealWith(Long experimentId, String errMsg, String task) {
    	//释放掉锁
    	if (experimentId != null ) {
            MLock mlock = mLockService.getLock(experimentId, MLock.MLOCK_TYPE_TRAIN);
            if (mlock != null) {
            	mlock.unLock();
            }
    	}
    	
    	if (Constant.COMMAND_PREPROCESS.equals(task)) { //预处理
    		experimentService.maintainPreprocessStatus(experimentId, Experiment.PREFINISH_ERROR, errMsg);
        } else if (Constant.COMMAND_TRAIN.equals(task) || Constant.COMMAND_SECOND_TRAIN.equals(task)) { //训练
        	experimentService.maintainErrorMsg(experimentId, Experiment.STATUS_TRAINING_ERROR, errMsg);
        } 
    }

	@Override
	public void batchErrorDealWith(Long batchId, String errorMsg) {
		if (batchId != null) {
			batchRecordService.maintainErrorMsg(batchId, errorMsg, BatchRecord.BATCH_STATUS_ERROR);
		}
	}

}

