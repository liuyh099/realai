package cn.realai.online.core.entity;

/**
 * 服务调优记录
 * @author zyy
 */
public class TuningRecord {

	private Long id;

	//创建时间
	private Long createTime;

	//更新时间
	private Long updateTime;

	//模型ID
	private Long modelId;

	//服务Id
	private Long serviceId;

	//调优类型
	private String type;

	public static enum TYPE {
		PSI("psi", "PSI调优"),
		KEY("key", "强制调优");

		public String value;
		public String desc;

		TYPE(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}
	}

	//最大psi值
	private Double maxPsi;

	//强制调优密钥串
	private String securityKey;

	//状态
	private String status;

	public static enum STATUS {
		VALID("valid", "有效"),
		USED("used", "已用"),
		INVALID("invalid", "失效");

		public String value;
		public String desc;

		STATUS(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getMaxPsi() {
		return maxPsi;
	}

	public void setMaxPsi(Double maxPsi) {
		this.maxPsi = maxPsi;
	}

	public String getSecurityKey() {
		return securityKey;
	}

	public void setSecurityKey(String securityKey) {
		this.securityKey = securityKey;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
