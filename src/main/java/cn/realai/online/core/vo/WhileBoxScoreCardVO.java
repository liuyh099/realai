package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class WhileBoxScoreCardVO {

    @ApiModelProperty(value = "变量的ID-当请求类型是image时不返回")
    private String variableId;

    //变量来源
    @ApiModelProperty(value = "变量来源-当请求类型是image时不返回")
    private String variableName;

    //变量含义 来源于 VariableData 表
    @ApiModelProperty(value = "变量含义-当请求类型是image时不返回")
    private String meaning;

    //分箱范围 / 得分
    @ApiModelProperty(value = "分箱范围-当请求类型是image时不返回")
    private String boxBeans;

    @ApiModelProperty(value = "变量类型-当请求类型是image时不返回", example = "1:同质 2：异质")
    private int variableType;

    @ApiModelProperty(value = "权重-当请求类型是image时不返回")
    private double variableWeight;

    @ApiModelProperty(value = "图片路径-当请求类型是normal时不返回")
    private String imgUrl;


    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getBoxBeans() {
        return boxBeans;
    }

    public void setBoxBeans(String boxBeans) {
        this.boxBeans = boxBeans;
    }

    public int getVariableType() {
        return variableType;
    }

    public void setVariableType(int variableType) {
        this.variableType = variableType;
    }

    public double getVariableWeight() {
        return variableWeight;
    }

    public void setVariableWeight(double variableWeight) {
        this.variableWeight = variableWeight;
    }

    public String getVariableId() {
        return variableId;
    }

    public void setVariableId(String variableId) {
        this.variableId = variableId;
    }
}
