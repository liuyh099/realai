package cn.realai.online.core.entity;

/**
 * 千人千面同质数据（热力图数据来源）
 * @author lyh
 */
public class PersonalHomoResultSet {

	private long id;
	
	//批次id
	private long batchId;
		
	//用户消费流水序号（第几条流水）(x轴)
	private int k;
	
	//实验样本人员信息id（千人千面人员信息id）
	private long personalId;
	
	//变量名(y轴)
	private String variableId;
	
	//值
	private String value;
	
	//权重(显示值)
	private double weight;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public long getPersonalId() {
		return personalId;
	}

	public void setPersonalId(long personalId) {
		this.personalId = personalId;
	}

	public String getVariableId() {
		return variableId;
	}

	public void setVariableId(String variableId) {
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

	public long getBatchId() {
		return batchId;
	}

	public void setBatchId(long batchId) {
		this.batchId = batchId;
	}

}
