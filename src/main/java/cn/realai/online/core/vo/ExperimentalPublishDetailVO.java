package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "实验发布详情信息")
public class ExperimentalPublishDetailVO {

    @ApiModelProperty(value = "模型名称")
    private String modelName;

    @ApiModelProperty(value = "服务类型")
    private int type;

    @ApiModelProperty(value = "算法类型")
    private String algorithmType;

    @ApiModelProperty(value = "样本综诉")
    private String sampleReview;

    @ApiModelProperty(value = "模型表现数据")
    private List<ModelPerformanceVO> modelPerformanceList;




}
