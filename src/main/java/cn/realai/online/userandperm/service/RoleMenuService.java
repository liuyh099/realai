package cn.realai.online.userandperm.service;

import cn.realai.online.userandperm.entity.RoleMenu;

import java.util.List;

public interface RoleMenuService {

    /**
     * 批量插入角色菜单关系
     * @param roleMenus
     */
    void batchInsert(List<RoleMenu> roleMenus);

    /**
     * 根绝角色id 删除角色菜单关系
     * @param ids
     */
    void deleteByRoleIds(List<Long> ids);
}
