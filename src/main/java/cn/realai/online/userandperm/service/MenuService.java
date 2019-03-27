package cn.realai.online.userandperm.service;

import cn.realai.online.userandperm.entity.SysMenu;

import java.util.List;

public interface MenuService {
    /**
     * 查询Menu信息
     *
     * @param sysMenu
     * @return
     */
    List<SysMenu> findList(SysMenu sysMenu);

    /**
     * 获得所有的menuID
     * @return
     */
    List<Long> getAllMenuIds();

    /**
     * 查询所有的权限
     * @return
     */
    List<String> getAllPermission();

    /**
     * 根据角色ID获得权限
     * @param roleId
     * @return
     */
    List<String> getPermissionByRoleId(Long roleId);
}
