package cn.realai.online.tool.modelcallthreadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.realai.online.core.entity.MLock;
import cn.realai.online.core.service.ExperimentService;
import cn.realai.online.util.SpringContextUtils;

public class BatchTaskOfDone extends BaseBatchTask {

	private static Logger logger = LoggerFactory.getLogger(BatchTaskOfDone.class);
	
	private Long experimentId;
	
	private Long batchId;
	
	private Boolean done;
	
	public BatchTaskOfDone(Long experimentId, Long batchId, Boolean done) {
		this.experimentId = experimentId;
		this.batchId = batchId;
		this.done = done;
	}
	
	@Override
	public void run() {
		logger.info("BatchTaskOfDone run. experimentId{}, batchId{}, redisKey{}", experimentId, batchId, redisKey);
		//释放批次锁
		ExperimentService experimentService = SpringContextUtils.getBean(ExperimentService.class);
		MLock mLock = experimentService.getExperimentTrainMLockInstance(experimentId);
		mLock.unLock();
	}

}
