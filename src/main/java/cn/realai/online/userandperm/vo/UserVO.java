package cn.realai.online.userandperm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class UserVO {
    //id
    @ApiModelProperty(value = "用户id")
    private Long id;
    //用户名
    @ApiModelProperty(value = "用户名")
    private String name;
    //部门名称
    @ApiModelProperty(value = "部门名称")
    private String department;
    //部门职位
    @ApiModelProperty(value = "部门职位")
    private String position;
    //手机号码
    @ApiModelProperty(value = "手机号码")
    private String phoneNumber;

    //角色名称
    @ApiModelProperty(value = "角色名称")
    private String group;

    @ApiModelProperty(value = "创建时间")
    private Long createTime;

    @ApiModelProperty(value = "是否是忘记密码 true:忘记密码 false:不是忘记密码")
    private boolean forgetFlag;

    private Integer forget;

    public boolean getForgetFlag() {
        if(forget!=null && forget>0){
            return true;
        }else {
            return false;
        }
    }

    public void setForgetFlag(boolean forgetFlag) {
        this.forgetFlag = forgetFlag;
    }

    public Integer getForget() {
        return forget;
    }

    public void setForget(Integer forget) {
        this.forget = forget;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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


    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
