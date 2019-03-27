package cn.realai.online.core.bo;


/**
 * 功能描述：TODO
 *
 * @author admin
 * @create 2019-03-19 15:19
 */
public class ModelDetailBO {

    private Long modelId;
    private String modelName;
    private Long serviceId;
    private String serviceName;
    private Long experimentId;
    private String experimentName;
    private Long releaseTime;
    private String algorithmType;
    private int tuningCount;
    private String ydefine;
    private String tuningReason;
    private String serviceType;
    private String remark;

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

    public Long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Long releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getAlgorithmType() {
        return algorithmType;
    }

    public void setAlgorithmType(String algorithmType) {
        this.algorithmType = algorithmType;
    }

    public int getTuningCount() {
        return tuningCount;
    }

    public void setTuningCount(int tuningCount) {
        this.tuningCount = tuningCount;
    }

    public String getYdefine() {
        return ydefine;
    }

    public void setYdefine(String ydefine) {
        this.ydefine = ydefine;
    }

    public String getTuningReason() {
        return tuningReason;
    }

    public void setTuningReason(String tuningReason) {
        this.tuningReason = tuningReason;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
