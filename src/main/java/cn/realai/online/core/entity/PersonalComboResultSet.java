package cn.realai.online.core.entity;

/**
 * 千人千面组合和特征
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
	
	//变量名1
	private String variableName1;
	
	//变量含义1
	private String variableMeaning1;
	
	//值1
	private String value1;
	
	//变量名2
	private String variableName2;
	
	//变量含义2
	private String variableMeaning2;
	
	//值2
	private String value2;
	
	//变量名3
	private String variableName3;
	
	//变量含义3
	private String variableMeaning3;
	
	//值3
	private String value3;
	
	//权重
	private double weight;
	
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

	public String getVariableName1() {
		return variableName1;
	}

	public void setVariableName1(String variableName1) {
		this.variableName1 = variableName1;
	}

	public String getVariableMeaning1() {
		return variableMeaning1;
	}

	public void setVariableMeaning1(String variableMeaning1) {
		this.variableMeaning1 = variableMeaning1;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getVariableName2() {
		return variableName2;
	}

	public void setVariableName2(String variableName2) {
		this.variableName2 = variableName2;
	}

	public String getVariableMeaning2() {
		return variableMeaning2;
	}

	public void setVariableMeaning2(String variableMeaning2) {
		this.variableMeaning2 = variableMeaning2;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public String getVariableName3() {
		return variableName3;
	}

	public void setVariableName3(String variableName3) {
		this.variableName3 = variableName3;
	}

	public String getVariableMeaning3() {
		return variableMeaning3;
	}

	public void setVariableMeaning3(String variableMeaning3) {
		this.variableMeaning3 = variableMeaning3;
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
