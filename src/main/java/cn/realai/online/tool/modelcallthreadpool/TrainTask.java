package cn.realai.online.tool.modelcallthreadpool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;

import cn.realai.online.core.bo.ExperimentBO;
import cn.realai.online.core.bo.TrainResultRedisKey;
import cn.realai.online.core.entity.ModelPerformance;
import cn.realai.online.core.entity.VariableData;
import cn.realai.online.core.service.ExperimentService;
import cn.realai.online.core.service.ModelPerformanceService;
import cn.realai.online.core.service.VariableDataService;
import cn.realai.online.core.service.impl.ExperimentServiceImpl;
import cn.realai.online.tool.redis.RedisClientTemplate;
import cn.realai.online.util.SpringContextUtils;

/**
 * 模型训练回调任务
 * @author lyh
 */
public class TrainTask implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(TrainTask.class);
	
	private Long experimentId;
	
	private TrainResultRedisKey redisKey;
	
	public TrainTask(Long experimentId, TrainResultRedisKey redisKey) {
		this.experimentId = experimentId;
		this.redisKey = redisKey;
	}
	
	@Override
	@Transactional()
	public void run() {
		//查询实验信息
		ExperimentService experimentService = SpringContextUtils.getBean(ExperimentService.class);
		ExperimentBO experiment = experimentService.selectExperimentById(experimentId);
		if (experiment == null) {
			logger.error("TrainTask run. experiment信息不存在. experimentId{}", experimentId);
		}
		
		//获取Redis操作对象
		RedisClientTemplate redisClientTemplate = SpringContextUtils.getBean(RedisClientTemplate.class);
		
		//解析模型表现
		List<ModelPerformance> modelPerformanceList = analysisModelPerformance(redisClientTemplate.get(redisKey.getModelperformance()));
		ModelPerformanceService modelPerformanceService = SpringContextUtils.getBean(ModelPerformanceService.class);
		modelPerformanceService.insertList(modelPerformanceList);
		
		VariableDataService variableDataService = SpringContextUtils.getBean(VariableDataService.class);
		List<VariableData> vdList = variableDataService.findListByExperimentId(experimentId);
		//Map<String, VariableData> homo = new HashMap<>;
		
	}
	
	private List<ModelPerformance> analysisModelPerformance(String redisValue) {
		if (redisValue == null || "".equals(redisValue)) {
			logger.error("TrainTask analysisModelPerformance. 训练结果没有模型表现数据. experimentId{}", experimentId);
			throw new RuntimeException("训练结果没有模型表现数据experimentId = " + experimentId);
		}
		List<ModelPerformance> mpList = JSON.parseArray(redisValue, ModelPerformance.class);
		for (ModelPerformance mp : mpList) {
			if (mp.getExperimentId() != experimentId.longValue()) {
				logger.error("TrainTask analysisModelPerformance. 训练结果数据错误,训练实验id的结果实验id不相等. experimentId{}, resultId{}", experimentId, mp.getExperimentId());
				throw new RuntimeException("训练结果数据错误,训练实验id的结果实验id不相等.");
			}
		}
		return mpList;
	}

}
