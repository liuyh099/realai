package cn.realai.online.core.service;

import cn.realai.online.core.bo.BatchListBO;
import cn.realai.online.core.bo.BatchRecordBO;
import cn.realai.online.core.entity.BatchRecord;

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
	List<BatchListBO> selectList(BatchListBO batchListBO, Long minTime, Long maxTime);

	/**
	 * 查询批次数据
	 * @param batchRecordBO
	 * @return
	 */
    List<BatchRecord> findBatchRecordList(BatchRecordBO batchRecordBO);
    
	BatchRecord getBatchRecordOfDaily(long eid, String date, int batchTypeDaily,
			String xtableHeterogeneousDataSource, String xtableHomogeneousDataSource, String ytableDataSource);

	/**
	 * 维护得分下载地址
	 * @param batchId
	 * @param downUrl
	 */
	void maintainDownUrl(Long batchId, String downUrl);

	/**
	 * 批次处理报错维护
	 * @param experimentId
	 * @param msg
	 * @param bATCH_STATUS_ERROR
	 */
	void maintainErrorMsg(Long batchId, String errorMsg, int status);

	/**
	 * 修改批次状态
	 * @param id
	 * @param status
	 */
	void updateBatchRecordStatus(Long batchId, int status);

}
