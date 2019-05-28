package cn.realai.online.tool.traincallbackthreadpool.copy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.realai.online.core.entity.BatchExecutionTask;
import cn.realai.online.core.entity.BatchRecord;
import cn.realai.online.core.entity.MLock;
import cn.realai.online.core.service.BatchExecutionTaskService;
import cn.realai.online.core.service.BatchRecordService;
import cn.realai.online.core.service.MLockService;
import cn.realai.online.util.SpringContextUtils;

public class BatchTaskOfDone extends BaseBatchTask {

	private static Logger logger = LoggerFactory.getLogger(BatchTaskOfDone.class);

	
	public BatchTaskOfDone(BatchExecutionTask bet) {
		this.bet = bet;
	}
	
	@Override
	public void run() {
		logger.info("BatchTaskOfDone run. bet{}", JSON.toJSONString(bet));
		try {
			Long batchId = bet.getBatchId();
			
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
				boolean b = mLock.unLock();
				logger.info("BatchTaskOfDone run, unlock. batchId{}, b{}", batchId, b);
			}
			
			//修改批次状态
			if (bet.getDone()) {
				batchRecordService.updateBatchRecordStatus(batchId, batchRecord.BATCH_STATUS_OVER);
			} else {
				batchRecordService.updateBatchRecordStatus(batchId, batchRecord.BATCH_STATUS_ERROR);
			}
			BatchExecutionTaskService batchExecutionTaskService = SpringContextUtils.getBean(BatchExecutionTaskService.class);
			batchExecutionTaskService.updateStatusById(bet.getId(), BatchExecutionTask.STATUS_SUCCESS);
		} catch (Exception e) {
			BatchExecutionTaskService batchExecutionTaskService = SpringContextUtils.getBean(BatchExecutionTaskService.class);
			batchExecutionTaskService.updateStatusById(bet.getId(), BatchExecutionTask.STATUS_FAIL);
		}
	}

}
