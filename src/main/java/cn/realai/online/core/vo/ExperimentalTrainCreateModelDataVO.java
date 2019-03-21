package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ExperimentalTrainCreateModelDataVO {

    //主键
    @ApiModelProperty(value = "id")
    private long id;

    //实验id
    @ApiModelProperty(value = "实验ID")
    private long experimentId;

    //名称
    @ApiModelProperty(value = "名称")
    private String name;

    //数据类型
    @ApiModelProperty(value = "数据类型")
    private String dataType;

    //总数
    @ApiModelProperty(value = "总数")
    private String count;

    //最大值
    @ApiModelProperty(value = "max")
    private String max;

    //最小值
    @ApiModelProperty(value = "min")
    private String min;

    //平均值
    @ApiModelProperty(value = "mean")
    private String mean;

    //中间值 中位数
    @ApiModelProperty(value = "median")
    private String median;

    //25%
    @ApiModelProperty(value = "percent25")
    private String percent25;

    //75%
    @ApiModelProperty(value = "percent75")
    private String percent75;

    //推荐删除
    @ApiModelProperty(value = "推荐删除标识", example = "2:推荐删除 1:不推荐删除")
    private int recommendedDelete;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
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

    public int getRecommendedDelete() {
        return recommendedDelete;
    }

    public void setRecommendedDelete(int recommendedDelete) {
        this.recommendedDelete = recommendedDelete;
    }
}
