package cn.realai.online.userandperm.service;

import cn.realai.online.userandperm.entity.SysMenu;

import java.util.List;

public interface MenuService {
    /**
     * 查询Menu信息
     * @param sysMenu
     * @return
     */
    List<SysMenu> findList(SysMenu sysMenu);
}
