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
	
	int training(Experiment experiment, Long oldEid, List<VariableData> homoList, List<VariableData> hetroList);

}
