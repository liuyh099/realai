package cn.realai.online.core.entity;

/**
 * 模型
 *
 * @author lyh
 */
public class Model {

    private Long id;

    //模型名称
    private String name;

    //创建时间
    private Long createTime;

    //模型状态
    private String releaseStatus;

    public static enum RELEASE_STATUS {
        NONE("none", "未发布"),
        ONLINE("online", "线上发布"),
        OFFLINE("offline", "线下发布");

        public String value;
        public String desc;

        RELEASE_STATUS(String value, String desc) {
            this.value = value;
            this.desc = desc;
        }
    }

    //实验id
    private Long experimentId;

    //服务id
    private Long serviceId;

    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(String releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public Long getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(Long experimentId) {
        this.experimentId = experimentId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
}
