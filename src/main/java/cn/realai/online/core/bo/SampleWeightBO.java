package cn.realai.online.core.bo;

import cn.realai.online.core.entity.SampleWeight;
import io.swagger.annotations.ApiModelProperty;

/**
 * 样本权重BO
 */
public class SampleWeightBO extends SampleWeight {

    //变量含义 来源于 VariableData 表
    private String meaning;


    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}
