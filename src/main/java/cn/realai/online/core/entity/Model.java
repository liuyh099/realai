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


    private Long createUserId;

    public static enum RELEASE_STATUS {
        NONE("none", "已下线"),
        ONLINE("online", "线上发布"),
        OFFLINE("offline", "离线发布");

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

    //调优序列 同一个服务下的模型再次创建则+1，第一次创建为0
    private Integer tuningNo;

    private Long tuningRecordId;

    private String remark;


    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

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

    public Integer getTuningNo() {
        return tuningNo;
    }

    public void setTuningNo(Integer tuningNo) {
        this.tuningNo = tuningNo;
    }
    public Long getTuningRecordId() {
        return tuningRecordId;
    }

    public void setTuningRecordId(Long tuningRecordId) {
        this.tuningRecordId = tuningRecordId;
    }
}
