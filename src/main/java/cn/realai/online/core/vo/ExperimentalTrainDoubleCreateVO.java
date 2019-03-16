package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel
public class ExperimentalTrainDoubleCreateVO {

    @ApiModelProperty(value = "实验的id")
    private long trainId;

    @ApiModelProperty(value = "全局变量要选中的ID")
    private List<Long> variableIdList;

    public long getTrainId() {
        return trainId;
    }

    public void setTrainId(long trainId) {
        this.trainId = trainId;
    }

    public List<Long> getVariableIdList() {
        return variableIdList;
    }

    public void setVariableIdList(List<Long> variableIdList) {
        this.variableIdList = variableIdList;
    }
}
