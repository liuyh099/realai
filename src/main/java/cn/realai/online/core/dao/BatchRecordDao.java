package cn.realai.online.core.dao;

import org.apache.ibatis.annotations.Param;

import cn.realai.online.core.entity.BatchRecord;

public interface BatchRecordDao {

    Long insert(@Param("batchRecord") BatchRecord batchRecord);

    /**
     * 必须传入ID
     *
     * @param batchRecord
     * @return
     */
    BatchRecord getByEntity(BatchRecord batchRecord);

	BatchRecord getBatchRecordByEidAndDate(@Param("eid")long eid, 
			@Param("date")String date, 
			@Param("batchType")int batchType);
}
