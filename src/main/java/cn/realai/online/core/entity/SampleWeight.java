package cn.realai.online.core.entity;

/**
 * 样本权重
 * @author lyh
 */
public class SampleWeight {

	private long id;
	
	private long experimentId;
	
	private long groupId;
	
	//计算后Python的分组名称
	private String groupName;

	//变量ID
	private long variableId;
	
	//变量来源
	private String variableName;
	
	//变量权重
	private String variableWeight;
	
	//变量类型
	private int variableType;
	
	//同质
	public static final int SAMPLE_TYPE_HOMO = 1;
	
	//异质
	public static final int SAMPLE_TYPE_HETERO = 2;
	
	//分箱特征
	private String boxBeans;
	
	//图片路径
	private String imgUrl;


	public long getVariableId() {
		return variableId;
	}

	public void setVariableId(long variableId) {
		this.variableId = variableId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public String getBoxBeans() {
		return boxBeans;
	}

	public void setBoxBeans(String boxBeans) {
		this.boxBeans = boxBeans;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public long getExperimentId() {
		return experimentId;
	}

	public void setExperimentId(long experimentId) {
		this.experimentId = experimentId;
	}

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	public String getVariableWeight() {
		return variableWeight;
	}

	public void setVariableWeight(String variableWeight) {
		this.variableWeight = variableWeight;
	}

	public int getVariableType() {
		return variableType;
	}

	public void setVariableType(int variableType) {
		this.variableType = variableType;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
