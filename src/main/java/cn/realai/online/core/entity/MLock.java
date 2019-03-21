package cn.realai.online.core.entity;

import cn.realai.online.core.service.ExperimentService;
import cn.realai.online.tool.lock.MysqlLock;
import cn.realai.online.util.SpringContextUtils;

/**
 * 数据库锁
 *
 * @author lyh
 */
public class MLock {

    //锁
    private String lockKey;

    //锁的过期时间
    private long expiredTime;

    //锁的值
    private String lockValue;

    //当前时间
    private long currentTime;

    //结束时间
    private long endTime;

    private MysqlLock mysqlLock;

    public MLock(String lockKey, String lockValue, long expiredTime) {
        this.lockKey = lockKey;
        this.lockValue = lockValue;
        this.expiredTime = expiredTime;
        MysqlLock mysqlLock = SpringContextUtils.getBean(MysqlLock.class);
        this.mysqlLock = mysqlLock;
    }

    public MLock() {
    }

    public String getLockKey() {
        return lockKey;
    }

    public void setLockKey(String lockKey) {
        this.lockKey = lockKey;
    }

    public long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(long expiredTime) {
        this.expiredTime = expiredTime;
        this.currentTime = System.currentTimeMillis();
        this.endTime = currentTime + expiredTime;
    }

    public String getLockValue() {
        return lockValue;
    }

    public void setLockValue(String lockValue) {
        this.lockValue = lockValue;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public boolean tryLock() {
        return mysqlLock.tryLock(this);
    }

    public boolean unLock() {
        return mysqlLock.unLock(this);
    }

}
