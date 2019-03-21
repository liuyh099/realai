package cn.realai.online.userandperm.vo;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserUpdatePwdVO {

    @NotNull(message = "id不能为空")
    @ApiModelProperty(value = "用户ID")
    private Long id;

    @NotBlank(message = "密码不能为空")
    @Length(min = 8, message = "密码最短8位")
    @ApiModelProperty(value = "密码")
    private String pwd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
