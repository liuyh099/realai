package cn.realai.online.core.service;

import cn.realai.online.core.bo.BatchDetailBO;
import cn.realai.online.core.bo.BatchListBO;
import cn.realai.online.core.entity.BatchRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BatchRecordService {

	Integer insert(BatchRecord batchRecord);

	Integer delete(List<Long> idList);

    /**
     * 根据ID和其他条件查询批次信息
     *
     * @param batchRecord
     * @return
     */
    BatchRecord getByEntity(BatchRecord batchRecord);

	//查询离线跑批列表
	List<BatchListBO> selectList(@Param("batchListBO") BatchListBO batchListBO, @Param("minTime") Long minTime, @Param("maxTime") Long maxTime);

	//查询离线跑批详细
	BatchDetailBO selectDetail(Long batchId);

}
