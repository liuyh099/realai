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
import cn.realai.online.util.ConvertJavaBean;

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
		Experiment experiment = redisClientTemplate.get(getExperimentRedisKey(id), ExperimentBO.class);
		if (experiment == null) {
			experiment = experimentDao.selectExperimentById(id);
			//设置缓存
		}
		if (experiment == null) {
			return null;
		}
		//封装成BO		
		ExperimentBO experimentBO = convertBO(experiment);
		return experimentBO;
	}
	
	/*
	 * 转换成bo
	 */
	private ExperimentBO convertBO(Experiment experiment) {
		ExperimentBO experimentBO = new ExperimentBO();
		ConvertJavaBean.convertJavaBean(experimentBO, experiment);
		return experimentBO;
	}

	@Override
	public List<ExperimentBO> findList(Experiment experiment) {
		List<Experiment> list = experimentDao.findList(experiment);
		List<ExperimentBO> result = JSON.parseArray(JSON.toJSONString(list), ExperimentBO.class);
		//BeanUtilsBean.copyProperties(list,result);
		return result;
	}

	/*
     * 预处理完成，修改与处理结果
     * @param experimentId 实验id
     * @param 
     */
	@Override
	public int updatePreprocessStatus(Long experimentId, int preFinishStatus) {
		int ret = experimentDao.updatePreprocessStatus(experimentId, preFinishStatus);
		return ret;
	}

	/*
	 * 修改实验的状态
	 * @param experimentId
	 * @param statusTraining
	 * @return
	 */
	@Override
	public int updateExperimentStatus(long experimentId, int status) {
		int ret = experimentDao.updateExperimentStatus(experimentId, status);
		return ret;
	}

}
