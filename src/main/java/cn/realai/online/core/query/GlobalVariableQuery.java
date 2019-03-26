package cn.realai.online.core.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class GlobalVariableQuery extends PageQuery {

    @ApiModelProperty(value = "实验ID")
    private Long trainId;

    @ApiModelProperty(value = "变量类型 1:同质 2:异质 为空表示查询所有", example = "1:同质 2:异质 为空表示查询所有")
    private Integer sampleType;

    @ApiModelProperty(value = "请求类型 image:请求图片 normal:普通请求", example = "image:请求图片 normal:普通请求")
    private String type;

    private Long groupId;

    public Long getGroupId() {
        return groupId;
    }

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public Integer getSampleType() {
        return sampleType;
    }

    public void setSampleType(Integer sampleType) {
        this.sampleType = sampleType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
