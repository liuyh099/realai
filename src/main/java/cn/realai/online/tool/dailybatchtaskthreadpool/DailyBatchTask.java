package cn.realai.online.tool.dailybatchtaskthreadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.realai.online.calculation.TrainService;
import cn.realai.online.core.entity.BatchRecord;
import cn.realai.online.core.service.BatchRecordService;
import cn.realai.online.tool.calculationthreadpool.CalculationTask;
import cn.realai.online.util.SpringContextUtils;

/**
 * 每日跑批任务
 * @author 86183
 */
public class DailyBatchTask implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(CalculationTask.class);
	
	private String command;
	
	private Long experimentId;
	
	private String batchDate;
	
	private String xtableHeterogeneousDataSource;
	
	private String xtableHomogeneousDataSource;
	
	private String ytableDataSource;
	
	public DailyBatchTask(Long experimentId, String batchDate, String xtableHeterogeneousDataSource, 
			String xtableHomogeneousDataSource, String ytableDataSource) {
		this.experimentId = experimentId;
		this.batchDate = batchDate;
		this.xtableHeterogeneousDataSource = xtableHeterogeneousDataSource;
		this.xtableHomogeneousDataSource = xtableHomogeneousDataSource;
		this.ytableDataSource = ytableDataSource;
	}

	/*
	 * 跑批处理方法
	 */
	@Override
	public void run() {
		//获取批次信息
		BatchRecordService batchRecordService = SpringContextUtils.getBean(BatchRecordService.class);
		BatchRecord batchRecord = batchRecordService.getBatchRecordOfDaily(experimentId, batchDate, BatchRecord.BATCH_TYPE_DAILY,
				xtableHeterogeneousDataSource, xtableHomogeneousDataSource, ytableDataSource);
		
		//调用跑批接口
		TrainService trainService = SpringContextUtils.getBean(TrainService.class);
		int ret = 0;
		try {
			ret = trainService.runBatchDaily(batchRecord);
		} catch (Exception e) {
			logger.error("DailyBatchTask run. batchRecord{}", JSON.toJSONString(batchRecord));
		}
		
		//如果因为没有获取锁而调用失败则将任务重新放回队列
		if (ret == -1) {
			DailyBatchTaskQueue.queue.execute(this);
		}
		
		try {
			Thread.sleep(10000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
