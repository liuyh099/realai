package cn.realai.online.core.entity;

/**
 * 千人千面组合和特征(异质最强组合特征)
 * @author lyh
 */
public class PersonalComboResultSet {

	private long id;
	
	//批次id 
	private long batchId;
	
	//实验样本人员信息id（千人千面人员信息id）
	private long personalId;
	
	//实验id
	private long experimentId;
	
	//变量id
	private long variableId1;
	
	//值1
	private String value1;
	
	//变量id2
	private String variableId2;
	
	//值2
	private String value2;
	
	//变量id3
	private String variableId3;

	//值3
	private String value3;
	
	//权重
	private double weight;


	public long getVariableId1() {
		return variableId1;
	}

	public void setVariableId1(long variableId1) {
		this.variableId1 = variableId1;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getVariableId2() {
		return variableId2;
	}

	public void setVariableId2(String variableId2) {
		this.variableId2 = variableId2;
	}

	public String getVariableId3() {
		return variableId3;
	}

	public void setVariableId3(String variableId3) {
		this.variableId3 = variableId3;
	}

	public long getExperimentId() {
		return experimentId;
	}

	public void setExperimentId(long experimentId) {
		this.experimentId = experimentId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPersonalId() {
		return personalId;
	}

	public void setPersonalId(long personalId) {
		this.personalId = personalId;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public String getValue3() {
		return value3;
	}

	public void setValue3(String value3) {
		this.value3 = value3;
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
