package cn.realai.online.core.dao;

import org.apache.ibatis.annotations.Param;

import cn.realai.online.core.entity.BatchRecord;

public interface BatchRecordDao {

	Long insert(@Param("batchRecord")BatchRecord batchRecord);

}
