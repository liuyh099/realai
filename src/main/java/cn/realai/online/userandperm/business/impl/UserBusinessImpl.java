package cn.realai.online.userandperm.business.impl;

import cn.realai.online.common.page.PageBO;
import cn.realai.online.core.query.PageQuery;
import cn.realai.online.userandperm.bo.UserBO;
import cn.realai.online.userandperm.business.UserBusiness;
import cn.realai.online.userandperm.entity.User;
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

    @Override
    public PageBO<UserBO> list(PageQuery pageQuery) {
        Page page = PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize());
        List<User> list = userService.list(new User());
        List<UserBO> result = JSON.parseArray(JSON.toJSONString(list), UserBO.class);
        return new PageBO<UserBO>(result, pageQuery.getPageSize(), pageQuery.getPageNum(), page.getTotal(), page.getPages());
    }

    @Override
    @Transactional(readOnly = false)
    public boolean insert(UserBO userBO) {

        //检查用户名
        if (checkUserNameOrPhoneNumber(userBO)) return false;
        User user = new User();
        BeanUtils.copyProperties(userBO, user);
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
        UserBO userBO =new UserBO();
        BeanUtils.copyProperties(user,userBO);
        return userBO;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean update(UserBO userBO) {
        if (checkUserNameOrPhoneNumber(userBO)) return false;
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
        User user =new User();
        BeanUtils.copyProperties(userbo,user);
        if (userService.updatePwd(user) <= 0) {
            return false;
        }
        return true;
    }


    /**
     * 检查用户名或者密码是否相同
     * @param userBO
     * @return
     */
    private boolean checkUserNameOrPhoneNumber(UserBO userBO) {
        //检查用户名
        if (!checkUserName(userBO.getName())) {
            return true;
        }
        //检查手机号码
        if (!checkPhoneNumber(userBO.getPhoneNumber())) {
            return true;
        }
        return false;
    }


}
