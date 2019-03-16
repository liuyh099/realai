package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class PersonalHomoResultSetVO {

    //用户消费流水序号（第几条流水）(x轴)
    @ApiModelProperty(value = "k")
    private int k;

    //变量名1
    @ApiModelProperty(value = "变量名称")
    private String variableName;

    //变量含义1
    @ApiModelProperty(value = "变量含义")
    private String variableMeaning;

    //值
    @ApiModelProperty(value = "值")
    private String value;

    //权重(显示值)
    @ApiModelProperty(value = "权重")
    private double weight;

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getVariableMeaning() {
        return variableMeaning;
    }

    public void setVariableMeaning(String variableMeaning) {
        this.variableMeaning = variableMeaning;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
