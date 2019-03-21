package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("模型名选项")
public class ModelNameSelectVO {

    @ApiModelProperty(value = "模型ID", example = "1")
    private long id;
    @ApiModelProperty(value = "模型名称", example = "小微-A卡")
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

