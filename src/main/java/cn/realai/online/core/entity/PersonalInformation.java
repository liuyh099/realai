package cn.realai.online.core.entity;

/**
 * 实验样本人员信息（千人千面人员信息）
 * @author lyh
 */
public class PersonalInformation {

	//数据id
	private long id;
	
	private long experimentId;
	
	//实验组别id
	private long groupId;
	
	//计算后Python的分组名称
	private String groupName;
	
	//批次id
	private long batchId;
	
	//用户姓名
	private String personalName;
	
	//模型步骤
	private int stage;
	
	//身份证号
	private String personalCardId;
	
	//手机号
	private String phoneNum;
	
	//用户id 用户在行方的id（无需处理）
	private String personalId;
	
	//创建时间
	private long createTime;
	
	//订单日期
	private String orderDate;
	
	//标签
	private int label;
	
	//概率
	private double probability;

	//python返回的批次标记字段，只做解析不需要入库
	private String batchStr;

	public String getBatchStr() {
		return batchStr;
	}

	public void setBatchStr(String batchStr) {
		this.batchStr = batchStr;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
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

	public String getPersonalName() {
		return personalName;
	}

	public void setPersonalName(String personalName) {
		this.personalName = personalName;
	}

	public String getPersonalCardId() {
		return personalCardId;
	}

	public void setPersonalCardId(String personalCardId) {
		this.personalCardId = personalCardId;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getPersonalId() {
		return personalId;
	}

	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public int getLabel() {
		return label;
	}

	public void setLabel(int label) {
		this.label = label;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
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

	public int getStage() {
		return stage;
	}

	public void setStage(int stage) {
		this.stage = stage;
	}
	
}
