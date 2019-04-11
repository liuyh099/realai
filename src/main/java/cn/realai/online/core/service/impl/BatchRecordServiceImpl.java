package cn.realai.online.core.service.impl;

import org.redisson.api.RLock;
import cn.realai.online.core.bo.BatchListBO;
import cn.realai.online.core.bo.BatchRecordBO;  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.realai.online.common.Constant;
import cn.realai.online.core.dao.BatchRecordDao;
import cn.realai.online.core.dao.ExperimentDao;
import cn.realai.online.core.entity.BatchRecord;
import cn.realai.online.core.entity.Experiment;
import cn.realai.online.core.service.BatchRecordService;
import cn.realai.online.tool.lock.RedissonLock;

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

    @Autowired
    private RedissonLock redissonLock;
    
    @Autowired
    private ExperimentDao experimentDao;
    
	@Override
	public List<BatchListBO> selectList(BatchListBO batchListBO, Long minTime, Long maxTime) {
		return batchRecordDao.selectList(batchListBO, minTime, maxTime);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer delete(List<Long> idList) {
		return batchRecordDao.delete(idList);
	}

    @Override
	public List<BatchRecord> findBatchRecordList(BatchRecordBO batchRecordBO) {
		return batchRecordDao.findBatchRecordList(batchRecordBO);
	}
    
    /**
     * 获取每日定时跑批的批次记录，如果没有查询到则创建
     * 方法的事务为REQUIRES_NEW，业务方法总是会为自己发起一个新的事务,如果方法已运行在一个事务中,则原有事务被挂起,新的事务被创建,直到方法结束,新事务才结束,原先的事务才会恢复执行.
     * 保证批次创建后立即对所有数据库操作可见
     */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly=false)
	public BatchRecord getBatchRecordOfDaily(long eid, String batchDate, int batchTypeDaily,
			String xtableHeterogeneousDataSource, String xtableHomogeneousDataSource, String ytableDataSource) {
		BatchRecord batchRecord = batchRecordDao.getBatchRecordByEidAndDate(eid, batchDate, batchTypeDaily);
        if (batchRecord == null) {
        	RLock lock = redissonLock.lock(Constant.BATCH_DAILY_LOCK_PREFIX + eid, 3);
        	try {
        		batchRecord = batchRecordDao.getBatchRecordByEidAndDate(eid, batchDate, batchTypeDaily);
        		if (batchRecord == null) {
        			batchRecord = new BatchRecord();
        			batchRecord.setExperimentId(eid);
        			Experiment e = experimentDao.selectExperimentById(eid);
        			batchRecord.setServiceId(e.getServiceId());
        			batchRecord.setCreateTime(System.currentTimeMillis());
        			batchRecord.setBatchName(Constant.BATCH_DAILY_NAME_PREFIX + e.getName() + "_" + batchDate);
        			batchRecord.setBatchType(BatchRecord.BATCH_TYPE_DAILY);
        			batchRecord.setBatchDate(batchDate);
        			batchRecord.setXtableHeterogeneousDataSource(xtableHeterogeneousDataSource);
        			batchRecord.setXtableHomogeneousDataSource(xtableHomogeneousDataSource);
        			batchRecord.setYtableDataSource(ytableDataSource);
        			batchRecordDao.insert(batchRecord);
        		}
        	} catch (Exception e) {
        		e.printStackTrace();
        	} finally {
        		lock.unlock();
			}
        }
		return batchRecord;
	}

	@Override
	public BatchRecord getByEntity(BatchRecord batchRecord) {
		return batchRecordDao.getByEntity(batchRecord);
	}

	@Override
	public void maintainDownUrl(Long batchId, String downUrl) {
		batchRecordDao.maintainDownUrl(batchId, downUrl);
	}

	@Override
	public void maintainErrorMsg(Long batchId, String errorMsg, int status) {
		batchRecordDao.maintainErrorMsg(batchId, errorMsg, status);
	}

	@Override
	public void updateBatchRecordStatus(Long batchId, int status) {
		batchRecordDao.updateBatchRecordStatus(batchId, status);
	}

}
