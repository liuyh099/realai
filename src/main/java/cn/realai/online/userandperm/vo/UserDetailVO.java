package cn.realai.online.userandperm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
 * 用户详情VO
 */
@ApiModel
public class UserDetailVO {

    @ApiModelProperty(value = "用户名")
    private String name;
    //部门名称
    @Length(max = 20,min = 0,message ="用户名为0-20个字节")
    private String department;
    //部门职位
    @Length(max = 20,min = 0,message ="用户名为0-20个字节")
    private String position;
    //手机号码
    @Length(max = 11,min = 11,message ="手机号码为11位")
    private String phoneNumber;
    //角色名称
    @ApiModelProperty(value = "角色id")
    private Long  roleId;

    @ApiModelProperty(value = "角色名称")
    private String group;

    @ApiModelProperty(value = "备注")
    private String notes;


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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
