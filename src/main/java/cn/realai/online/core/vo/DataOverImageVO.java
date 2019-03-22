package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModelProperty;

public class DataOverImageVO {

    @ApiModelProperty(value = "分段统计图")
    private String statisticalImage;

    @ApiModelProperty(value = "实验badTop图")
    private String topImage;

    @ApiModelProperty(value = "测试ROC图")
    private String testRocImage;

    public String getStatisticalImage() {
        return statisticalImage;
    }

    public void setStatisticalImage(String statisticalImage) {
        this.statisticalImage = statisticalImage;
    }

    public String getTopImage() {
        return topImage;
    }

    public void setTopImage(String topImage) {
        this.topImage = topImage;
    }

    public String getTestRocImage() {
        return testRocImage;
    }

    public void setTestRocImage(String testRocImage) {
        this.testRocImage = testRocImage;
    }
}
