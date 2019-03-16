package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;

@ApiModel
public class ExperimentalResultTopVO {

    private ExperimentalResultTopGroupVO trainTop;

    private ExperimentalResultTopGroupVO testTop;

    private ExperimentalResultTopGroupVO validateTop;


    public ExperimentalResultTopGroupVO getTrainTop() {
        return trainTop;
    }

    public void setTrainTop(ExperimentalResultTopGroupVO trainTop) {
        this.trainTop = trainTop;
    }

    public ExperimentalResultTopGroupVO getTestTop() {
        return testTop;
    }

    public void setTestTop(ExperimentalResultTopGroupVO testTop) {
        this.testTop = testTop;
    }

    public ExperimentalResultTopGroupVO getValidateTop() {
        return validateTop;
    }

    public void setValidateTop(ExperimentalResultTopGroupVO validateTop) {
        this.validateTop = validateTop;
    }
}
