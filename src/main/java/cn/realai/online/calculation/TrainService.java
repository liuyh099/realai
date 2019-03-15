package cn.realai.online.calculation;

import cn.realai.online.core.bo.ExperimentBO;

public interface TrainService {

	/**
	 * 预处理
	 * @param experimentBO
	 */
	void preprocess(ExperimentBO experimentBO);

}
