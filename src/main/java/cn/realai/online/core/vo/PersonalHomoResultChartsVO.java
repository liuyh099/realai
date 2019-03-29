package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel
public class PersonalHomoResultChartsVO {

    @ApiModelProperty(value = "x轴")
    private List<Integer> x;

    @ApiModelProperty(value = "y轴")
    private List<String> y;

    @ApiModelProperty(value = "数据")
    private List<List<Object>> data;

    @ApiModelProperty(value = "min")
    private Double min;

    @ApiModelProperty(value = "max")
    private Double max;


    public List<Integer> getX() {
        return x;
    }

    public void setX(List<Integer> x) {
        this.x = x;
    }

    public List<String> getY() {
        return y;
    }

    public void setY(List<String> y) {
        this.y = y;
    }

    public List<List<Object>> getData() {
        return data;
    }

    public void setData(List<List<Object>> data) {
        this.data = data;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }
}
