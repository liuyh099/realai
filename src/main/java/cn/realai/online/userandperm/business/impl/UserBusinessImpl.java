package cn.realai.online.userandperm.business.impl;

import cn.realai.online.common.page.PageBO;
import cn.realai.online.userandperm.bo.UserBO;
import cn.realai.online.userandperm.business.UserBusiness;
import cn.realai.online.userandperm.entity.User;
import cn.realai.online.userandperm.query.UserPageQuery;
import cn.realai.online.userandperm.service.SysForgetNoticeService;
import cn.realai.online.userandperm.service.UserService;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserBusinessImpl implements UserBusiness {

    @Autowired
    private UserService userService;

    @Autowired
    private SysForgetNoticeService sysForgetNoticeService;

    @Override
    public PageBO<UserBO> list(UserPageQuery pageQuery) {
        Page page = PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize());
        User user = new User();
        user.setForget(pageQuery.getForget());
        List<User> list = userService.list(user);
        List<UserBO> result = JSON.parseArray(JSON.toJSONString(list), UserBO.class);
        return new PageBO<UserBO>(result, pageQuery.getPageSize(), pageQuery.getPageNum(), page.getTotal(), page.getPages());
    }

    @Override
    @Transactional(readOnly = false)
    public boolean insert(UserBO userBO) {

        //检查用户名
        //if (checkUserNameOrPhoneNumber(userBO)) return false;
        User user = new User();
        BeanUtils.copyProperties(userBO, user);
        user.setCreateTime(System.currentTimeMillis());
        if (userService.insert(user) <= 0) {
            return false;
        }
        return true;

    }

    /**
     * 检查用户名
     *
     * @param userName
     * @return
     */
    @Override
    public boolean checkUserName(String userName) {
        User user = new User();
        user.setName(userName);
        List list = userService.list(user);
        if (CollectionUtils.isEmpty(list)) {
            return true;
        }
        return false;
    }

    /**
     * 检查手机号码
     *
     * @param phoneNumber
     * @return
     */
    @Override
    public boolean checkPhoneNumber(String phoneNumber) {
        User user = new User();
        user.setPhoneNumber(phoneNumber);
        List list = userService.list(user);
        if (CollectionUtils.isEmpty(list)) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = false)
    public Integer delete(List<Long> ids) {
        return userService.delete(ids);
    }

    @Override
    public UserBO detail(Long id) {
        User user = userService.get(id);
        UserBO userBO = new UserBO();
        BeanUtils.copyProperties(user, userBO);
        return userBO;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean update(UserBO userBO) {
        //if (checkUserNameOrPhoneNumber(userBO)) return false;
        User user = new User();
        BeanUtils.copyProperties(userBO, user);
        if (userService.update(user) <= 0) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean updatePwd(UserBO userbo) {
        User user = new User();
        BeanUtils.copyProperties(userbo, user);
        if (userService.updatePwd(user) <= 0) {
            return false;
        }
        //删除忘记密码提醒
        sysForgetNoticeService.deleteByUserId(userbo.getId());

        return true;
    }


    /**
     * 检查用户名或者密码是否相同
     *
     * @param nameOrPassword
     * @return
     */
    public boolean checkUserNameOrPhoneNumber(String nameOrPassword, Long userId) {

        //用户名和手机号码都不可以重复
        User user = userService.findByNameOrPhoneNumber(nameOrPassword);
        if (userId == null) {
            if (user == null) {
                return true;
            }
        } else {
            if (user == null) {
                return true;
            } else {
                if (user.getId() == userId) {
                    return true;
                }
            }
        }
        return false;
    }


}
