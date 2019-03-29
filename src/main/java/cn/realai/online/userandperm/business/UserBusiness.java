package cn.realai.online.userandperm.business;

import cn.realai.online.common.page.PageBO;
import cn.realai.online.userandperm.bo.UserBO;
import cn.realai.online.userandperm.query.UserPageQuery;

import java.util.List;

/**
 * 用户业务层
 */
public interface UserBusiness {
    /**
     * 查询用户列表
     *
     * @return
     */
    PageBO<UserBO> list(UserPageQuery pageQuery);

    /**
     * 插入用户
     *
     * @param userBO
     */
    boolean insert(UserBO userBO);

    /**
     * 检查用户名
     *
     * @param userName
     * @return
     */
    boolean checkUserName(String userName);

    /**
     * 检查手机号码
     *
     * @param phoneNumber
     * @return
     */
    boolean checkPhoneNumber(String phoneNumber);

    /**
     * 根据id集合删除数据
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
    UserBO detail(Long id);

    /**
     * 更新用户信息
     *
     * @param userbo
     * @return
     */
    boolean update(UserBO userbo);

    /**
     * 更新用户密码
     *
     * @param userbo
     * @return
     */
    boolean updatePwd(UserBO userbo);

    /**
     * 检查名称和电话号码
     * @param nameOrPassword
     * @param userId
     * @return
     */
    boolean checkUserNameOrPhoneNumber(String nameOrPassword, Long userId);
}
