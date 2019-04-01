package cn.realai.online.core.entity;

/**
 * 服务历史
 *
 * @author lyh
 */
public class ServiceDeployRecord {

    //主键
    private Long id;

    //服务id
    private Long serviceId;

    //模型名称
    private String modelName;

    //实验id
    private Long experimentId;

    //创建时间
    private Long createTime;

    //操作类型
    private int opertionType;

    //操作人
    private Long userId;

    //备注
    private String remark;

    private Long modelId;

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Long getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(Long experimentId) {
        this.experimentId = experimentId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public int getOpertionType() {
        return opertionType;
    }

    public void setOpertionType(int opertionType) {
        this.opertionType = opertionType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
