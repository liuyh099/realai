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
    private String releaseTime;

    @ApiModelProperty(value = "调优原因")
    private String tuningReason;

    @ApiModelProperty(value = "来源实验名称")
    private String experimentName;

    @ApiModelProperty(value = "Y的定义")
    private String ydefine;

    @ApiModelProperty(value = "服务类型")
    private String serviceType;

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

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getTuningReason() {
        return tuningReason;
    }

    public void setTuningReason(String tuningReason) {
        this.tuningReason = tuningReason;
    }

    public String getExperimentName() {
        return experimentName;
    }

    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }

    public String getYdefine() {
        return ydefine;
    }

    public void setYdefine(String ydefine) {
        this.ydefine = ydefine;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
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
