package cn.realai.online.calculation.impl;

import org.springframework.stereotype.Service;

import cn.realai.online.calculation.TrainService;
import cn.realai.online.calculation.requestbo.PreprocessRequestBO;
import cn.realai.online.core.bo.ExperimentBO;
import cn.realai.online.util.ConvertJavaBean;

@Service
public class TrainServiceImpl implements TrainService {

	@Override
	public void preprocess(ExperimentBO experimentBO) {
		PreprocessRequestBO prbo = new PreprocessRequestBO();
		ConvertJavaBean.convertJavaBean(prbo, experimentBO);
		
	}
	
}
