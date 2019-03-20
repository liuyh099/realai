package cn.realai.online.core.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class VariableDataQuery extends PageQuery {

    //训练名称
    @ApiModelProperty(value="实验id",dataType="long",name="experimentId")
    private long experimentId;

    @ApiModelProperty(value="模式类型",dataType="int",name="variableType",notes = "1:异质 2:同质",example = "1:异质 2:同质")
    private Integer variableType;

    public long getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(long experimentId) {
        this.experimentId = experimentId;
    }

    public Integer getVariableType() {
        return variableType;
    }

    public void setVariableType(Integer variableType) {
        this.variableType = variableType;
    }
}
