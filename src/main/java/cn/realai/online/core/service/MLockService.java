package cn.realai.online.core.service;

import cn.realai.online.core.entity.MLock;

public interface MLockService {

	MLock getLock(Long id, int type);
	
    boolean tryLock(MLock mlock);

    boolean unLock(MLock mlock);

}
