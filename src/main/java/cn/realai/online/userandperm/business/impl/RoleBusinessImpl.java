package cn.realai.online.userandperm.business.impl;

import cn.realai.online.common.page.PageBO;
import cn.realai.online.core.query.PageQuery;
import cn.realai.online.userandperm.bo.MenuTreeNodeBO;
import cn.realai.online.userandperm.bo.RoleBO;
import cn.realai.online.userandperm.bo.RoleDetailBO;
import cn.realai.online.userandperm.business.RoleBusiness;
import cn.realai.online.userandperm.entity.RoleMenu;
import cn.realai.online.userandperm.entity.SysMenu;
import cn.realai.online.userandperm.entity.SysRole;
import cn.realai.online.userandperm.entity.User;
import cn.realai.online.userandperm.service.MenuService;
import cn.realai.online.userandperm.service.RoleMenuService;
import cn.realai.online.userandperm.service.RoleService;
import cn.realai.online.userandperm.service.UserService;
import cn.realai.online.util.UserUtils;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoleBusinessImpl implements RoleBusiness {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserService userService;

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
        sysRole.setCreateTime(System.currentTimeMillis());
        sysRole.setCreateUserId(UserUtils.getUser().getId());
        Integer count = roleService.insert(sysRole);
        if (count <= 0) {
            return false;
        }

        List<Long> menuIds = roleBO.getHalfMenu();
        if (!CollectionUtils.isEmpty(menuIds)) {
            batchInsertRoleMenu(sysRole, menuIds, 2);
        }
        menuIds = roleBO.getCheckMenu();
        if (!CollectionUtils.isEmpty(menuIds)) {
            batchInsertRoleMenu(sysRole, menuIds, 1);
        }
        return true;
    }

    private void batchInsertRoleMenu(SysRole sysRole, List<Long> menuIds, Integer type) {
        //不为空的情况下插入角色菜单关系
        List<RoleMenu> roleMenus = new ArrayList<>(menuIds.size());
        for (Long menuId : menuIds) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(sysRole.getId());
            roleMenu.setMenuId(menuId);
            roleMenu.setCheckStatus(type);
            roleMenus.add(roleMenu);
        }
        roleMenuService.batchInsert(roleMenus);
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

    @Override
    public List<MenuTreeNodeBO> menuTree() {
        return menuTree(null, false);
    }

    @Override
    public RoleDetailBO detail(Long id) {
        return getRoleDetailBO(id, true);
    }

    private RoleDetailBO getRoleDetailBO(Long id, Boolean clearNocheck) {
        SysRole sysRole = roleService.get(id);
        if (ObjectUtils.isEmpty(sysRole)) {
            return null;
        }

        RoleDetailBO roleDetailBO = new RoleDetailBO();
        BeanUtils.copyProperties(sysRole, roleDetailBO);

        List<Long> check = roleMenuService.findIdsByRoleIdAndStatus(sysRole.getId(), 1);
        List<Long> half = roleMenuService.findIdsByRoleIdAndStatus(sysRole.getId(), 2);
//        List<Long> menuIds = roleMenuService.findIdsByRoleId(sysRole.getId());
//        if (!ObjectUtils.isEmpty(sysRole)) {
//            List<MenuTreeNodeBO> nodeTree = menuTree(menuIds, clearNocheck);
//            roleDetailBO.setMenu(nodeTree);
//        }
        roleDetailBO.setHalfCheck(half);
        roleDetailBO.setCheck(check);
        return roleDetailBO;
    }

    @Override
    public RoleDetailBO edit(Long id) {
        return getRoleDetailBO(id, false);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean update(RoleBO roleBO) {

        if (!checkName(roleBO.getName())) {
            return false;
        }

        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(roleBO, sysRole);

        Integer count = roleService.update(sysRole);
        if (count <= 0) {
            return false;
        }
        List<Long> ids = new ArrayList<>();
        ids.add(sysRole.getId());
        roleMenuService.deleteByRoleIds(ids);

        List<Long> menuIds = roleBO.getHalfMenu();
        if (!CollectionUtils.isEmpty(menuIds)) {
            batchInsertRoleMenu(sysRole, menuIds, 2);
        }
        menuIds = roleBO.getCheckMenu();
        if (!CollectionUtils.isEmpty(menuIds)) {
            batchInsertRoleMenu(sysRole, menuIds, 1);
        }
        return true;
    }

    @Override
    public List<MenuTreeNodeBO> findIndexMenu() {
        User user = UserUtils.getUser();
        if (ObjectUtils.isEmpty(user)) {
            return null;
        }
        user = userService.get(user.getId());
        List<Long> menuIds = null;
        if (UserUtils.isAdmin(user)) {
            //admin 获得所有的菜单
             menuIds = menuService.getAllMenuIds();
        } else {
            menuIds = roleMenuService.findIdsByRoleId(user.getRoleId());
        }

        return menuTree(menuIds, true);
    }

    @Override
    public List<RoleBO> findList() {
        SysRole sysRole = new SysRole();
        List<SysRole> list = roleService.list(sysRole);
        List<RoleBO> result = JSON.parseArray(JSON.toJSONString(list), RoleBO.class);
        return result;
    }


    /**
     * 构建菜单树
     *
     * @param ids          选中的菜单
     * @param clearNoCheck 是否清除未选中菜单
     * @return
     */
    private List<MenuTreeNodeBO> menuTree(List<Long> ids, boolean clearNoCheck) {
        SysMenu sysMenu = new SysMenu();
        sysMenu.setParentId(0L);
        List<SysMenu> parentMenus = menuService.findList(sysMenu);
        if (CollectionUtils.isEmpty(parentMenus)) {
            return null;
        }

        List<MenuTreeNodeBO> menuTreeNodeBOList = new ArrayList<>();
        for (SysMenu sysMenu1 : parentMenus) {
            MenuTreeNodeBO menuTreeNodeBO = new MenuTreeNodeBO();
            BeanUtils.copyProperties(sysMenu1, menuTreeNodeBO);
            if (ids != null && ids.contains(sysMenu1.getId())) {
                menuTreeNodeBO.setCheck(true);
            } else {
                if (clearNoCheck) {
                    continue;
                }
            }

            SysMenu sysMenuChild = new SysMenu();
            sysMenuChild.setParentId(sysMenu1.getId());
            List<SysMenu> childrenList = menuService.findList(sysMenuChild);
            if (CollectionUtils.isEmpty(childrenList)) {
                menuTreeNodeBOList.add(menuTreeNodeBO);
                continue;
            }

            List<MenuTreeNodeBO> childMenuTreeNodeBOList = new ArrayList<>(childrenList.size());
            for (SysMenu sysMenuTemp : childrenList) {
                MenuTreeNodeBO menuTreeNodeBOChild = new MenuTreeNodeBO();
                BeanUtils.copyProperties(sysMenuTemp, menuTreeNodeBOChild);
                if (ids != null && ids.contains(sysMenuTemp.getId())) {
                    menuTreeNodeBOChild.setCheck(true);
                } else {
                    if (clearNoCheck) {
                        continue;
                    }
                }
                childMenuTreeNodeBOList.add(menuTreeNodeBOChild);
            }
            menuTreeNodeBO.setChildren(childMenuTreeNodeBOList);
            menuTreeNodeBOList.add(menuTreeNodeBO);
        }

        return menuTreeNodeBOList;
    }

}
