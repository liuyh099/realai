package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ExperimentalTrainSelectParamVO {

    @ApiModelProperty( value = "实验id" ,required = true)
    private long id;

    //是否选择验证集
    @ApiModelProperty( value = "是否选择验证集" ,required = true,example = "1:未选择 2:选择")
    private int verificationSet;


    //样本选择类型
    @ApiModelProperty( value = "验证集选择类型" ,required = true,example = "1:时间顺序 2:随机选择")
    private int sampleType;


    @ApiModelProperty( value = "训练集比例" ,required = true)
    private int trainRatio;

    //测试比例
    @ApiModelProperty( value = "测试集比例" ,required = true)
    private int testRatio;

    //验证比例
    @ApiModelProperty( value = "验证集比例" ,required = false)
    private int validRatio;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getVerificationSet() {
        return verificationSet;
    }

    public void setVerificationSet(int verificationSet) {
        this.verificationSet = verificationSet;
    }

    public int getSampleType() {
        return sampleType;
    }

    public void setSampleType(int sampleType) {
        this.sampleType = sampleType;
    }

    public int getTrainRatio() {
        return trainRatio;
    }

    public void setTrainRatio(int trainRatio) {
        this.trainRatio = trainRatio;
    }

    public int getTestRatio() {
        return testRatio;
    }

    public void setTestRatio(int testRatio) {
        this.testRatio = testRatio;
    }

    public int getValidRatio() {
        return validRatio;
    }

    public void setValidRatio(int validRatio) {
        this.validRatio = validRatio;
    }
}
