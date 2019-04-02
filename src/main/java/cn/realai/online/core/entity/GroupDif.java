package cn.realai.online.core.entity;

/**
 * 分组对比
 */
public class GroupDif {

	private Long id;
	
	private Long experimentId;
	
	private String variableName;
	
	private String groupName1;
	
	private String groupName2;
	
	private Double value1;
	
	private Double value2;

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	public String getGroupName1() {
		return groupName1;
	}

	public void setGroupName1(String groupName1) {
		this.groupName1 = groupName1;
	}

	public String getGroupName2() {
		return groupName2;
	}

	public void setGroupName2(String groupName2) {
		this.groupName2 = groupName2;
	}

	public Double getValue1() {
		return value1;
	}

	public void setValue1(Double value1) {
		this.value1 = value1;
	}

	public Double getValue2() {
		return value2;
	}

	public void setValue2(Double value2) {
		this.value2 = value2;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getExperimentId() {
		return experimentId;
	}

	public void setExperimentId(Long experimentId) {
		this.experimentId = experimentId;
	}
	
}
