package cn.realai.online.core.dao;

import cn.realai.online.core.bo.BatchDetailBO;
import cn.realai.online.core.bo.BatchListBO;
import cn.realai.online.core.bo.BatchRecordBO;
import org.apache.ibatis.annotations.Param;

import cn.realai.online.core.entity.BatchRecord;

import java.util.List;

public interface BatchRecordDao {

	Integer insert(BatchRecord batchRecord);

	Integer delete(List<Long> idList);

    /**
     * 必须传入ID
     *
     * @param batchRecord
     * @return  
     */
    BatchRecord getByEntity(BatchRecord batchRecord);

	//查询离线跑批列表
	List<BatchListBO> selectList(@Param("batchListBO") BatchListBO batchListBO, @Param("minTime") Long minTime, @Param("maxTime") Long maxTime);

	//查询离线跑批详细
	BatchDetailBO selectDetail(Long batchId);

	/**
	 *
	 * @param batchRecordBO
	 * @return
	 */
    List<BatchRecord> findBatchRecordList(BatchRecordBO batchRecordBO);
    
	BatchRecord getBatchRecordByEidAndDate(@Param("eid")long eid, 
			@Param("date")String date, 
			@Param("batchType")int batchType);

	void maintainDownUrl(@Param("batchId")Long batchId, @Param("downUrl")String downUrl);

}
