package cn.realai.online.core.service;

import cn.realai.online.core.entity.BatchExecutionTask;

public interface BatchExecutionTaskService {

	Long insertBatchExecutionTask(BatchExecutionTask bet);
	
	int updateStatusById(long id, int status);

	int taskErrorHandling(long id, String redisValue);
}
