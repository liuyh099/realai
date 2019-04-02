package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 功能描述：TODO
 *
 * @author admin
 * @create 2019-03-18 14:28
 */
@ApiModel
public class ModelListVO {

    @ApiModelProperty(value = "模型id")
    private long modelId;

    @ApiModelProperty(value = "模型名称")
    private String modelName;

    @ApiModelProperty(value = "服务Id")
    private Long serviceId;

    @ApiModelProperty(value = "服务名称")
    private String serviceName;

    @ApiModelProperty(value = "实验id")
    private long experimentId;

    @ApiModelProperty(value = "来源实验")
    private String experementName;

    @ApiModelProperty(value = "上线时间")
    private Long releaseTime;

    @ApiModelProperty(value = "算法")
    private String algorithm;

    @ApiModelProperty(value = "调优序列")
    private String tuningNo;

    @ApiModelProperty(value = "调优原因")
    private String tuningReason;

    @ApiModelProperty(value = "psi喇叭值")
    private Double psi;

    @ApiModelProperty(value = "是否预警(true: 预警； false：不预警)")
    private boolean aler;


    public long getModelId() {
        return modelId;
    }

    public void setModelId(long modelId) {
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

    public long getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(long experimentId) {
        this.experimentId = experimentId;
    }

    public String getExperementName() {
        return experementName;
    }

    public void setExperementName(String experementName) {
        this.experementName = experementName;
    }

    public Long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Long releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getTuningNo() {
        return tuningNo;
    }

    public void setTuningNo(String tuningNo) {
        this.tuningNo = tuningNo;
    }

    public String getTuningReason() {
        return tuningReason;
    }

    public void setTuningReason(String tuningReason) {
        this.tuningReason = tuningReason;
    }

    public Double getPsi() {
        return psi;
    }

    public void setPsi(Double psi) {
        this.psi = psi;
    }

    public boolean isAler() {
        return aler;
    }

    public void setAler(boolean aler) {
        this.aler = aler;
    }
}
