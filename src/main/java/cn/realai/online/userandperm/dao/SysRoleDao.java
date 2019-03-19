package cn.realai.online.userandperm.dao;

import cn.realai.online.userandperm.entity.SysRole;

import java.util.List;

public interface SysRoleDao {
    /**
     * 查询角色列表
     * @param sysRole
     * @return
     */
    List<SysRole> findList(SysRole sysRole);

    /**
     * 插入新用户
     * @param sysRole
     * @return
     */
    Integer insert(SysRole sysRole);

    /**
     * 删除角色信息
     * @param delete
     * @return
     */
    Integer delete(List<Long> delete);
}
