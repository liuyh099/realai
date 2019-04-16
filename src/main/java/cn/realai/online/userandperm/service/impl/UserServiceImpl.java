package cn.realai.online.userandperm.service.impl;

import cn.realai.online.userandperm.dao.UserDao;
import cn.realai.online.userandperm.entity.User;
import cn.realai.online.userandperm.service.UserService;
import cn.realai.online.util.EncodingPasswordUtils;
import cn.realai.online.util.UserUtils;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

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
        String pwd = user.getPwd();
        pwd = EncodingPasswordUtils.encodingPassword(pwd);
        user.setPwd(pwd);
        return userDao.insert(user);
    }

    @Override
    public Integer delete(List<Long> ids) {
        return userDao.delete(ids);
    }

    @Override
    public User get(Long id) {
        if (id == null) {
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
        String pwd = user.getPwd();
        pwd = EncodingPasswordUtils.encodingPassword(pwd);
        user.setPwd(pwd);
        return userDao.updatePwd(user);
    }

    @Override
    public User findByNameOrPhoneNumber(String username) {
        return userDao.findByNameOrPhoneNumber(username);
    }

    @Override
    public Boolean checkOldPwd(String oldPwd) {
        User user = UserUtils.getUser();
        if (ObjectUtils.isEmpty(user)) {
            return false;
        }
        user = get(user.getId());
        byte[] salt = HexUtils.fromHexString(user.getPwd().substring(0, 16));
        String pwd = EncodingPasswordUtils.encodingPassword(oldPwd, salt);
        if (user.getPwd().equals(pwd)) {
            return true;
        }
        return false;
    }

    @Override
    public List<Long> getUserIdsByRoleId(Long roleId) {
        return userDao.getUserIdsByRoleId(roleId);
    }

    @Override
    public List<Long> findUserIdByRoleIds(List<Long> ids) {
        return userDao.findUserIdByRoleIds(ids);
    }

    @Override
    public void updateRoleIdNull(List<Long> userIds) {
        userDao.updateRoleIdNull(userIds);
    }
}
