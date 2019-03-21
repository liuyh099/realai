package cn.realai.online.core.dao;

import cn.realai.online.core.bo.BatchListBO;
import org.apache.ibatis.annotations.Param;

import cn.realai.online.core.entity.BatchRecord;

import java.util.List;

public interface BatchRecordDao {

	Long insert(@Param("batchRecord")BatchRecord batchRecord);

	//查询离线跑批列表
	List<BatchListBO> selectList(@Param("batchListBO") BatchListBO batchListBO, @Param("minTime") Long minTime, @Param("maxTime") Long maxTime);

}
