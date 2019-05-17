package cn.realai.online.calculation;

import java.util.List;

import cn.realai.online.core.entity.BatchRecord;
import cn.realai.online.core.entity.Experiment;
import cn.realai.online.core.entity.VariableData;
import cn.realai.online.core.query.realtime.RealTimeData;

public interface TrainService {
	
	String realTimeForecast(RealTimeData realTimeData);

	/**
	 * 预处理
	 * @param experimentBO
	 */
	void preprocess(Experiment experiment);  
	
	/**
	 * 训练
	 * @param experiment 训练对象
	 * @param oldEid  如果是而此实验，为原实验的id
	 * @param homoList  同质字段
	 * @param hetroList  异质字段
	 * @Param delOrAdd  上述同质或异质字段是选择还是删除
	 * @return
	 */
	int training(Experiment experiment, Long oldEid, List<VariableData> homoList, List<VariableData> hetroList, int delOrAdd);

	/**
	 * 部署
	 */
	String experimentDeploy(Long experimentId, Long originalId, String type, String secretKey, Long serviceId);
	
	/**
	 * 离线跑批
	 */
	int runBatchOffLine(BatchRecord batchRecord);
	
	/**
	 * 删除实验
	 */
	int deleteExperiment(Long experimentId);

	/**
	 * 每日跑批
	 * @param batchRecord
	 * @return
	 */
	int runBatchDaily(BatchRecord batchRecord);
}
