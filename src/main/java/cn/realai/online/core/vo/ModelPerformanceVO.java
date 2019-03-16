package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "实验发布详情信息")
public class ModelPerformanceVO {

    //指标名
    @ApiModelProperty(value = "模型表现")
    private String metricName;

    //训练集
    @ApiModelProperty(value = "训练集")
    private Double trainingSet;

    //测试集
    @ApiModelProperty(value = "测试集")
    private Double testSet;

    //验证集
    @ApiModelProperty(value = "验证集")
    private Double validSet;

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public Double getTrainingSet() {
        return trainingSet;
    }

    public void setTrainingSet(Double trainingSet) {
        this.trainingSet = trainingSet;
    }

    public Double getTestSet() {
        return testSet;
    }

    public void setTestSet(Double testSet) {
        this.testSet = testSet;
    }

    public Double getValidSet() {
        return validSet;
    }

    public void setValidSet(Double validSet) {
        this.validSet = validSet;
    }
}
