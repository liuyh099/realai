package cn.realai.online.core.entity;

/**
 * 局域网和
 * @author 86183
 */
public class DeployInfo {

	/**
	 * 服务id
	 */
	private Long serviceId;
	
	/**
	 * 模型id
	 */
	private Long experimentId;
	
	/**
	 * 密钥
	 */
	private String secretKey;  
	
	/**
	 * 原信息
	 */
	private String historyInfo;
	
	/**
	 * 创建时间
	 */
	private long createTime;
	
	/**
	 * 修改时间
	 */
	private long updateTime;
	
	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Long getExperimentId() {
		return experimentId;
	}

	public void setExperimentId(Long experimentId) {
		this.experimentId = experimentId;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public String getHistoryInfo() {
		return historyInfo;
	}

	public void setHistoryInfo(String historyInfo) {
		this.historyInfo = historyInfo;
	}

}
