package cn.realai.online.userandperm.service;

import cn.realai.online.userandperm.entity.SysMenu;

import java.util.List;

/**
 * 菜单service
 */
public interface SysMenuService {

    List<SysMenu> findList(SysMenu sysMenu);
}
