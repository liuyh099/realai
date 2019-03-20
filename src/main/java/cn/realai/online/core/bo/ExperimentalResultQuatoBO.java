package cn.realai.online.core.bo;

import java.util.List;

public class ExperimentalResultQuatoBO{

    private Integer model;


    private List<ExperimentResultSetBO> validateResultList;


    private List<ExperimentResultSetBO> testResultList;


    private List<ExperimentResultSetBO> trainResultList;

    public Integer getModel() {
        return model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }

    public List<ExperimentResultSetBO> getValidateResultList() {
        return validateResultList;
    }

    public void setValidateResultList(List<ExperimentResultSetBO> validateResultList) {
        this.validateResultList = validateResultList;
    }

    public List<ExperimentResultSetBO> getTestResultList() {
        return testResultList;
    }

    public void setTestResultList(List<ExperimentResultSetBO> testResultList) {
        this.testResultList = testResultList;
    }

    public List<ExperimentResultSetBO> getTrainResultList() {
        return trainResultList;
    }

    public void setTrainResultList(List<ExperimentResultSetBO> trainResultList) {
        this.trainResultList = trainResultList;
    }
}
