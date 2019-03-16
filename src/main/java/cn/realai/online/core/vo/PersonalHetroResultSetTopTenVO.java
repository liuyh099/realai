package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 来源于 PersonalHetroResultSet 需要根据ID 去变量表查询名称和含义
 */
@ApiModel
public class PersonalHetroResultSetTopTenVO {

    //变量名1
    @ApiModelProperty(value = "变量名称")
    private String variableName;

    //变量含义1
    @ApiModelProperty(value = "变量含义")
    private String variableMeaning;

    //值1
    @ApiModelProperty(value = "值")
    private String value;

    @ApiModelProperty(value = "权重")
    private double weight;


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
