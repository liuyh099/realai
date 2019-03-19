package cn.realai.online.userandperm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel
public class MenuTreeNodeVo {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "name")
    private String name;

    @ApiModelProperty(value = "是否选中")
    private Boolean check;

    @ApiModelProperty(value = "children")
    private List<MenuTreeNodeVo> children;

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

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

    public List<MenuTreeNodeVo> getChildren() {
        return children;
    }

    public void setChildren(List<MenuTreeNodeVo> children) {
        this.children = children;
    }
}
