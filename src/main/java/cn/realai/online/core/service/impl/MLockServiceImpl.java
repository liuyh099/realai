package cn.realai.online.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.realai.online.core.dao.MLockDao;
import cn.realai.online.core.entity.MLock;
import cn.realai.online.core.service.MLockService;

@Service
public class MLockServiceImpl implements MLockService {

	@Autowired
	private MLockDao mlockDao;

	@Override
	public boolean tryLock(MLock mlock) {
		int ret = mlockDao.tryLock(mlock);
		return ret == 1 ? true : false;
	}

	@Override
	public boolean unLock(MLock mlock) {
		int ret = mlockDao.unLock(mlock);
		return ret == 1 ? true : false;
	}
	
}
