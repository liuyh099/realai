package cn.realai.online.userandperm.dao;

import cn.realai.online.userandperm.entity.RoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMenuDao {
    /**
     * 批量插入角色菜单
     *
     * @param roleMenus
     */
    void batchInsert(@Param("roleMenus") List<RoleMenu> roleMenus);

    /**
     * 根据角色Id 删除角色菜单关系
     *
     * @param ids
     */
    void deleteByRoleIds(@Param("ids")List<Long> ids);

    /**
     * 根据角色ID 查询菜单ID
     *
     * @param id
     * @return
     */
    List<Long> findIdsByRoleId(Long id);

    /**
     * 根基状态和角色id查询menu
     * @param id
     * @param i
     * @return
     */
    List<Long> findIdsByRoleIdAndStatus(@Param("id") Long id, @Param("status")int i);

    /**
     * 根据角色名称删除角色
     * @param roleId
     */
    void deleteByRoleId(@Param("roleId")Long roleId);
}
