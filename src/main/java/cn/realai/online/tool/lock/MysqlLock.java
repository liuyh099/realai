package cn.realai.online.tool.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import cn.realai.online.core.entity.MLock;
import cn.realai.online.core.service.MLockService;

@Configuration
public class MysqlLock {

    @Autowired
    private MLockService mlockService;

    public boolean tryLock(String key, String value, long expiredTime) {
        MLock mlock = new MLock(key, value, expiredTime);
        return tryLock(mlock);
    }

    public boolean tryLock(MLock mlock) {
        return mlockService.tryLock(mlock);
    }

    public boolean unLock(String key, String value) {
        MLock mlock = new MLock(key, value, 0);
        return unLock(mlock);
    }

    public boolean unLock(MLock mlock) {
        return mlockService.unLock(mlock);
    }
}
