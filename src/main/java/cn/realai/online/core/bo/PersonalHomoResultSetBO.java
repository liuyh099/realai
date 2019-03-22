package cn.realai.online.core.bo;

import cn.realai.online.core.entity.PersonalHomoResultSet;
import io.swagger.annotations.ApiModelProperty;

public class PersonalHomoResultSetBO extends PersonalHomoResultSet {
    //变量名1
    @ApiModelProperty(value = "变量名称")
    private String variableName;

    //变量含义1
    @ApiModelProperty(value = "变量含义")
    private String variableMeaning;

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
}
