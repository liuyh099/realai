package cn.realai.online.core.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel
public class WhileBoxScoreCardQuery extends PageQuery {

    @ApiModelProperty(value = "实验ID")
    @NotNull(message = "模型Id不能为空")
    private Long modelId;
    @ApiModelProperty(value = "分组ID")
    @NotNull(message = "分组ID必填")
    private Long groupId;
    @ApiModelProperty(value = "变量类型 2:同质 1:异质 为空表示查询所有", example = "2:同质 1:异质 为空表示查询所有")
    private Integer variableType;
    @ApiModelProperty(value = "请求类型 image:请求图片 normal:普通请求", example = "image:请求图片 normal:普通请求")
    private String type;

    @ApiModelProperty(value="查询过滤条件（while: 过滤异常点、全局点 其他：不过滤） 白盒决策这里不传也为while")
    private String searchType;

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Integer getVariableType() {
        return variableType;
    }

    public void setVariableType(Integer variableType) {
        this.variableType = variableType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }
}
