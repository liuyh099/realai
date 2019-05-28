package cn.realai.online.tool.traincallbackthreadpool;

import cn.realai.online.core.entity.BatchExecutionTask;

public abstract class BaseBatchTask implements Runnable {

	BatchExecutionTask bet;
	
	Long experimentId;
	
	Long batchId;
	
	String redisKey;
	
	int errorCount;
	
	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public String getRedisKey() {
		return redisKey;
	}

	public void setRedisKey(String redisKey) {
		this.redisKey = redisKey;
	}

}
