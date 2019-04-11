package cn.realai.online.tool.modelcallthreadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.realai.online.core.entity.BatchRecord;
import cn.realai.online.core.entity.MLock;
import cn.realai.online.core.service.BatchRecordService;
import cn.realai.online.core.service.MLockService;
import cn.realai.online.util.SpringContextUtils;

public class BatchTaskOfDone extends BaseBatchTask {

	private static Logger logger = LoggerFactory.getLogger(BatchTaskOfDone.class);
	
	private Long experimentId;
	
	private Long batchId;
	
	private Boolean done;
	
	public BatchTaskOfDone(Long experimentId, Long batchId, Boolean done) {
		this.experimentId = experimentId;
		this.batchId = batchId;
		this.done = done;
	}
	
	@Override
	public void run() {
		logger.info("BatchTaskOfDone run. experimentId{}, batchId{}, redisKey{}", experimentId, batchId, redisKey);
		
		BatchRecordService batchRecordService = SpringContextUtils.getBean(BatchRecordService.class);
		BatchRecord batchRecord = new BatchRecord();
		batchRecord.setId(batchId);
		batchRecord = batchRecordService.getByEntity(batchRecord);
		
		//释放批次锁
		MLockService mLockService = SpringContextUtils.getBean(MLockService.class);
		MLock mLock;
		if (batchRecord.getBatchType() == BatchRecord.BATCH_TYPE_DAILY) {
			mLock = mLockService.getLock(MLock.ONLINE_MLOCK_LOCK, MLock.BATCH_MLOCK_PREFIX, batchId);
		} else if (batchRecord.getBatchType() == BatchRecord.BATCH_TYPE_OFFLINE) {
			mLock = mLockService.getLock(MLock.TRAIN_MLOCK_LOCK, MLock.BATCH_MLOCK_PREFIX, batchId);
		} else {
			mLock = null;
		}
		
		if (mLock != null) {
			mLock.unLock();
		}
		
		//修改批次状态
		if (done) {
			batchRecordService.updateBatchRecordStatus(batchId, batchRecord.BATCH_STATUS_OVER);
		} else {
			batchRecordService.updateBatchRecordStatus(batchId, batchRecord.BATCH_STATUS_ERROR);
		}
	}

}
