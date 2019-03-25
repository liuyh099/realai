package cn.realai.online.core.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ExperimentalResultWhileBoxQuery extends PageQuery {

    @ApiModelProperty(value = "实验ID")
    private long trainId;
    @ApiModelProperty(value = "组id")
    private String groupName;
    @ApiModelProperty(value = "变量类型 1:同质 2:异质 为空表示查询所有", example = "1:同质 2:异质 为空表示查询所有")
    private int sampleType;
    @ApiModelProperty(value = "请求类型 image:请求图片 normal:普通请求", example = "image:请求图片 normal:普通请求")
    private String type;


    public long getTrainId() {
        return trainId;
    }

    public void setTrainId(long trainId) {
        this.trainId = trainId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getSampleType() {
        return sampleType;
    }

    public void setSampleType(int sampleType) {
        this.sampleType = sampleType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
