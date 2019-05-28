package cn.realai.online.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.realai.online.core.dao.BatchExecutionTaskDao;
import cn.realai.online.core.entity.BatchExecutionTask;
import cn.realai.online.core.service.BatchExecutionTaskService;

@Service
public class BatchExecutionTaskServiceImpl implements BatchExecutionTaskService{

	@Autowired
	private BatchExecutionTaskDao batchExecutionTaskDao;

	@Override
	public Long insertBatchExecutionTask(BatchExecutionTask bet) {
		bet.setCreateTime(System.currentTimeMillis());
		return batchExecutionTaskDao.insertBatchExecutionTask(bet);
	}

	@Override
	public int taskErrorHandling(long id, String redisValue) {
		return batchExecutionTaskDao.updateStatusAndRedidValueById(id, BatchExecutionTask.STATUS_FAIL, redisValue);
	}

	@Override
	public int updateStatusById(long id, int status) {
		return batchExecutionTaskDao.updateStatusAndRedidValueById(id, status, null);
	}

}
