package cn.realai.online.core.service.impl;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.realai.online.core.bo.ExperimentBO;
import cn.realai.online.common.RedisKeyPrefix;
import cn.realai.online.core.dao.ExperimentDao;
import cn.realai.online.core.entity.Experiment;
import cn.realai.online.core.service.ExperimentService;
import cn.realai.online.tool.redis.RedisClientTemplate;

import java.util.List;

@Service
public class ExperimentServiceImpl implements ExperimentService {

	private static Logger logger = LoggerFactory.getLogger(ExperimentServiceImpl.class);

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

	@Override
	public List<ExperimentBO> findList(Experiment experiment) {
		List<Experiment> list = experimentDao.findList(experiment);
		List<ExperimentBO> result = JSON.parseArray(JSON.toJSONString(list), ExperimentBO.class);
		//BeanUtilsBean.copyProperties(list,result);
		return result;
	}

}
