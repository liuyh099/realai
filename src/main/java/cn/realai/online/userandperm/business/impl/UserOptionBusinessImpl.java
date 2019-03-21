package cn.realai.online.userandperm.business.impl;

import cn.realai.online.userandperm.business.UserOptionBusiness;
import cn.realai.online.userandperm.entity.SysForgetNotice;
import cn.realai.online.userandperm.entity.User;
import cn.realai.online.userandperm.service.SysForgetNoticeService;
import cn.realai.online.userandperm.service.UserService;
import cn.realai.online.userandperm.vo.ChangePwdVO;
import cn.realai.online.userandperm.vo.ForgetVo;
import cn.realai.online.util.EncodingPasswordUtils;
import cn.realai.online.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;

@Service
@Transactional(readOnly = true)
public class UserOptionBusinessImpl implements UserOptionBusiness {

    @Autowired
    private UserService userService;

    @Autowired
    private SysForgetNoticeService sysForgetNoticeService;

    /**
     * 忘记密码业务 先检查用户是否存在，如果存在将数据存入forget_notice表
     *
     * @param forgetVo
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public Boolean forget(ForgetVo forgetVo) {
        HashMap<String, Object> result = new HashMap<>();
        User user = userService.findByNameOrPhoneNumber(forgetVo.getName());
        if (ObjectUtils.isEmpty(user)) {
            return false;
        }
        SysForgetNotice sysForgetNotice = new SysForgetNotice();
        sysForgetNotice.setUserId(user.getId());
        sysForgetNotice.setCreateTime(System.currentTimeMillis());
        sysForgetNoticeService.insert(sysForgetNotice);
        return true;
    }

    @Override
    public Integer forgetNotice() {
        return sysForgetNoticeService.forgetNotice();
    }

    @Override
    public Boolean checkOldPwd(String oldPwd) {
        return userService.checkOldPwd(oldPwd);
    }

    @Override
    public Boolean changePwd(ChangePwdVO changePwdVO) {
        User user = UserUtils.getUser();
        if (ObjectUtils.isEmpty(user)) {
            return false;
        }
        String pwd = changePwdVO.getOldPwd();
        pwd = EncodingPasswordUtils.encodingPassword(pwd);
        user.setPwd(pwd);

        if (userService.updatePwd(user) > 0) {
            return true;
        }
        return false;
    }
}
