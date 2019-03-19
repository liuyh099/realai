package cn.realai.online.userandperm.service.impl;

import cn.realai.online.userandperm.dao.SysRoleDao;
import cn.realai.online.userandperm.entity.SysRole;
import cn.realai.online.userandperm.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    @Autowired
    private SysRoleDao sysRoleDao;

    @Override
    public List<SysRole> list(SysRole sysRole) {
        return sysRoleDao.findList(sysRole);
    }

    @Override
    public Integer insert(SysRole sysRole) {
        return sysRoleDao.insert(sysRole);
    }

    @Override
    public Integer delete(List<Long> delete) {
        return sysRoleDao.delete(delete);
    }

    @Override
    public SysRole get(Long id) {
        return sysRoleDao.get(id);
    }

    @Override
    public Integer update(SysRole sysRole) {
        return sysRoleDao.update(sysRole);
    }
}
