package cn.realai.online.userandperm.service;

import cn.realai.online.userandperm.entity.User;

import java.util.List;

public interface UserService {
    /**
     * 查询用户列表
     *
     * @return
     */
    List<User> list(User user);

    /**
     * 插入数据
     *
     * @param user
     */
    Integer insert(User user);

    /**
     * 根据ID集合删除用户
     *
     * @param ids
     * @return
     */
    Integer delete(List<Long> ids);

    /**
     * 获得用户详情
     *
     * @param id
     * @return
     */
    User get(Long id);

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    Integer update(User user);

    /**
     * 更新用户密码
     *
     * @param user
     * @return
     */
    Integer updatePwd(User user);

    /**
     * 根据用户名或者手机查询用户
     *
     * @param username
     * @return
     */
    User findByNameOrPhoneNumber(String username);

    /**
     * 检查用户旧密码
     *
     * @param oldPwd
     * @return
     */
    Boolean checkOldPwd(String oldPwd);

    /**
     * 根据角色ID获得用户ID
     * @param id
     * @return
     */
    List<Long> getUserIdsByRoleId(Long id);
}
