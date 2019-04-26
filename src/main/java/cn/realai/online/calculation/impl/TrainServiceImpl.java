package cn.realai.online.calculation.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import cn.realai.online.calculation.TrainService;
import cn.realai.online.calculation.requestbo.BatchOfOfflineRequestBO;
import cn.realai.online.calculation.requestbo.DeleteExperimentRequestBO;
import cn.realai.online.calculation.requestbo.DeployRequestBO;
import cn.realai.online.calculation.requestbo.PreprocessRequestBO;
import cn.realai.online.calculation.requestbo.RealTimeRequestBO;
import cn.realai.online.calculation.requestbo.TrainRequestBO;
import cn.realai.online.common.Constant;
import cn.realai.online.common.config.Config;
import cn.realai.online.core.entity.BatchRecord;
import cn.realai.online.core.entity.Experiment;
import cn.realai.online.core.entity.MLock;
import cn.realai.online.core.entity.VariableData;
import cn.realai.online.core.query.realtime.RealTimeData;
import cn.realai.online.core.service.MLockService;
import cn.realai.online.util.ConvertJavaBean;
import cn.realai.online.util.HttpUtil;

@Service
public class TrainServiceImpl implements TrainService {

	private static Logger logger = LoggerFactory.getLogger(TrainServiceImpl.class);
	
    @Autowired
    private Config config;

    @Autowired
    private MLockService mLockService;
    
    /*
     * 预处理
     */
    @Override
    public void preprocess(Experiment experiment) {
        PreprocessRequestBO prbo = new PreprocessRequestBO();
        prbo.setExperimentId(experiment.getId());
        if (experiment.getXtableHeterogeneousDataSource() != null) {
        	prbo.setXtableHeterogeneousDataSource("/" + experiment.getXtableHeterogeneousDataSource());
        }
        if (experiment.getXtableHomogeneousDataSource() != null) {
        	prbo.setXtableHomogeneousDataSource("/" + experiment.getXtableHomogeneousDataSource());
        }
        if (experiment.getXtableMeaningDataSource() != null) {
        	prbo.setXtableMeaningDataSource("/" + experiment.getXtableMeaningDataSource());
        }
        if (experiment.getYtableDataSource() != null) {
        	prbo.setYtableDataSource("/" + experiment.getYtableDataSource());
        }
        String url = config.getPythonUrl();
        String ret = HttpUtil.postRequest(url, JSON.toJSONString(prbo));
        if (ret == null) {
            throw new RuntimeException("TrainServiceImpl preprocess. 调用python预处理接口失败. prbo{}" + JSON.toJSONString(prbo));
        }
    }

	/*
	 * 训练
	 */
	@Override
	public int training(Experiment experiment, Long oldEid, List<VariableData> homoList, 
			List<VariableData> hetroList, int delOrAdd) {
		//获取训练锁
        if (!getLock(MLock.TRAIN_MLOCK_LOCK, MLock.TRAIN_MLOCK_PREFIX, experiment.getId())) {
            return -1;
        }
		
		if (experiment.getXtableHeterogeneousDataSource() != null) {
        	experiment.setXtableHeterogeneousDataSource("/" + experiment.getXtableHeterogeneousDataSource());
        }
        if (experiment.getXtableHomogeneousDataSource() != null) {
        	experiment.setXtableHomogeneousDataSource("/" + experiment.getXtableHomogeneousDataSource());
        }
        if (experiment.getXtableMeaningDataSource() != null) {
        	experiment.setXtableMeaningDataSource("/" + experiment.getXtableMeaningDataSource());
        }
        if (experiment.getYtableDataSource() != null) {
        	experiment.setYtableDataSource("/" + experiment.getYtableDataSource());
        }
		TrainRequestBO trbo = new TrainRequestBO();
		ConvertJavaBean.convertJavaBean(trbo, experiment);
		trbo.setParentId(oldEid);
		trbo.setExperimentId(experiment.getId());
		if (delOrAdd == 2) {
			trbo.setCommand(Constant.COMMAND_TRAIN);
			trbo.setDeleteColumnsHomo(getVariableDataName(homoList));
			trbo.setDeleteColumnsHetero(getVariableDataName(hetroList));
		} else if (delOrAdd == 1) {
			trbo.setCommand(Constant.COMMAND_SECOND_TRAIN);
			trbo.setColumnsHomo(getVariableDataName(homoList));
			trbo.setColumnsHetero(getVariableDataName(hetroList));
		}
		String url = config.getPythonUrl();
		String ret = HttpUtil.postRequest(url, JSON.toJSONString(trbo));
		if (ret == null) {
			throw new RuntimeException("TrainServiceImpl preprocess. 调用python预处理接口失败. prbo{}" + JSON.toJSONString(trbo));
		}
		return 1;
	}
	
	private List<String> getVariableDataName(List<VariableData> list) {
		if (list == null) {
			return null;
		}
		List<String> strList = new ArrayList<String>();
		for (VariableData vd : list) {
			strList.add(vd.getName());
		}
		return strList;
	}

