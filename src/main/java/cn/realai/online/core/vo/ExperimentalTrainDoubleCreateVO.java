package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel
public class ExperimentalTrainDoubleCreateVO {

    @ApiModelProperty(value = "实验的id")
    @NotNull(message = "实验ID不能为空")
    private Long trainId;

    @ApiModelProperty(value = "全局变量要选中的ID")
    private List<Long> variableIdList;

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public List<Long> getVariableIdList() {
        return variableIdList;
    }

    public void setVariableIdList(List<Long> variableIdList) {
        this.variableIdList = variableIdList;
    }
}
