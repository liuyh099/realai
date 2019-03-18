package cn.realai.online.calculation;

import cn.realai.online.core.entity.Experiment;

public interface TrainService {

	/**
	 * 预处理
	 * @param experimentBO
	 */
	void preprocess(Experiment experiment);
	
	int training(Experiment experiment);

}
