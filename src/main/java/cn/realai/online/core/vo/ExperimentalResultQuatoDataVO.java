package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ExperimentalResultQuatoDataVO {

    @ApiModelProperty(value = "组ID")
    private Long id;

    //最小概率
    @ApiModelProperty(value = "最小概率")
    private Double minPro;

    @ApiModelProperty(value = "最小概率对应分数")
    private Integer minProScore;

    @ApiModelProperty(value = "最大概率")
    private Double maxPro;


    @ApiModelProperty(value = "最大概率对应分数")
    private Integer maxProScore;

    @ApiModelProperty(value = "未逾期总数")
    private Integer negtiveCount;

    @ApiModelProperty(value = "逾期总数")
    private Integer postiveCount;

    @ApiModelProperty(value = "总数")
    private Integer count;

    @ApiModelProperty(value = "未逾期总数/逾期总数")
    private Double negtiveCountInPostiveCount;

    @ApiModelProperty(value = "逾期率")
    private Double positiveRatio;

    @ApiModelProperty(value = "累计逾期率")
    private Double cumulativePositiveRatio;

    @ApiModelProperty(value = "逾期数/总体逾期数")
    private Double postiveCountInTotalPositiveCount;

    //
    @ApiModelProperty(value = "逾期数/总体逾期数")
    private Double cumulativePostiveCountInTotalPositiveCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMinPro() {
        return minPro;
    }

    public void setMinPro(Double minPro) {
        this.minPro = minPro;
    }

    public Integer getMinProScore() {
        return minProScore;
    }

    public void setMinProScore(Integer minProScore) {
        this.minProScore = minProScore;
    }

    public Double getMaxPro() {
        return maxPro;
    }

    public void setMaxPro(Double maxPro) {
        this.maxPro = maxPro;
    }

    public Integer getMaxProScore() {
        return maxProScore;
    }

    public void setMaxProScore(Integer maxProScore) {
        this.maxProScore = maxProScore;
    }

    public Integer getNegtiveCount() {
        return negtiveCount;
    }

    public void setNegtiveCount(Integer negtiveCount) {
        this.negtiveCount = negtiveCount;
    }

    public Integer getPostiveCount() {
        return postiveCount;
    }

    public void setPostiveCount(Integer postiveCount) {
        this.postiveCount = postiveCount;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getNegtiveCountInPostiveCount() {
        return negtiveCountInPostiveCount;
    }

    public void setNegtiveCountInPostiveCount(Double negtiveCountInPostiveCount) {
        this.negtiveCountInPostiveCount = negtiveCountInPostiveCount;
    }

    public Double getPositiveRatio() {
        return positiveRatio;
    }

    public void setPositiveRatio(Double positiveRatio) {
        this.positiveRatio = positiveRatio;
    }

    public Double getCumulativePositiveRatio() {
        return cumulativePositiveRatio;
    }

    public void setCumulativePositiveRatio(Double cumulativePositiveRatio) {
        this.cumulativePositiveRatio = cumulativePositiveRatio;
    }

    public Double getPostiveCountInTotalPositiveCount() {
        return postiveCountInTotalPositiveCount;
    }

    public void setPostiveCountInTotalPositiveCount(Double postiveCountInTotalPositiveCount) {
        this.postiveCountInTotalPositiveCount = postiveCountInTotalPositiveCount;
    }

    public Double getCumulativePostiveCountInTotalPositiveCount() {
        return cumulativePostiveCountInTotalPositiveCount;
    }

    public void setCumulativePostiveCountInTotalPositiveCount(Double cumulativePostiveCountInTotalPositiveCount) {
        this.cumulativePostiveCountInTotalPositiveCount = cumulativePostiveCountInTotalPositiveCount;
    }
}
