package cn.realai.online.userandperm.entity;

/**
 * 角色菜单关系
 */
public class RoleMenu {

    // 角色ID
    private Long roleId;

    // 菜单ID
    private Long menuId;


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}
