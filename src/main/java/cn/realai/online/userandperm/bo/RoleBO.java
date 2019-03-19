package cn.realai.online.userandperm.bo;

import cn.realai.online.userandperm.entity.SysRole;

import java.util.List;

public class RoleBO extends SysRole {

    private String createUser;

    private List<Long> menu;

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public List<Long> getMenu() {
        return menu;
    }

    public void setMenu(List<Long> menu) {
        this.menu = menu;
    }
}
