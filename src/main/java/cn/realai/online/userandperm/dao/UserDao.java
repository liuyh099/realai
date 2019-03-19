package cn.realai.online.userandperm.dao;

import cn.realai.online.userandperm.entity.User;

import java.util.List;

public interface UserDao {
    /**
     * 查询用户列表
     * @param user
     * @return
     */
    List<User> findList(User user);

    /**
     * 保存用户
     * @param user
     */
    Integer insert(User user);

    /**
     * 删除用户
     * @param ids
     * @return
     */
    Integer delete(List<Long> ids);

    /**
     * 获得用户详情
     * @param id
     * @return
     */
    User get(Long id);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    Integer update(User user);

    /**
     * 更新用户密码
     * @param user
     * @return
     */
    Integer updatePwd(User user);
}
