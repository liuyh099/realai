package cn.realai.online.core.entity;

/**
 * 样本权重
 *
 * @author lyh
 */
public class SampleWeight {

    private Long id;

    private Long experimentId;

    private Long groupId;

    //计算后Python的分组名称
    private String groupName;

    //变量ID
    private Long variableId;

    //变量来源
    private String variableName;

    //变量权重
    private Double variableWeight;

    //变量类型
    private Integer variableType;

    //分箱特征
    private String boxBeans;

    //图片路径
    private String imgUrl;


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

    public Long getVariableId() {
        return variableId;
    }

    public void setVariableId(Long variableId) {
        this.variableId = variableId;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public Double getVariableWeight() {
        return variableWeight;
    }

    public void setVariableWeight(Double variableWeight) {
        this.variableWeight = variableWeight;
    }

    public Integer getVariableType() {
        return variableType;
    }

    public void setVariableType(Integer variableType) {
        this.variableType = variableType;
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
}
