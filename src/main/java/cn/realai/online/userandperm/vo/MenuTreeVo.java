package cn.realai.online.userandperm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel
public class MenuTreeVo {

    @ApiModelProperty(value = "半选id")
    private List<Long> halfCheck;

    @ApiModelProperty(value = "全选id")
    private List<Long> check;

    @ApiModelProperty(value = "菜单")
    private List<MenuTreeNodeVo> menus;


    public List<Long> getHalfCheck() {
        return halfCheck;
    }

    public void setHalfCheck(List<Long> halfCheck) {
        this.halfCheck = halfCheck;
    }

    public List<Long> getCheck() {
        return check;
    }

    public void setCheck(List<Long> check) {
        this.check = check;
    }

    public List<MenuTreeNodeVo> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuTreeNodeVo> menus) {
        this.menus = menus;
    }
}
