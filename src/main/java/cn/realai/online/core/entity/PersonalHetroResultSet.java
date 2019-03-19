package cn.realai.online.core.entity;

/**
 * 千人千面异质数据
 * @author lyh
 */
public class PersonalHetroResultSet {

	private long id;
	
	//批次id
	private long batchId;
	
	//实验样本人员信息id 主键id
	private long pid;
	
	//python计算返回 实验样本人员信息id（千人千面人员信息id）
	private String personalId;
	
	//实验id
	private long experimentId;
	
	//变量id
	private long variableId;
	
	//值
	private String value;
	
	//权重(权重排序取绝对值)
	private double weight;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPersonalId() {
		return personalId;
	}

	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}

	public long getVariableId() {
		return variableId;
	}

	public void setVariableId(long variableId) {
		this.variableId = variableId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public long getExperimentId() {
		return experimentId;
	}

	public void setExperimentId(long experimentId) {
		this.experimentId = experimentId;
	}

	public long getBatchId() {
		return batchId;
	}

	public void setBatchId(long batchId) {
		this.batchId = batchId;
	}

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

}
