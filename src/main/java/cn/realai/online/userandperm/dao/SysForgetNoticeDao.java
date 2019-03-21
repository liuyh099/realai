package cn.realai.online.userandperm.dao;

import cn.realai.online.userandperm.entity.SysForgetNotice;

public interface SysForgetNoticeDao {

    /**
     * 插入忘记密码消息
     *
     * @param sysForgetNotice
     */
    void insert(SysForgetNotice sysForgetNotice);

    /**
     * 获得忘记密码总条数
     *
     * @return
     */
    Integer forgetNotice();
}
