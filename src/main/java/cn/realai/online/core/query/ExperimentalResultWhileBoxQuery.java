package cn.realai.online.core.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ExperimentalResultWhileBoxQuery extends PageQuery {

    @ApiModelProperty(value = "实验ID")
    private long experimentId;
    @ApiModelProperty(value = "分组ID")
    private long groupId;
    @ApiModelProperty(value = "变量类型 1:同质 2:异质 为空表示查询所有", example = "1:同质 2:异质 为空表示查询所有")
    private int variableType;
    @ApiModelProperty(value = "请求类型 image:请求图片 normal:普通请求", example = "image:请求图片 normal:普通请求")
    private String type;

    public long getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(long experimentId) {
        this.experimentId = experimentId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public int getVariableType() {
        return variableType;
    }

    public void setVariableType(int variableType) {
        this.variableType = variableType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
