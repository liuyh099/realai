package cn.realai.online.userandperm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@ApiModel
public class UserAddVO {

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    @Length(max = 20, min = 1, message = "用户名为1-20个字节")
    private String name;
    //部门名称
    @Length(max = 20, min = 0, message = "部门名称为0-20个字节")
    @ApiModelProperty(value = "部门名称")
    private String department;
    //部门职位
    @Length(max = 20, min = 0, message = "职位为0-20个字节")
    @ApiModelProperty(value = "职位")
    private String position;
    //手机号码
    @Length(max = 11, min = 11, message = "手机号码为11位")
    @ApiModelProperty(value = "手机号码")
    @NotBlank(message = "手机号码不能为空")
    private String phoneNumber;
    //角色名称

    @NotNull(message = "角色不能为空")
    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$",message = "密码至少8位，数字+字母")
    @ApiModelProperty(value = "密码")
    private String pwd;

    @Length(max = 100, message = "备注最大为100个字节")
    @ApiModelProperty(value = "备注")
    private String note;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
