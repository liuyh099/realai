package cn.realai.online.userandperm.service;

import cn.realai.online.userandperm.entity.SysRole;

import java.util.List;

public interface RoleService {
    /**
     * 查询所有的角色
     * @param sysRole
     * @return
     */
    List<SysRole> list(SysRole sysRole);

    /**
     * 保存角色
     * @param sysRole
     * @return
     */
    Integer insert(SysRole sysRole);

    /**
     * 删除角色信息
     * @param ids
     * @return
     */
    Integer delete(List<Long> ids);
}
