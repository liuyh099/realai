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
		//释放批次锁
		MLockService mLockService = SpringContextUtils.getBean(MLockService.class);
		MLock mLock = mLockService.getLock(experimentId);
		mLock.unLock();
		
		//修改批次状态
		BatchRecordService batchRecordService = SpringContextUtils.getBean(BatchRecordService.class);
		if (done) {
			batchRecordService.updateBatchRecordStatus(batchId, BatchRecord.BATCH_STATUS_OVER);
		} else {
			batchRecordService.updateBatchRecordStatus(batchId, BatchRecord.BATCH_STATUS_ERROR);
		}
	}

}
