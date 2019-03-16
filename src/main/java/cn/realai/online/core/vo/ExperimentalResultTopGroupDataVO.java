package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ExperimentalResultTopGroupDataVO {

    //top
    @ApiModelProperty(value = "top")
    private String top;

    //样本数量
    @ApiModelProperty(value = "样本数")
    private int sampleCount;

    //客户逾期数
    @ApiModelProperty(value = "客户逾期数")
    private int customerOverdueCount;

    //逾期率
    @ApiModelProperty(value = "逾期率")
    private Double overdueRate;

    //总逾期率 占总逾期客户比例
    @ApiModelProperty(value = "占总逾期客户比例")
    private Double totalOverdueRate;


    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public int getSampleCount() {
        return sampleCount;
    }

    public void setSampleCount(int sampleCount) {
        this.sampleCount = sampleCount;
    }

    public int getCustomerOverdueCount() {
        return customerOverdueCount;
    }

    public void setCustomerOverdueCount(int customerOverdueCount) {
        this.customerOverdueCount = customerOverdueCount;
    }

    public Double getOverdueRate() {
        return overdueRate;
    }

    public void setOverdueRate(Double overdueRate) {
        this.overdueRate = overdueRate;
    }

    public Double getTotalOverdueRate() {
        return totalOverdueRate;
    }

    public void setTotalOverdueRate(Double totalOverdueRate) {
        this.totalOverdueRate = totalOverdueRate;
    }
}
