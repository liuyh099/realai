package cn.realai.online.calculation.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.realai.online.calculation.TrainService;
import cn.realai.online.calculation.requestbo.PreprocessRequestBO;
import cn.realai.online.calculation.requestbo.TrainRequestBO;
import cn.realai.online.common.Constant;
import cn.realai.online.common.config.Config;
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
		PreprocessRequestBO prbo = new PreprocessRequestBO(Constant.TASK_PREPROCESS);
		ConvertJavaBean.convertJavaBean(prbo, experiment);
		String url = config.getUrl();
		String ret = HttpUtil.postRequest(url, JSON.toJSONString(prbo), String.class);
		if (ret == null) {
			throw new RuntimeException("TrainServiceImpl preprocess. 调用python预处理接口失败. prbo{}" + JSON.toJSONString(prbo));
		}
	}

	/*
	 * 训练
	 */
	@Override
	public int training(Experiment experiment, Long oldEid, List<VariableData> homoList, List<VariableData> hetroList) {
		TrainRequestBO trbo = new TrainRequestBO();
		ConvertJavaBean.convertJavaBean(trbo, experiment);
		trbo.setOldExperimentId(oldEid);
		trbo.setDeleteColumnsHomo(getVariableDataName(homoList));
		trbo.setDeleteColumnsHetero(getVariableDataName(hetroList));
		String url = config.getUrl();
		/*String ret = HttpUtil.postRequest(url, JSON.toJSONString(trbo), String.class);
		if (ret == null) {
			throw new RuntimeException("TrainServiceImpl preprocess. 调用python预处理接口失败. prbo{}" + JSON.toJSONString(trbo));
		}*/
		return 0;
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
	
}
