package cn.realai.online.userandperm.service.impl;

import cn.realai.online.userandperm.dao.SysMenuDao;
import cn.realai.online.userandperm.entity.SysMenu;
import cn.realai.online.userandperm.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MenuServiceImpl implements MenuService {

    @Autowired
    private SysMenuDao sysMenuDao;

    @Override
    public List<SysMenu> findList(SysMenu sysMenu) {
        return sysMenuDao.findList(sysMenu);
    }

    @Override
    public List<Long> getAllMenuIds() {
        return sysMenuDao.getAllMenuIds();
    }
}
