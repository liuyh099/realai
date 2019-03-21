package cn.realai.online.core.bo;


import java.math.BigDecimal;
import java.sql.Date;

/**
 * 功能描述：TODO
 *
 * @author admin
 * @create 2019-03-19 15:19
 */
public class ModelListBO {

    private Long modelId;
    private String modelName;
    private Long serviceId;
    private String serviceName;
    private Long experimentId;
    private String experimentName;
    private Date releaseTime;
    private String algorithmType;
    private int tuningCount;
    private String tuningReason;
    private BigDecimal psi;
    private int aler;

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

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
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

    public String getTuningReason() {
        return tuningReason;
    }

    public void setTuningReason(String tuningReason) {
        this.tuningReason = tuningReason;
    }

    public BigDecimal getPsi() {
        return psi;
    }

    public void setPsi(BigDecimal psi) {
        this.psi = psi;
    }

    public int getAler() {
        return aler;
    }

    public void setAler(int aler) {
        this.aler = aler;
    }
}