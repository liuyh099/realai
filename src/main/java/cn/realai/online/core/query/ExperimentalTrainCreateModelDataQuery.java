package cn.realai.online.core.query;

import cn.realai.online.core.query.PageQuery;
import io.swagger.annotations.ApiModelProperty;

public class ExperimentalTrainCreateModelDataQuery extends PageQuery {

    @ApiModelProperty(value = "实验Id",name = "实验Id")
    private long id;
    @ApiModelProperty(value = "数据类型",name = "实验Id",example = "2:同质 1:异质")
    private int variableType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getVariableType() {
        return variableType;
    }

    public void setVariableType(int variableType) {
        this.variableType = variableType;
    }
}
