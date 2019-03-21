package cn.realai.online.core.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "X轴变量数据")
public class VariableDataBO {

    //实验id
    @ApiModelProperty(value = "实验id")
    private long experimentId;

    //名称
    @ApiModelProperty(value = "名称", example = "名称")
    private String name;

    //数据类型
    @ApiModelProperty(value = "数据类型")
    private String dataType;

    //最大值
    @ApiModelProperty(value = "最大值")
    private String max;

    //最小值
    @ApiModelProperty(value = "最小值")
    private String min;

    //平均值
    @ApiModelProperty(value = "平均值")
    private String mean;

    //中间值 中位数
    @ApiModelProperty(value = "中间值")
    private String median;

    //25%
    @ApiModelProperty(value = "25%")
    private String percent25;

    //75%
    @ApiModelProperty(value = "75%")
    private String percent75;

    //模式类型
    @ApiModelProperty(value = "模式类型")
    private int variableType;

    public long getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(long experimentId) {
        this.experimentId = experimentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public String getMedian() {
        return median;
    }

    public void setMedian(String median) {
        this.median = median;
    }

    public String getPercent25() {
        return percent25;
    }

    public void setPercent25(String percent25) {
        this.percent25 = percent25;
    }

    public String getPercent75() {
        return percent75;
    }

    public void setPercent75(String percent75) {
        this.percent75 = percent75;
    }

    public int getVariableType() {
        return variableType;
    }

    public void setVariableType(int variableType) {
        this.variableType = variableType;
    }
}
