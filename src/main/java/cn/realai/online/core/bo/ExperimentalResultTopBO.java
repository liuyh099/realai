package cn.realai.online.core.bo;

public class ExperimentalResultTopBO {

    private ExperimentalResultTopGroupBO trainTop;

    private ExperimentalResultTopGroupBO testTop;

    private ExperimentalResultTopGroupBO validateTop;

    public ExperimentalResultTopGroupBO getTrainTop() {
        return trainTop;
    }

    public void setTrainTop(ExperimentalResultTopGroupBO trainTop) {
        this.trainTop = trainTop;
    }

    public ExperimentalResultTopGroupBO getTestTop() {
        return testTop;
    }

    public void setTestTop(ExperimentalResultTopGroupBO testTop) {
        this.testTop = testTop;
    }

    public ExperimentalResultTopGroupBO getValidateTop() {
        return validateTop;
    }

    public void setValidateTop(ExperimentalResultTopGroupBO validateTop) {
        this.validateTop = validateTop;
    }
}
