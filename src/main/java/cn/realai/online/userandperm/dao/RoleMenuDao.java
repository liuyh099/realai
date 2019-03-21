package cn.realai.online.userandperm.dao;

import cn.realai.online.userandperm.entity.RoleMenu;

import java.util.List;

public interface RoleMenuDao {
    /**
     * 批量插入角色菜单
     *
     * @param roleMenus
     */
    void batchInsert(List<RoleMenu> roleMenus);

    /**
     * 根据角色Id 删除角色菜单关系
     *
     * @param ids
     */
    void deleteByRoleIds(List<Long> ids);

    /**
     * 根据角色ID 查询菜单ID
     *
     * @param id
     * @return
     */
    List<Long> findIdsByRoleId(Long id);
}
