package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel
public class ExperimentalTrainSelectParamVO {

    @ApiModelProperty(value = "实验id",name ="实验id" ,required = true)
    @NotNull(message = "请选择实验")
    private Long id;

    //是否选择验证集
    @ApiModelProperty(value = "是否选择验证集",name="是否选择验证集 1:未选择 2:选择", required = true, example = "1:未选择 2:选择")
    @NotNull(message = "请选择验证集")
    private Integer verificationSet;


    //样本选择类型
    @ApiModelProperty(value = "验证集类型 1:时间顺序 2:随机选择", name="选择样本类型 1:时间顺序 2:随机选择", required = true, example = "验证集类型 1:时间顺序 2:随机选择")
    @NotNull(message = "请选择验证集类型")
    private Integer validSampleType;

    //样本选择类型
    @ApiModelProperty(value = "测试集类型 1:时间顺序 2:随机选择", name="选择样本类型 1:时间顺序 2:随机选择", required = true, example = "测试集类型 1:时间顺序 2:随机选择")
    @NotNull(message = "请选择测试集类型")
    private Integer testSampleType;


    @ApiModelProperty(value = "训练集比例",name = "训练集比例", required = true)
    @NotNull(message = "请选择训练集比例")
    private Integer trainRatio;

    //测试比例
    @ApiModelProperty(value = "测试集比例", name = "测试集比例", required = true)
    @NotNull(message = "请选择测试集比例")
    private Integer testRatio;

    //验证比例
    @ApiModelProperty(value = "验证集比例",name = "验证集比例", required = false)
    private Integer validRatio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVerificationSet() {
        return verificationSet;
    }

    public void setVerificationSet(Integer verificationSet) {
        this.verificationSet = verificationSet;
    }

    public Integer getTrainRatio() {
        return trainRatio;
    }

    public void setTrainRatio(Integer trainRatio) {
        this.trainRatio = trainRatio;
    }

    public Integer getTestRatio() {
        return testRatio;
    }

    public void setTestRatio(Integer testRatio) {
        this.testRatio = testRatio;
    }

    public Integer getValidRatio() {
        return validRatio;
    }

    public void setValidRatio(Integer validRatio) {
        this.validRatio = validRatio;
    }

    public Integer getValidSampleType() {
        return validSampleType;
    }

    public void setValidSampleType(Integer validSampleType) {
        this.validSampleType = validSampleType;
    }

    public Integer getTestSampleType() {
        return testSampleType;
    }

    public void setTestSampleType(Integer testSampleType) {
        this.testSampleType = testSampleType;
    }
}
