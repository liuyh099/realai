package cn.realai.online.tool.modelcallthreadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.realai.online.core.bo.ExperimentBO;
import cn.realai.online.core.service.ExperimentService;
import cn.realai.online.util.SpringContextUtils;

/**
 * 每日跑批任务
 * @author lyh
 */
public class BatchDailyTask implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(BatchDailyTask.class);
	
	private Long experimentId;
	
	private String redisKey;
	
	public BatchDailyTask(Long experimentId, String redisKey) {
		this.experimentId = experimentId;
		this.redisKey = redisKey;
	}
	
	@Override
	public void run() {
		//查询实验信息
		ExperimentService experimentService = SpringContextUtils.getBean(ExperimentService.class);
		ExperimentBO experiment = experimentService.selectExperimentById(experimentId);
		if (experiment == null) {
			logger.error("BatchDailyTask run. experiment信息不存在. experimentId{}", experimentId);
		}
		
		//查询当日批次信息
		
		
	}

}
