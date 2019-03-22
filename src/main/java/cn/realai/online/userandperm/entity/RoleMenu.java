package cn.realai.online.userandperm.entity;

/**
 * 角色菜单关系
 */
public class RoleMenu {

    // 角色ID
    private Long roleId;

    // 菜单ID
    private Long menuId;

    //1:全选 2:半选
    private Integer checkStatus;


    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

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
