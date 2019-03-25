package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ExperimentalResultSummaryVO {

    //实验类型
    @ApiModelProperty(value = "数据集类型", example = "1:训练集 2:测试集 3:验证集")
    private Integer dataSetType;

    //样本描述
    @ApiModelProperty(value = "样本描述")
    private String sampleDesc;

    //样本数量
    @ApiModelProperty(value = "样本数")
    private Integer sampleCount;

    //样本用户数
    @ApiModelProperty(value = "标签用户数")
    private Integer numberOfLabelUsers;

    //标签样本比例
    @ApiModelProperty(value = "标签样本比例")
    private String labelSampleRatio;

    //预测最小值
    @ApiModelProperty(value = "预测最小值")
    private String min;

    //预测最大值
    @ApiModelProperty(value = "预测最大值")
    private String max;

    //预测平均值
    @ApiModelProperty(value = "预测平均值")
    private String mean;


    public Integer getDataSetType() {
        return dataSetType;
    }

    public void setDataSetType(Integer dataSetType) {
        this.dataSetType = dataSetType;
    }

    public String getSampleDesc() {
        return sampleDesc;
    }

    public void setSampleDesc(String sampleDesc) {
        this.sampleDesc = sampleDesc;
    }

    public Integer getSampleCount() {
        return sampleCount;
    }

    public void setSampleCount(Integer sampleCount) {
        this.sampleCount = sampleCount;
    }

    public Integer getNumberOfLabelUsers() {
        return numberOfLabelUsers;
    }

    public void setNumberOfLabelUsers(Integer numberOfLabelUsers) {
        this.numberOfLabelUsers = numberOfLabelUsers;
    }

    public String getLabelSampleRatio() {
        return labelSampleRatio;
    }

    public void setLabelSampleRatio(String labelSampleRatio) {
        this.labelSampleRatio = labelSampleRatio;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }
}
