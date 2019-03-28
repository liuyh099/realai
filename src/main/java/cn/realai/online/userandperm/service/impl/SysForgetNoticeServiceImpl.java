package cn.realai.online.userandperm.service.impl;

import cn.realai.online.userandperm.dao.SysForgetNoticeDao;
import cn.realai.online.userandperm.entity.SysForgetNotice;
import cn.realai.online.userandperm.service.SysForgetNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SysForgetNoticeServiceImpl implements SysForgetNoticeService {

    @Autowired
    private SysForgetNoticeDao sysForgetNoticeDao;

    @Override
    @Transactional(readOnly = false)
    public void insert(SysForgetNotice sysForgetNotice) {
        sysForgetNoticeDao.insert(sysForgetNotice);
    }

    @Override
    public Integer forgetNotice() {
        return sysForgetNoticeDao.forgetNotice();
    }

    @Override
    public void deleteByUserId(Long id) {
        sysForgetNoticeDao.deleteByUserId(id);
    }
}
