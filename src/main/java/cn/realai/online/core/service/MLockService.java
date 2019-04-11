package cn.realai.online.core.service;

import cn.realai.online.core.entity.MLock;

public interface MLockService {

	MLock getLock(String lockKey, String lockValue, Long id, long expiredTime);
	
	MLock getLock(String key, String value, Long id);
	
    boolean tryLock(MLock mlock);

    boolean unLock(MLock mlock);

}
