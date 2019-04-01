package cn.realai.online.calculation.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import ch.qos.logback.classic.Logger;
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
import cn.realai.online.core.service.MLockService;
import cn.realai.online.util.ConvertJavaBean;
import cn.realai.online.util.HttpUtil;

@Service
public class TrainServiceImpl implements TrainService {

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
        if (!getLock(experiment.getId())) {
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
	public int experimentDeploy(Long experimentId, Long originalId) {
		DeployRequestBO drbo = new DeployRequestBO();
		drbo.setModelId(experimentId);
		//drbo.setOriginalId(originalId);
        String url = config.getModelPublish();
        String ret = HttpUtil.postRequest(url, JSON.toJSONString(drbo));
        if (ret == null) {
            throw new RuntimeException("TrainServiceImpl preprocess. 调用python发布接口失败. drbo{}" + JSON.toJSONString(drbo));
        }
        return 1;
	}

	@Override
	public int runBatchOfOffline(BatchRecord batchRecord) {
		//获取锁
    	if (!getLock(batchRecord.getExperimentId())) {
    		return -1;
    	}
		
		BatchOfOfflineRequestBO boorbo = new BatchOfOfflineRequestBO();
		boorbo.setBatchId(batchRecord.getId());
		boorbo.setCommand(Constant.COMMAND_BATCH);
		boorbo.setModelId(batchRecord.getExperimentId());
		boorbo.setModelId(2L);
		if (batchRecord.getXtableHeterogeneousDataSource() != null) {
			boorbo.setXtableHeterogeneousDataSource("/" + batchRecord.getXtableHeterogeneousDataSource());
			//boorbo.setXtableHeterogeneousDataSource(batchRecord.getXtableHeterogeneousDataSource());
		}
		if (batchRecord.getXtableHomogeneousDataSource() != null) {
			boorbo.setXtableHomogeneousDataSource("/" + batchRecord.getXtableHomogeneousDataSource());
			//boorbo.setXtableHomogeneousDataSource(batchRecord.getXtableHomogeneousDataSource());
		}
		if (batchRecord.getYtableDataSource() != null) {
			//boorbo.setYtableDataSource(batchRecord.getYtableDataSource());
			boorbo.setYtableDataSource("/" + batchRecord.getYtableDataSource());
		}
		String ret = HttpUtil.postRequest(config.getModelOfflineBatch(), JSON.toJSONString(boorbo));
        if (ret == null) {
            throw new RuntimeException("TrainServiceImpl preprocess. 调用python预处理接口失败. prbo{}" + JSON.toJSONString(boorbo));
        }
		return 1;
	}

	public boolean getLock(Long experimentId) {
		//获取锁
		MLock mLock = mLockService.getLock(experimentId);
    	return mLock.tryLock();
	}
	
	@Override
	public int deleteExperiment(Long experimentId) {
		DeleteExperimentRequestBO derbo = new DeleteExperimentRequestBO();
		derbo.setModelId(experimentId);
        String ret = HttpUtil.postRequest(config.getModelDrop(), JSON.toJSONString(derbo));
        if (ret == null) {
            throw new RuntimeException("TrainServiceImpl deleteExperiment. 调用python删除接口失败. derbo{}" + JSON.toJSONString(derbo));
        }
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
	public String realTimeForecast(Long modelId, String jsonData) {
		RealTimeRequestBO rtrbo = new RealTimeRequestBO();
		rtrbo.setModelId(modelId);
		rtrbo.setJsonData(jsonData);
		/*String url = config.getPythonUrl();
		String ret = HttpUtil.postRequest(url, JSON.toJSONString(rtrbo));
        if (ret == null) {
            throw new RuntimeException("TrainServiceImpl realTimeForecast. 调用python线上实时接口失败. rtrbo{}" + JSON.toJSONString(rtrbo));
        }
		return ret;*/
		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "OK";
	}
}
