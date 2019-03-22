package cn.realai.online.userandperm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class RoleVO {
    @ApiModelProperty(value = "角色ID")
    private Long id;
    //角色名称
    @ApiModelProperty(value = "角色名称")
    private String name;
    //创建时间

    @ApiModelProperty(value = "创建时间")
    private Long createTime;
    //创建备注
    @ApiModelProperty(value = "备注")
    private String notes;

    @ApiModelProperty(value = "用户名称")
    private String createUser;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
