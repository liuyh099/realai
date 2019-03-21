package cn.realai.online.userandperm.business;

import cn.realai.online.userandperm.vo.ChangePwdVO;
import cn.realai.online.userandperm.vo.ForgetVo;

public interface UserOptionBusiness {

    /**
     * 忘记密码业务
     *
     * @param forgetVo
     * @return
     */
    Boolean forget(ForgetVo forgetVo);

    /**
     * 获得忘记密码提示总数
     *
     * @return
     */
    Integer forgetNotice();

    /**
     * 检查旧密码
     *
     * @param oldPwd
     * @return
     */
    Boolean checkOldPwd(String oldPwd);

    /**
     * 修改密码
     *
     * @param changePwdVO
     * @return
     */
    Boolean changePwd(ChangePwdVO changePwdVO);
}
