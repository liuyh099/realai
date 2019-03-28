package cn.realai.online.userandperm.dao;

import cn.realai.online.userandperm.entity.SysForgetNotice;
import org.apache.ibatis.annotations.Param;

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

    /**
     *
     * @param id
     */
    void deleteByUserId(@Param("userId") Long id);
}
