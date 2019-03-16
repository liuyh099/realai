package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ExperimentalResultQuatoDataVO {

    @ApiModelProperty(value = "组ID")
    private long id;

    //最小概率
    @ApiModelProperty(value = "最小概率")
    private double minPro;

    @ApiModelProperty(value = "最小概率对应分数")
    private int minProScore;

    @ApiModelProperty(value = "最大概率")
    private double maxPro;


    @ApiModelProperty(value = "最大概率对应分数")
    private int maxProScore;

    @ApiModelProperty(value = "未逾期总数")
    private int negtiveCount;

    @ApiModelProperty(value = "逾期总数")
    private int postiveCount;

    @ApiModelProperty(value = "总数")
    private int count;

    @ApiModelProperty(value = "未逾期总数/逾期总数")
    private double negtiveCountInPostiveCount;

    @ApiModelProperty(value = "逾期率")
    private double positiveRatio;

    @ApiModelProperty(value = "累计逾期率")
    private double cumulativePositiveRatio;

    @ApiModelProperty(value = "逾期数/总体逾期数")
    private double postiveCountInTotalPositiveCount;

    //
    @ApiModelProperty(value = "逾期数/总体逾期数")
    private double cumulativePostiveCountInTotalPositiveCount;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
