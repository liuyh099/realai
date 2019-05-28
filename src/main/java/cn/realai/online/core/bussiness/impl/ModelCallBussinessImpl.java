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
import cn.realai.online.core.bussiness.ModelCallBussiness;
import cn.realai.online.core.entity.BatchExecutionTask;
import cn.realai.online.core.entity.BatchRecord;
import cn.realai.online.core.entity.DailyBatchRequest;
import cn.realai.online.core.entity.DeployInfo;
import cn.realai.online.core.entity.Experiment;
import cn.realai.online.core.entity.MLock;
import cn.realai.online.core.entity.VariableData;
import cn.realai.online.core.service.BatchExecutionTaskService;
import cn.realai.online.core.service.BatchRecordService;
import cn.realai.online.core.service.DeployInfoService;
import cn.realai.online.core.service.ExperimentService;
import cn.realai.online.core.service.MLockService;
import cn.realai.online.core.service.VariableDataService;
import cn.realai.online.tool.batchtaskthreadpool.DailyBatchTask;
import cn.realai.online.tool.batchtaskthreadpool.DailyBatchTaskQueue;
import cn.realai.online.tool.redis.RedisClientTemplate;
import cn.realai.online.tool.traincallbackthreadpool.BaseBatchTask;
import cn.realai.online.tool.traincallbackthreadpool.BatchTaskOfDone;
import cn.realai.online.tool.traincallbackthreadpool.BatchTaskOfPSI;
import cn.realai.online.tool.traincallbackthreadpool.BatckTaskOfThousandPeople;
import cn.realai.online.tool.traincallbackthreadpool.ModelCallPool;
import cn.realai.online.tool.traincallbackthreadpool.TrainTaskStageOne;
import cn.realai.online.tool.traincallbackthreadpool.TrainTaskStageTwo;

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
    
    @Autowired
    private DeployInfoService deployInfoService;
    
    @Autowired
    private BatchExecutionTaskService batchExecutionTaskService;
    
    /*
     * 处理每日跑批任务
     */
    @Override
    public void runBatchDaily(List<DailyBatchRequest> dbrList) {
        for (DailyBatchRequest dbr : dbrList) {
        	DailyBatchTask dbt = new DailyBatchTask(dbr.getModelId(), dbr.getBatchDate(), dbr.getXtableHetro(),
        			dbr.getXtableHomo(), dbr.getYtable());
        	DailyBatchTaskQueue.queue.execute(dbt);
        }
    }
    
    /*
     * 批次处理结果信息处理
     */
    @Override
	public void runBatchOffline(BatchExecutionTask bet) {
    	
    	//记录处理的小批次任务
    	batchExecutionTaskService.insertBatchExecutionTask(bet);
    	
    	BaseBatchTask bbt;
    	if (BatchExecutionTask.TYPE_DONE.equals(bet.getType())) {
        	bbt = new BatchTaskOfDone(bet);
        } else if (BatchExecutionTask.TYPE_PSICHECKRESULT.equals(bet.getType())) {
        	bbt = new BatchTaskOfPSI(bet);
        } else if (BatchExecutionTask.TYPE_THOUSANDPEOPLE_INFORMATION.equals(bet.getType())) {
        	bbt = new BatckTaskOfThousandPeople(bet);
        } else if (BatchExecutionTask.TYPE_DOENURL.equals(bet.getType())) {
        	if (bet.getDownUrl() != null && !"".equals(bet.getDownUrl())) {
        		batchRecordService.maintainDownUrl(bet.getBatchId(), bet.getDownUrl());
        	}
        	return ;
        } else {
        	logger.error("BatchTask run. 跑批数据类型不能识别. bet{}", JSON.toJSONString(bet));
        	return ;
        }
    	logger.info("ModelCallBussinessImpl runBatchOffline bet{}", JSON.toJSONString(bet));
    	
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
        variableDataService.deleteVariableDataByExperimentId(experimentId);
        variableDataService.insertVariableDataList(vdList);

        //修改实验的预处理状态
        int ret = experimentService.maintainPreprocessStatus(experimentId, Experiment.PREFINISH_YES, "");

        redisClientTemplate.delete(redisKey);
        logger.info("ModelCallBussinessImpl preprocessCallback. 预处正常结束， experimentId{}, redisKey{} ", experimentId, redisKey);
        return ret;
    }

    /*
     * 模型训练回调
     */
    @Override
    public void trainCallback(Long experimentId, TrainResultRedisKey redisKey, Integer stage) {
    	if (stage == 1) { //训练回调的第一个步骤
    		TrainTaskStageOne stageOne = new TrainTaskStageOne(experimentId, redisKey);
    		ModelCallPool.modelCallPool.execute(stageOne);
    	} else if (stage == 2) { //训练回调的第二个步骤   解释性数据
    		TrainTaskStageTwo stageTwo = new TrainTaskStageTwo(experimentId, redisKey);
    		ModelCallPool.modelCallPool.execute(stageTwo);
    	} else {
    		throw new RuntimeException("ModelCallBussinessImpl trainCallback.模型训练回调错误,stage为null");
    	}
    }

    /*
     * 训练错误处理
     */
    @Override
    public void trainErrorDealWith(Long experimentId, String errMsg, String task) {
    	//释放掉锁
    	if (experimentId != null ) {
            MLock mlock = mLockService.getLock(MLock.TRAIN_MLOCK_LOCK, MLock.TRAIN_MLOCK_PREFIX, experimentId);
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

    /*
     * 批次错误处理
     */
	@Override
	public void batchErrorDealWith(Long batchId, String errorMsg) {
		if (batchId != null) {
			logger.info("ModelCallBussinessImpl batchErrorDealWith, batchId{}, errorMsg{}", batchId, errorMsg);
			BatchRecord batchRecord = new BatchRecord();
			batchRecord.setId(batchId);
			batchRecord = batchRecordService.getByEntity(batchRecord);
			MLock mLock;
			if (batchRecord.getBatchType() == BatchRecord.BATCH_TYPE_DAILY) {
				mLock = mLockService.getLock(MLock.ONLINE_MLOCK_LOCK, MLock.BATCH_MLOCK_PREFIX, batchId);
			} else if (batchRecord.getBatchType() == BatchRecord.BATCH_TYPE_OFFLINE) {
				mLock = mLockService.getLock(MLock.TRAIN_MLOCK_LOCK, MLock.BATCH_MLOCK_PREFIX, batchId);
			} else {
				mLock = null;
			}
			if (mLock != null) {
				boolean b = mLock.unLock();
				logger.info("ModelCallBussinessImpl batchErrorDealWith unLock, batchId{}, b{}", batchId, b);
			}
		}
		
		if (batchId != null) {
			batchRecordService.maintainErrorMsg(batchId, errorMsg, BatchRecord.BATCH_STATUS_ERROR);
		}
	}

	/**
	 * 部署服务模型
	 */
	@Override
	public int deployServiceModel(DeployInfo deployInfo) {
		DeployInfo oldDeployInfo = deployInfoService.selectDeployInfoById(deployInfo.getServiceId());
		int ret;
		if (oldDeployInfo == null) {
			ret = deployInfoService.insertDeployInfo(deployInfo);
		} else {
			ret = deployInfoService.updateDeployInfo(deployInfo);
		}
		return ret;
	}

}
