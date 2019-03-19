package cn.realai.online.userandperm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

@ApiModel
public class UserLoginVo {

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty("用户名")
    private String name;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty("密码")
    private String pwd;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