	@Override
	public int experimentDeploy(Long experimentId, Long originalId, String type) {
		DeployRequestBO drbo = new DeployRequestBO();
		drbo.setModelId(experimentId);
		//drbo.setOriginalId(originalId);
		String url = null;
		if ("online".equals(type)) {
			url = config.getModelOnlinePublish();
		} else {
			url = config.getModelOfflinePublish();
		}
        String ret = HttpUtil.postRequest(url, JSON.toJSONString(drbo));
        if (ret == null) {
            throw new RuntimeException("TrainServiceImpl preprocess. 调用python发布接口失败. drbo{}" + JSON.toJSONString(drbo));
        }
        return 1;
	}

	/*
	 * 跑批
	 */
	@Override
	public int runBatchOffLine(BatchRecord batchRecord) {
		//获取锁
    	if (!getLock(MLock.TRAIN_MLOCK_LOCK, MLock.BATCH_MLOCK_PREFIX, batchRecord.getId())) {
    		return -1;
    	}
		BatchOfOfflineRequestBO boorbo = new BatchOfOfflineRequestBO();
		boorbo.setBatchId(batchRecord.getId());
		boorbo.setCommand(Constant.COMMAND_BATCH);
		boorbo.setModelId(batchRecord.getExperimentId());
		if (batchRecord.getXtableHeterogeneousDataSource() != null) {
			boorbo.setXtableHeterogeneousDataSource("/" + batchRecord.getXtableHeterogeneousDataSource());
		}
		if (batchRecord.getXtableHomogeneousDataSource() != null) {
			boorbo.setXtableHomogeneousDataSource("/" + batchRecord.getXtableHomogeneousDataSource());
		}
		if (batchRecord.getYtableDataSource() != null) {
			boorbo.setYtableDataSource("/" + batchRecord.getYtableDataSource());
		}
		String ret = HttpUtil.postRequest(config.getModelOfflineBatch(), JSON.toJSONString(boorbo));
        if (ret == null) {
            throw new RuntimeException("TrainServiceImpl preprocess. 调用python预处理接口失败. prbo{}" + JSON.toJSONString(boorbo));
        }
		return 1;
	}

	/*
	 * 每日跑批
	 */
	@Override
	public int runBatchDaily(BatchRecord batchRecord) {
		//获取锁
    	if (!getLock(MLock.ONLINE_MLOCK_LOCK, MLock.BATCH_MLOCK_PREFIX, batchRecord.getId())) {
    		return -1;
    	}
		BatchOfOfflineRequestBO boorbo = new BatchOfOfflineRequestBO();
		boorbo.setBatchId(batchRecord.getId());
		boorbo.setCommand(Constant.COMMAND_BATCH);
		boorbo.setModelId(batchRecord.getExperimentId());
		boorbo.setXtableHeterogeneousDataSource(batchRecord.getXtableHeterogeneousDataSource());
		boorbo.setXtableHomogeneousDataSource(batchRecord.getXtableHomogeneousDataSource());
		boorbo.setYtableDataSource(batchRecord.getYtableDataSource());
		String ret = HttpUtil.postRequest(config.getModelDailyBatch(), JSON.toJSONString(boorbo));
        if (ret == null) {
            throw new RuntimeException("TrainServiceImpl preprocess. 调用python预处理接口失败. prbo{}" + JSON.toJSONString(boorbo));
        }
		return 1;
	}
	
	private boolean getLock(String lockKey, String lockValue, long id) {
		//获取锁
		MLock mLock = mLockService.getLock(lockKey, lockValue, id);
		if (mLock == null) {
			return false;
		}
    	return mLock.tryLock();
	}
	
	@Override
	public int deleteExperiment(Long experimentId) {
		DeleteExperimentRequestBO derbo = new DeleteExperimentRequestBO();
		derbo.setExperimentId(experimentId);
        String ret = HttpUtil.postRequest(config.getExperimentDrop(), JSON.toJSONString(derbo));
        if (ret == null) {
            throw new RuntimeException("TrainServiceImpl deleteExperiment. 调用python删除接口失败. derbo{}" + JSON.toJSONString(derbo));
        }
        //删除python实验成功之后在释放训练所，如果当前训练的实验是当前删除的实验，则释放训练锁，如不是则释放动作并不生效
        MLock mLock = mLockService.getLock(MLock.TRAIN_MLOCK_LOCK, MLock.BATCH_MLOCK_PREFIX, experimentId);
        mLock.unLock();
		return 1;
	}
	
	public int deleteModel(Long modelId) {
		DeleteExperimentRequestBO derbo = new DeleteExperimentRequestBO();
		derbo.setModelId(modelId);
        String ret = HttpUtil.postRequest(config.getModelDrop(), JSON.toJSONString(derbo));
        if (ret == null) {
            throw new RuntimeException("TrainServiceImpl deleteExperiment. 调用python删除接口失败. derbo{}" + JSON.toJSONString(derbo));
        }
		return 1;
	}

	/**
	 * 线上实时算法
	 */
	@Override
	public String realTimeForecast(RealTimeData realTimeData) {
		RealTimeRequestBO rtrbo = new RealTimeRequestBO();
		String url = config.getRealtimeUrl();
		logger.info("TrainServiceImpl realTimeForecast, 线上实时接口调用 url{}" + url);
		String ret = HttpUtil.postRequest(url, JSON.toJSONString(realTimeData, SerializerFeature.WriteMapNullValue));
        if (ret == null) {
            throw new RuntimeException("TrainServiceImpl realTimeForecast. 调用python线上实时接口失败. rtrbo{}" + JSON.toJSONString(rtrbo));
        }
		return ret;
	}

}
