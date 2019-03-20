package cn.realai.online.core.query;

import cn.realai.online.core.query.PageQuery;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class ExperimentalTrainCreateModelDataQuery extends PageQuery {

    @ApiModelProperty(value = "实验Id",name = "实验Id")
    @NotNull(message = "请选择实验")
    private Long id;
    @ApiModelProperty(value = "数据类型",name = "数据类型",example = "2:同质 1:异质")
    @NotNull(message = "请选择数据类型")
    private Integer variableType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVariableType() {
        return variableType;
    }

    public void setVariableType(Integer variableType) {
        this.variableType = variableType;
    }
}
