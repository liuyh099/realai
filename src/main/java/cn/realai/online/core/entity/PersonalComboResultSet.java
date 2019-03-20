package cn.realai.online.core.entity;

/**
 * 千人千面组合和特征(异质最强组合特征)
 * @author lyh
 */
public class PersonalComboResultSet {

	private Long id;
	
	//批次id
	private Long batchId;
	
	//实验样本人员信息id 主键id
	private Long pid;
	
	//python计算返回 实验样本人员信息id（千人千面人员信息id）
	private String personalId;
	
	//实验id
	private long experimentId;
	
	//变量id
	private long variableId1;
	
	//变量名字1
	private String variableName1;
	
	//值1
	private String value1;
	
	//变量id2
	private long variableId2;
	
	//变量名字2
	private String variableName2;
	
	//值2
	private String value2;
	
	//变量id3
	private long variableId3;
	
	//变量名字3
	private String variableName3;

	//值3
	private String value3;
	
	//权重
	private double weight;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getPersonalId() {
		return personalId;
	}

	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}

	public long getExperimentId() {
		return experimentId;
	}

	public void setExperimentId(long experimentId) {
		this.experimentId = experimentId;
	}

	public long getVariableId1() {
		return variableId1;
	}

	public void setVariableId1(long variableId1) {
		this.variableId1 = variableId1;
	}

	public String getVariableName1() {
		return variableName1;
	}

	public void setVariableName1(String variableName1) {
		this.variableName1 = variableName1;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public long getVariableId2() {
		return variableId2;
	}

	public void setVariableId2(long variableId2) {
		this.variableId2 = variableId2;
	}

	public String getVariableName2() {
		return variableName2;
	}

	public void setVariableName2(String variableName2) {
		this.variableName2 = variableName2;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public long getVariableId3() {
		return variableId3;
	}

	public void setVariableId3(long variableId3) {
		this.variableId3 = variableId3;
	}

	public String getVariableName3() {
		return variableName3;
	}

	public void setVariableName3(String variableName3) {
		this.variableName3 = variableName3;
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
}
