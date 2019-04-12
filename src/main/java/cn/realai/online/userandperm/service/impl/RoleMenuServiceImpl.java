package cn.realai.online.userandperm.service.impl;

import cn.realai.online.userandperm.dao.RoleMenuDao;
import cn.realai.online.userandperm.entity.RoleMenu;
import cn.realai.online.userandperm.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoleMenuServiceImpl implements RoleMenuService {


    @Autowired
    private RoleMenuDao roleMenuDao;

    @Transactional(readOnly = false)
    @Override
    public void batchInsert(List<RoleMenu> roleMenus) {
        roleMenuDao.batchInsert(roleMenus);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteByRoleIds(List<Long> ids) {
        roleMenuDao.deleteByRoleIds(ids);
    }

    @Override
    public List<Long> findIdsByRoleId(Long id) {
        return roleMenuDao.findIdsByRoleId(id);
    }

    @Override
    public List<Long> findIdsByRoleIdAndStatus(Long id, int i) {
        return roleMenuDao.findIdsByRoleIdAndStatus(id,i);
    }

    @Override
    public void deleteByRoleId(Long id) {
        roleMenuDao.deleteByRoleId(id);
    }
}
