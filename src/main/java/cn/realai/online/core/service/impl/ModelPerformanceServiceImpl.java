package cn.realai.online.core.service.impl;

import java.util.List;

import cn.realai.online.core.dao.ModelPerfomanceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.realai.online.core.entity.ModelPerformance;
import cn.realai.online.core.service.ModelPerformanceService;

@Service
public class ModelPerformanceServiceImpl implements ModelPerformanceService {

	@Autowired
	private ModelPerfomanceDao modelPerfomanceDao;

	@Override
	public void insertList(List<ModelPerformance> modelPerformanceList) {
		
	}

	@Override
	public List<ModelPerformance> selectList(Long modelId) {
		return modelPerfomanceDao.selectList(modelId);
	}
}
