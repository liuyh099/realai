package cn.realai.online.userandperm.dao;

import cn.realai.online.userandperm.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单dao
 */
public interface SysMenuDao {

    /**
     * 查询菜单列表数据
     *
     * @param sysMenu
     * @return
     */
    List<SysMenu> findList(SysMenu sysMenu);

    /**
     * 查询所有的菜单列表ID
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
    List<String> getPermissionByRoleId(@Param("roleId") Long roleId);
}
