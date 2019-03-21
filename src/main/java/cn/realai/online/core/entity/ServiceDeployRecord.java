package cn.realai.online.core.entity;

/**
 * 服务历史
 *
 * @author lyh
 */
public class ServiceDeployRecord {

    //主键
    private long id;

    //服务id
    private long serviceId;

    //模型名称
    private String modelName;

    //实验id
    private long experimentId;

    //创建时间
    private long createTime;

    //操作类型
    private int opertionType;

    //操作人
    private long userId;

    //备注
    private String remark;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public long getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(long experimentId) {
        this.experimentId = experimentId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getOpertionType() {
        return opertionType;
    }

    public void setOpertionType(int opertionType) {
        this.opertionType = opertionType;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
