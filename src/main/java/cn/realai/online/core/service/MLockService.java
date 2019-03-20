package cn.realai.online.core.service;

import cn.realai.online.core.entity.MLock;

public interface MLockService {

	boolean tryLock(MLock mlock);

	boolean unLock(MLock mlock);

}
