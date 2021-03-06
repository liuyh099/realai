package cn.realai.online.core.query;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ExperimentalResultWhileBoxQuery extends PageQuery {

    @ApiModelProperty(value = "实验ID")
    private Long experimentId;
    @ApiModelProperty(value = "分组ID")
    @NotNull
    private Long groupId;
    @ApiModelProperty(value = "变量类型 2:同质 1:异质 为空表示查询所有", example = "2:同质 1:异质 为空表示查询所有")
    private Integer variableType;
    @ApiModelProperty(value = "请求类型 image:请求图片 normal:普通请求", example = "image:请求图片 normal:普通请求")
    private String type;

    @ApiModelProperty(value="查询过滤条件（while: 过滤异常点、全局点 其他：不过滤）")
    private String searchType;

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public Long getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(Long experimentId) {
        this.experimentId = experimentId;
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
}
