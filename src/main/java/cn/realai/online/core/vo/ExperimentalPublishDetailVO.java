package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "实验发布详情信息")
public class ExperimentalPublishDetailVO {

    @ApiModelProperty(value = "模型名称")
    private String modelName;

    @ApiModelProperty(value = "服务类型,1:风控A卡 2:风控B卡 3:风控C卡 4:营销")
    private int type;

    @ApiModelProperty(value = "服务类型中文名称")
    private String typeName;

    @ApiModelProperty(value = "算法类型")
    private String algorithmType;

    @ApiModelProperty(value = "样本综诉")
    private String sampleReview;

    @ApiModelProperty(value = "模型表现数据")
    private List<ModelPerformanceVO> modelPerformanceList;

    @ApiModelProperty(value = "服务名称")
    private String serviceName;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getAlgorithmType() {
        return algorithmType;
    }

    public void setAlgorithmType(String algorithmType) {
        this.algorithmType = algorithmType;
    }

    public String getSampleReview() {
        return sampleReview;
    }

    public void setSampleReview(String sampleReview) {
        this.sampleReview = sampleReview;
    }

    public List<ModelPerformanceVO> getModelPerformanceList() {
        return modelPerformanceList;
    }

    public void setModelPerformanceList(List<ModelPerformanceVO> modelPerformanceList) {
        this.modelPerformanceList = modelPerformanceList;
    }
}
