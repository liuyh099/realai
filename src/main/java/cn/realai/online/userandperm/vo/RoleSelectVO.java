package cn.realai.online.userandperm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class RoleSelectVO {

    //id
    @ApiModelProperty(value = "id")
    private Long id;
    //角色名称
    @ApiModelProperty(value = "name")
    private String name;

    public Long getId() {
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
}
