package cn.realai.online.core.bussiness.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.realai.online.core.bo.ExperimentBO;
import cn.realai.online.core.bussiness.ExperimentBussiness;
import cn.realai.online.calculation.TrainService;
import cn.realai.online.core.service.ExperimentService;

@Service
public class ExperimentBussinessImpl implements ExperimentBussiness {
	
	@Autowired
	private ExperimentService experimentService;
	
	@Autowired
	private TrainService trainService;

	public int modeling(long id) {
		
		//获取训练锁
		
		
		//查询实验记录数据
		ExperimentBO experimentBO = experimentService.selectExperimentById(id);
		
		//调用python
		trainService.preprocess(experimentBO); 
		
		return 0;
	}

	
	
}
