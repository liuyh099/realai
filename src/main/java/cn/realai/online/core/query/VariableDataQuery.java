package cn.realai.online.core.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel
public class VariableDataQuery extends PageQuery {

    //训练名称
    @ApiModelProperty(value = "实验id", dataType = "long", name = "experimentId")
    @NotNull(message = "请选择实验")
    private Long experimentId;

    @ApiModelProperty(value = "模式类型", dataType = "int", name = "variableType", notes = "1:异质 2:同质", example = "1:异质 2:同质")
    @NotNull(message = "请选择数据类型")
    private Integer variableType;

    public Long getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(Long experimentId) {
        this.experimentId = experimentId;
    }

    public Integer getVariableType() {
        return variableType;
    }

    public void setVariableType(Integer variableType) {
        this.variableType = variableType;
    }
}
