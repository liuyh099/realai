package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel
public class ExperimentalResultQuatoVO {

    @ApiModelProperty(value = "验证结果集",dataType = "List")
    private List<ExperimentalResultQuatoDataVO> validateResultList;

    @ApiModelProperty(value = "测试结果集",dataType = "List")
    private List<ExperimentalResultQuatoDataVO> testResultList;

    @ApiModelProperty(value = "训练结果集",dataType = "List")
    private List<ExperimentalResultQuatoDataVO> trainResultList;


    public List<ExperimentalResultQuatoDataVO> getValidateResultList() {
        return validateResultList;
    }

    public void setValidateResultList(List<ExperimentalResultQuatoDataVO> validateResultList) {
        this.validateResultList = validateResultList;
    }

    public List<ExperimentalResultQuatoDataVO> getTestResultList() {
        return testResultList;
    }

    public void setTestResultList(List<ExperimentalResultQuatoDataVO> testResultList) {
        this.testResultList = testResultList;
    }

    public List<ExperimentalResultQuatoDataVO> getTrainResultList() {
        return trainResultList;
    }

    public void setTrainResultList(List<ExperimentalResultQuatoDataVO> trainResultList) {
        this.trainResultList = trainResultList;
    }
}
