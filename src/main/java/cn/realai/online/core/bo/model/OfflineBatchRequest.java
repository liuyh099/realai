package cn.realai.online.core.bo.model;

public class OfflineBatchRequest extends BatchRequestBase {

    private String redisKey;
    
    //离线跑批数据下载路径
    private String downUrl;
    
    //离线跑批id
    private Long batchId;
    
    public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

	public String getRedisKey() {
		return redisKey;
	}

	public void setRedisKey(String redisKey) {
		this.redisKey = redisKey;
	}

}
