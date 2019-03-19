package cn.realai.online.userandperm.service;

import cn.realai.online.userandperm.entity.SysForgetNotice;

public interface SysForgetNoticeService {
    /**
     * 插入忘记密码通知
     * @param sysForgetNotice
     */
    void insert(SysForgetNotice sysForgetNotice);

    /**
     * 忘记密码总数
     * @return
     */
    Integer forgetNotice();
}
