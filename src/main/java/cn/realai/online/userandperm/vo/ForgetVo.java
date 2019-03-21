package cn.realai.online.userandperm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

@ApiModel
public class ForgetVo {

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
