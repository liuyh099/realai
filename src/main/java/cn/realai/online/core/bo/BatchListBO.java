package cn.realai.online.core.bo;

/**
 * 功能描述：TODO
 *
 * @author admin
 * @create 2019-03-18 14:28
 */
public class BatchListBO {

    private long batchId;

    private Long modelId;

    private String modelName;

    private Long experimentId;

    private String experimentName;

    private Integer status;

    private Long releaseTime;

    private Long serviceId;

    private String serviceName;

    private Integer batchTimes;

    private Integer offlineTimes;

    private Long batchCreateTime;

    private String downUrl;

    private String remark;

    public long getBatchId() {
        return batchId;
    }

    public void setBatchId(long batchId) {
        this.batchId = batchId;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
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

    public String getExperimentName() {
        return experimentName;
    }

    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Long releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Integer getBatchTimes() {
        return batchTimes;
    }

    public void setBatchTimes(Integer batchTimes) {
        this.batchTimes = batchTimes;
    }

    public Integer getOfflineTimes() {
        return offlineTimes;
    }

    public void setOfflineTimes(Integer offlineTimes) {
        this.offlineTimes = offlineTimes;
    }

    public Long getBatchCreateTime() {
        return batchCreateTime;
    }

    public void setBatchCreateTime(Long batchCreateTime) {
        this.batchCreateTime = batchCreateTime;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
