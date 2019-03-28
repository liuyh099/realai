package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ExperimentalTrainCreateModelCheckVO {

    @ApiModelProperty(value = "name",  dataType = "String")
    private String name;

    @ApiModelProperty(value = "预处理结果 1或者为空表示:未处理 2表示(只有为2的时候才可以一键建模):处理完成 3表示:处理异常")
    private Integer preFinish;

    @ApiModelProperty(value = "预处理错误信息")
    private String errMsg;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPreFinish() {
        return preFinish;
    }

    public void setPreFinish(Integer preFinish) {
        this.preFinish = preFinish;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
