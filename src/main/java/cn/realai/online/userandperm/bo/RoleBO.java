package cn.realai.online.userandperm.bo;

import cn.realai.online.userandperm.entity.SysRole;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class RoleBO extends SysRole {

    private List<Long> halfMenu;

    private List<Long> checkMenu;

    public List<Long> getHalfMenu() {
        return halfMenu;
    }

    public void setHalfMenu(List<Long> halfMenu) {
        this.halfMenu = halfMenu;
    }

    public List<Long> getCheckMenu() {
        return checkMenu;
    }

    public void setCheckMenu(List<Long> checkMenu) {
        this.checkMenu = checkMenu;
    }
}
