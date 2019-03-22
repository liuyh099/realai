package cn.realai.online.calculation;

import java.util.List;

import cn.realai.online.core.entity.Experiment;
import cn.realai.online.core.entity.VariableData;

public interface TrainService {

	/**
	 * 预处理
	 * @param experimentBO
	 */
	void preprocess(Experiment experiment);
	
	/**
	 * 训练
	 * @param experiment
	 * @param oldEid
	 * @param homoList
	 * @param hetroList
	 * @return
	 */
	int training(Experiment experiment, Long oldEid, List<VariableData> homoList, List<VariableData> hetroList);

	/**
	 * 部署
	 */
	int experimentDeploy(Long experimentId);
	
	/**
	 * 离线跑批
	 */
	int runBatchOfOffline();
	
	/**
	 * 删除实验
	 */
	int deleteExperiment(Long experimentId);
}
