package cn.realai.online.core.entity;

/**
 * 实验样本人员信息（千人千面人员信息）
 *
 * @author lyh
 */
public class PersonalInformation {

    //数据id
    private Long id;

    private Long experimentId;

    //实验组别id
    private Long groupId;

    //计算后Python的分组名称
    private String groupName;

    //批次id
    private Long batchId;

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
    private Long createTime;

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


    private Long inputStartDate;

    private Long inputEndDate;

    public Long getInputStartDate() {
        return inputStartDate;
    }

    public void setInputStartDate(Long inputStartDate) {
        this.inputStartDate = inputStartDate;
    }

    public Long getInputEndDate() {
        return inputEndDate;
    }

    public void setInputEndDate(Long inputEndDate) {
        this.inputEndDate = inputEndDate;
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

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getPersonalName() {
        return personalName;
    }

    public void setPersonalName(String personalName) {
        this.personalName = personalName;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
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
}
