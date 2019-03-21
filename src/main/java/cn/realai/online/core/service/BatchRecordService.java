package cn.realai.online.core.service;

import cn.realai.online.core.bo.BatchListBO;
import cn.realai.online.core.entity.BatchRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BatchRecordService {

	Long insert(BatchRecord batchRecord);

	//查询离线跑批列表
	List<BatchListBO> selectList(@Param("batchListBO") BatchListBO batchListBO, @Param("minTime") Long minTime, @Param("maxTime") Long maxTime);

}
