package cn.realai.online.core.bussiness.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.realai.online.core.bo.ExperimentBO;
import cn.realai.online.core.bo.TrainResultRedisKey;
import cn.realai.online.core.bussiness.ModelCallBussiness;
import cn.realai.online.core.entity.Experiment;
import cn.realai.online.core.entity.VariableData;
import cn.realai.online.core.service.ExperimentService;
import cn.realai.online.core.service.VariableDataService;
import cn.realai.online.tool.redis.RedisClientTemplate;

/**
 * 
 * @author lyh
 */
@Service
public class ModelCallBussinessImpl implements ModelCallBussiness{

	private static Logger logger = LoggerFactory.getLogger(ModelCallBussinessImpl.class);
	
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	
	@Autowired
	private ExperimentService experimentService;
	
	@Autowired
	private VariableDataService variableDataService;
	
	/*
	 * 处理每日跑批任务
	 */
	@Override
	public void runBatchDaily(Long experimentId, String fileAddress) {
		
	}

	/*
	 * 预处理回调
	 */
	@Override
	public int preprocessCallback(Long experimentId, String redisKey) {
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
		}
		
		int ret = variableDataService.insertVariableDataList(vdList);
		
		//修改实验的预处理状态
		ret = experimentService.updatePreprocessStatus(experimentId, Experiment.PREFINISH_YES);
		
		redisClientTemplate.delete(redisKey);
		
		return ret;
	}

	/*
	 * 模型训练回调
	 */
	@Override
	public void trainCallback(Long experimentId, TrainResultRedisKey redisKey) {
		
	}

}

