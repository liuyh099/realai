package cn.realai.online.userandperm.service.impl;

import cn.realai.online.userandperm.dao.UserDao;
import cn.realai.online.userandperm.entity.User;
import cn.realai.online.userandperm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> list(User user) {
        return userDao.findList(user);
    }

    @Override
    public Integer insert(User user) {
        return userDao.insert(user);
    }

    @Override
    public Integer delete(List<Long> ids) {
        return userDao.delete(ids);
    }

    @Override
    public User get(Long id) {
        if(id==null){
            return null;
        }
        return userDao.get(id);
    }

    @Override
    public Integer update(User user) {
        return userDao.update(user);
    }

    @Override
    public Integer updatePwd(User user) {
        return userDao.updatePwd(user);
    }

    @Override
    public User findByNameOrPhoneNumber(String username) {
        return userDao.findByNameOrPhoneNumber(username);
    }
}
