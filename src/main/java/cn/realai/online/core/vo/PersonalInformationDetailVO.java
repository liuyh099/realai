package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class PersonalInformationDetailVO {

    @ApiModelProperty(value = "id")
    private long id;

    @ApiModelProperty(value = "ID", example = "页面展示的ID")
    private String personalId;
    //标签
    @ApiModelProperty(value = "标签")
    private int label;
    //概率
    @ApiModelProperty(value = "概率")
    private double probability;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }
}
