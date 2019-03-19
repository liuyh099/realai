package cn.realai.online.userandperm.business.impl;

import cn.realai.online.common.page.PageBO;
import cn.realai.online.core.query.PageQuery;
import cn.realai.online.userandperm.bo.RoleBO;
import cn.realai.online.userandperm.business.RoleBusiness;
import cn.realai.online.userandperm.entity.RoleMenu;
import cn.realai.online.userandperm.entity.SysRole;
import cn.realai.online.userandperm.service.RoleMenuService;
import cn.realai.online.userandperm.service.RoleService;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoleBusinessImpl implements RoleBusiness {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public PageBO<RoleBO> list(PageQuery pageQuery) {
        Page page = PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize());
        List<SysRole> list = roleService.list(new SysRole());
        List<RoleBO> result = JSON.parseArray(JSON.toJSONString(list), RoleBO.class);
        return new PageBO<RoleBO>(result, pageQuery.getPageSize(), pageQuery.getPageNum(), page.getTotal(), page.getPages());
    }

    @Override
    @Transactional(readOnly = false)
    public boolean insert(RoleBO roleBO) {

        //检查名称是否存在
        if (!checkName(roleBO.getName())) {
            return false;
        }

        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(roleBO, sysRole);

        //插入角色
        Integer count = roleService.insert(sysRole);
        if (count <= 0) {
            return false;
        }

        List<Long> menuIds = roleBO.getMenu();
        if (CollectionUtils.isEmpty(menuIds)) {
            return true;
        }

        //不为空的情况下插入角色菜单关系
        List<RoleMenu> roleMenus = new ArrayList<>(menuIds.size());
        for (Long menuId : menuIds) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(sysRole.getId());
            roleMenu.setMenuId(menuId);
        }
        roleMenuService.batchInsert(roleMenus);

        return true;
    }

    @Override
    public Boolean checkName(String checkName) {
        SysRole sysRole = new SysRole();
        sysRole.setName(checkName);
        List<SysRole> list = roleService.list(sysRole);
        if (CollectionUtils.isEmpty(list)) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = false)
    public Integer delete(List<Long> ids) {

        //删除角色表
        int count = roleService.delete(ids);
        //删除角色菜单表
        roleMenuService.deleteByRoleIds(ids);
        return count;
    }
}
