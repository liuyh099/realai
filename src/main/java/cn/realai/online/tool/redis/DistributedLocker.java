package cn.realai.online.tool.redis;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;

/**
 * 分布式锁接口
 * @author lyh
 */
public interface DistributedLocker {
	
	RLock lock(String lockKey);
	 
    RLock lock(String lockKey, long timeout);
 
    RLock lock(String lockKey, TimeUnit unit, long timeout);
 
    boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime);
 
    void unlock(String lockKey);
 
    void unlock(RLock lock);
    
}
