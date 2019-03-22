package cn.realai.online.core.bo;

import cn.realai.online.core.entity.PersonalComboResultSet;
import io.swagger.annotations.ApiModelProperty;

public class PersonalComboResultSetBO extends PersonalComboResultSet {

    //变量名1

    private String variableName1;

    //变量含义1

    private String variableMeaning1;


    //变量名2

    private String variableName2;

    //变量含义2

    private String variableMeaning2;


    //变量名3

    private String variableName3;

    //变量含义3

    private String variableMeaning3;

    @Override
    public String getVariableName1() {
        return variableName1;
    }

    @Override
    public void setVariableName1(String variableName1) {
        this.variableName1 = variableName1;
    }

    public String getVariableMeaning1() {
        return variableMeaning1;
    }

    public void setVariableMeaning1(String variableMeaning1) {
        this.variableMeaning1 = variableMeaning1;
    }

    @Override
    public String getVariableName2() {
        return variableName2;
    }

    @Override
    public void setVariableName2(String variableName2) {
        this.variableName2 = variableName2;
    }

    public String getVariableMeaning2() {
        return variableMeaning2;
    }

    public void setVariableMeaning2(String variableMeaning2) {
        this.variableMeaning2 = variableMeaning2;
    }

    @Override
    public String getVariableName3() {
        return variableName3;
    }

    @Override
    public void setVariableName3(String variableName3) {
        this.variableName3 = variableName3;
    }

    public String getVariableMeaning3() {
        return variableMeaning3;
    }

    public void setVariableMeaning3(String variableMeaning3) {
        this.variableMeaning3 = variableMeaning3;
    }
}
