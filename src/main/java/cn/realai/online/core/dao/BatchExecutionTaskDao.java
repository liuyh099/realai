package cn.realai.online.core.dao;

import org.apache.ibatis.annotations.Param;

import cn.realai.online.core.entity.BatchExecutionTask;

public interface BatchExecutionTaskDao {

	Long insertBatchExecutionTask(BatchExecutionTask bet);

	int updateStatusAndRedidValueById(@Param("id")Long id, 
			@Param("status")int status,
			@Param("redisValue")String redisValue);

}
