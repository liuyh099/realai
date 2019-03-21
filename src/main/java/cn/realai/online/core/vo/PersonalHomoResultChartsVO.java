package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel
public class PersonalHomoResultChartsVO {

    @ApiModelProperty(value = "x轴")
    private List<String> x;

    @ApiModelProperty(value = "y轴")
    private List<Integer> y;

    @ApiModelProperty(value = "数据")
    private List<List<Double>> data;


    public List<String> getX() {
        return x;
    }

    public void setX(List<String> x) {
        this.x = x;
    }

    public List<Integer> getY() {
        return y;
    }

    public void setY(List<Integer> y) {
        this.y = y;
    }

    public List<List<Double>> getData() {
        return data;
    }

    public void setData(List<List<Double>> data) {
        this.data = data;
    }
}
