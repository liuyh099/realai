package cn.realai.online.tool.modelcallthreadpool;

public abstract class BaseBatchTask implements Runnable {

	Long experimentId;
	
	Long batchId;
	
	String redisKey;
	
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
