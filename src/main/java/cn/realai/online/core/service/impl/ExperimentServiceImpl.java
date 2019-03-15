package cn.realai.online.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.realai.online.core.bo.ExperimentBO;
import cn.realai.online.common.RedisKeyPrefix;
import cn.realai.online.core.controller.ExperimentController;
import cn.realai.online.core.dao.ExperimentDao;
import cn.realai.online.core.entity.Experiment;
import cn.realai.online.core.service.ExperimentService;
import cn.realai.online.tool.redis.RedisClientTemplate;

@Service
public class ExperimentServiceImpl implements ExperimentService {

	private static Logger logger = LoggerFactory.getLogger(ExperimentController.class);

	@Autowired
	private ExperimentDao experimentDao;
	
	@Autowired
	private RedisClientTemplate redisClientTemplate;

	private String getExperimentRedisKey(long id) {
		return RedisKeyPrefix.EXPERIMENT_PREFIX + id;
	}
	
	@Override
	public ExperimentBO selectExperimentById(long id) {
		ExperimentBO experimentBO = redisClientTemplate.get(getExperimentRedisKey(id), ExperimentBO.class);
		if (experimentBO != null) {
			return experimentBO;
		}
		
		Experiment experiment = experimentDao.selectExperimentById(id);
		if (experiment == null) {
			return null;
		}
		//设置缓存
		
		
		
		return experimentBO;
	}
	
}
