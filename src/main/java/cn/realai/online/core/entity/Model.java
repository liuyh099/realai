package cn.realai.online.core.entity;

/**
 * 模型
 * @author lyh
 */
public class Model {

	private long id;
	
	//模型名称	
	private String name;
	
	//创建时间
	private long createTime;
	
	//实验id
	private long experimentId;
	
	//服务id
	private long serviceId;
	
	//备注
	private String remark;
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getExperimentId() {
		return experimentId;
	}

	public void setExperimentId(long experimentId) {
		this.experimentId = experimentId;
	}

	public long getServiceId() {
		return serviceId;
	}

	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}
	
}
