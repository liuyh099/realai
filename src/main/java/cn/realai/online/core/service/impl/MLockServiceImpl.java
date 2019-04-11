package cn.realai.online.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.realai.online.core.dao.MLockDao;
import cn.realai.online.core.entity.MLock;
import cn.realai.online.core.service.MLockService;

@Service
public class MLockServiceImpl implements MLockService {

	@Autowired
	private MLockDao mlockDao;
 
	@Override
	@Transactional
	public boolean tryLock(MLock mlock) {
		int ret = mlockDao.tryLock(mlock);
		return ret == 1 ? true : false;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean unLock(MLock mlock) {
        int ret = mlockDao.unLock(mlock);
        return ret == 1 ? true : false;
    }

	@Override
	public MLock getLock(String lockKey, String lockValue, Long id, long expiredTime) {
		return new MLock(lockKey, lockValue + id, expiredTime);
	}
	
	@Override
	public MLock getLock(String lockKey, String lockValue, Long id) {
		return new MLock(lockKey, lockValue + id, MLock.MLOCK_LEASE_TIME);
	}

}
