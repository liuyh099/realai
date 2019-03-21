package cn.realai.online.core.entity;

/**
 * 风控结果集（实验评估风控）
 *
 * @author lyh
 */
public class ExperimentResultSet {

    private Long id;

    //实验id
    private Long experimentId;

    private Long parentId;

    //数据集类型
    private Integer dataSetType;

    //平均切分的十组数据名称，不同于聚类分组
    private String groupName;

    //
    private double minPro;

    //
    private int minProScore;

    //
    private double maxPro;

    //
    private int maxProScore;

    //
    private int negtiveCount;

    //
    private int postiveCount;

    //
    private int count;

    //
    private double negtiveCountInPostiveCount;

    //
    private double positiveRatio;

    //
    private double cumulativePositiveRatio;

    //
    private double postiveCountInTotalPositiveCount;

    //
    private double cumulativePostiveCountInTotalPositiveCount;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(Long experimentId) {
        this.experimentId = experimentId;
    }

    public Integer getDataSetType() {
        return dataSetType;
    }

    public void setDataSetType(Integer dataSetType) {
        this.dataSetType = dataSetType;
    }

    public double getMinPro() {
        return minPro;
    }

    public void setMinPro(double minPro) {
        this.minPro = minPro;
    }

    public int getMinProScore() {
        return minProScore;
    }

    public void setMinProScore(int minProScore) {
        this.minProScore = minProScore;
    }

    public double getMaxPro() {
        return maxPro;
    }

    public void setMaxPro(double maxPro) {
        this.maxPro = maxPro;
    }

    public int getMaxProScore() {
        return maxProScore;
    }

    public void setMaxProScore(int maxProScore) {
        this.maxProScore = maxProScore;
    }

    public int getNegtiveCount() {
        return negtiveCount;
    }

    public void setNegtiveCount(int negtiveCount) {
        this.negtiveCount = negtiveCount;
    }

    public int getPostiveCount() {
        return postiveCount;
    }

    public void setPostiveCount(int postiveCount) {
        this.postiveCount = postiveCount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getNegtiveCountInPostiveCount() {
        return negtiveCountInPostiveCount;
    }

    public void setNegtiveCountInPostiveCount(double negtiveCountInPostiveCount) {
        this.negtiveCountInPostiveCount = negtiveCountInPostiveCount;
    }

    public double getPositiveRatio() {
        return positiveRatio;
    }

    public void setPositiveRatio(double positiveRatio) {
        this.positiveRatio = positiveRatio;
    }

    public double getCumulativePositiveRatio() {
        return cumulativePositiveRatio;
    }

    public void setCumulativePositiveRatio(double cumulativePositiveRatio) {
        this.cumulativePositiveRatio = cumulativePositiveRatio;
    }

    public double getPostiveCountInTotalPositiveCount() {
        return postiveCountInTotalPositiveCount;
    }

    public void setPostiveCountInTotalPositiveCount(double postiveCountInTotalPositiveCount) {
        this.postiveCountInTotalPositiveCount = postiveCountInTotalPositiveCount;
    }

    public double getCumulativePostiveCountInTotalPositiveCount() {
        return cumulativePostiveCountInTotalPositiveCount;
    }

    public void setCumulativePostiveCountInTotalPositiveCount(double cumulativePostiveCountInTotalPositiveCount) {
        this.cumulativePostiveCountInTotalPositiveCount = cumulativePostiveCountInTotalPositiveCount;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
