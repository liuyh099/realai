package cn.realai.online.calculation.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.realai.online.calculation.TrainService;
import cn.realai.online.calculation.requestbo.BatchOfOfflineRequestBO;
import cn.realai.online.calculation.requestbo.DeleteExperimentRequestBO;
import cn.realai.online.calculation.requestbo.DeployRequestBO;
import cn.realai.online.calculation.requestbo.PreprocessRequestBO;
import cn.realai.online.calculation.requestbo.TrainRequestBO;
import cn.realai.online.common.Constant;
import cn.realai.online.common.config.Config;
import cn.realai.online.core.entity.BatchRecord;
import cn.realai.online.core.entity.Experiment;
import cn.realai.online.core.entity.VariableData;
import cn.realai.online.util.ConvertJavaBean;
import cn.realai.online.util.HttpUtil;

@Service
public class TrainServiceImpl implements TrainService {

    @Autowired
    private Config config;

    /*
     * 预处理
     */
    @Override
    public void preprocess(Experiment experiment) {
        PreprocessRequestBO prbo = new PreprocessRequestBO();
        prbo.setExperimentId(experiment.getId());
        prbo.setXtableHeterogeneousDataSource(experiment.getXtableHeterogeneousDataSource());
        prbo.setXtableHomogeneousDataSource(experiment.getXtableHomogeneousDataSource());
        prbo.setXtableMeaningDataSource(experiment.getXtableMeaningDataSource());
        prbo.setYtableDataSource(experiment.getYtableDataSource());
        String url = config.getUrl();
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
		String url = config.getUrl();
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
		drbo.setExperimentId(experimentId);
		
        String url = config.getUrl();
        String ret = HttpUtil.postRequest(url, experimentId + "");
        if (ret == null) {
            throw new RuntimeException("TrainServiceImpl preprocess. 调用python预处理接口失败. prbo{}" + JSON.toJSONString(drbo));
        }
        return 1;
	}

	@Override
	public int runBatchOfOffline(BatchRecord batchRecord) {
		BatchOfOfflineRequestBO boorbo = new BatchOfOfflineRequestBO();
		boorbo.setBatchId(batchRecord.getId());
		boorbo.setCommand(Constant.COMMAND_BATCH);
		boorbo.setModelId(batchRecord.getExperimentId());
		boorbo.setXtableHeterogeneousDataSource(batchRecord.getXtableHeterogeneousDataSource());
		boorbo.setXtableHomogeneousDataSource(batchRecord.getXtableHomogeneousDataSource());
		boorbo.setYtableDataSource(batchRecord.getYtableDataSource());
		String ret = HttpUtil.postRequest(config.getUrl(), JSON.toJSONString(boorbo));
        if (ret == null) {
            throw new RuntimeException("TrainServiceImpl preprocess. 调用python预处理接口失败. prbo{}" + JSON.toJSONString(boorbo));
        }
		return 1;
	}

	@Override
	public int deleteExperiment(Long experimentId) {
		DeleteExperimentRequestBO derbo = new DeleteExperimentRequestBO();
		derbo.setExperimentId(experimentId);
        String url = config.getUrl();
        String ret = HttpUtil.postRequest(url, experimentId + "");
        if (ret == null) {
            throw new RuntimeException("TrainServiceImpl preprocess. 调用python预处理接口失败. prbo{}" + JSON.toJSONString(derbo));
        }
		return 1;
	}
}
