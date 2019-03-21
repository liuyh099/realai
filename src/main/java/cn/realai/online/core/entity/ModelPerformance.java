package cn.realai.online.core.entity;

/**
 * 模型表现
 * 描述实验的效果
 *
 * @author lyh
 */
public class ModelPerformance {

    private Long id;

    //指标名
    private String metricName;

    //实验id
    private Long experimentId;

    //训练集
    private Double trainingSet;

    //测试集
    private Double testSet;

    //验证集
    private Double validSet;

    private Long createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public Long getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(Long experimentId) {
        this.experimentId = experimentId;
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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
