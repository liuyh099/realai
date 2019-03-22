package cn.realai.online.core.service.impl;

import cn.realai.online.core.bo.BatchListBO;
import cn.realai.online.core.bo.BatchRecordBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.realai.online.core.dao.BatchRecordDao;
import cn.realai.online.core.entity.BatchRecord;
import cn.realai.online.core.service.BatchRecordService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BatchRecordServiceImpl implements BatchRecordService {

	@Autowired
	private BatchRecordDao batchRecordDao;
	
	@Override
	public Integer insert(BatchRecord batchRecord) {
		batchRecord.setCreateTime(System.currentTimeMillis());
		return batchRecordDao.insert(batchRecord);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer delete(List<Long> idList) {
		return batchRecordDao.delete(idList);
	}

	@Override
	public BatchRecord getByEntity(BatchRecord batchRecord) {
		return batchRecordDao.getByEntity(batchRecord);
	}

	@Override
	public List<BatchListBO> selectList(@Param("batchListBO") BatchListBO batchListBO, @Param("minTime") Long minTime, @Param("maxTime") Long maxTime) {
		return batchRecordDao.selectList(batchListBO, minTime, maxTime);
	}

	@Override
	public List<BatchRecord> findBatchRecordList(BatchRecordBO batchRecordBO, boolean isTranFlag) {
		return batchRecordDao.findBatchRecordList(batchRecordBO,isTranFlag);
	}
}
