package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 功能描述：TODO
 *
 * @author admin
 * @create 2019-03-18 14:28
 */
@ApiModel
public class ModelDetailVO {

    @ApiModelProperty(value = "模型id")
    private long modelId;

    @ApiModelProperty(value = "模型名称")
    private String modelName;

    @ApiModelProperty(value = "上线时间")
    private Long releaseTime;

    @ApiModelProperty(value = "调优原因")
    private String tuningReason;

    @ApiModelProperty(value = "调优次数")
    private Integer tuningCount;

    @ApiModelProperty(value = "Y的定义")
    private String ydefine;

    @ApiModelProperty(value = "来源实验名称")
    private String experimentName;

    @ApiModelProperty(value = "服务名称")
    private String serviceName;

    @ApiModelProperty(value = "服务类型")
    private Integer serviceType;

    @ApiModelProperty(value = "服务子类型")
    private Integer serviceBusinessType;

    @ApiModelProperty(value = "服务类型名称")
    private String serviceTypeName;

    @ApiModelProperty(value = "算法类型")
    private String algorithmType;

    @ApiModelProperty(value = "样本综述")
    private String sampleSummary;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "模型表现")
    private List<ModelPerformanceVO> performanceList;

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

    public Long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Long releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getTuningReason() {
        return tuningReason;
    }

    public void setTuningReason(String tuningReason) {
        this.tuningReason = tuningReason;
    }

    public Integer getTuningCount() {
        return tuningCount;
    }

    public void setTuningCount(Integer tuningCount) {
        this.tuningCount = tuningCount;
    }

    public String getYdefine() {
        return ydefine;
    }

    public void setYdefine(String ydefine) {
        this.ydefine = ydefine;
    }

    public String getExperimentName() {
        return experimentName;
    }

    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public Integer getServiceBusinessType() {
        return serviceBusinessType;
    }

    public void setServiceBusinessType(Integer serviceBusinessType) {
        this.serviceBusinessType = serviceBusinessType;
    }

    public String getServiceTypeName() {
        return serviceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }

    public String getAlgorithmType() {
        return algorithmType;
    }

    public void setAlgorithmType(String algorithmType) {
        this.algorithmType = algorithmType;
    }

    public String getSampleSummary() {
        return sampleSummary;
    }

    public void setSampleSummary(String sampleSummary) {
        this.sampleSummary = sampleSummary;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<ModelPerformanceVO> getPerformanceList() {
        return performanceList;
    }

    public void setPerformanceList(List<ModelPerformanceVO> performanceList) {
        this.performanceList = performanceList;
    }
}
