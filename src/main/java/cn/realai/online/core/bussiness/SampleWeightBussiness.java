package cn.realai.online.core.bussiness;

import cn.realai.online.core.bo.SampleWeightBO;
import cn.realai.online.core.query.ExperimentalResultWhileBoxQuery;
import cn.realai.online.core.query.GlobalVariableQuery;

import java.util.List;

public interface SampleWeightBussiness {
	/**
	 * 样本权重
	 * @param experimentalResultWhileBoxQuery
	 * @return
	 */
	List<SampleWeightBO> getSampleWeightList(ExperimentalResultWhileBoxQuery experimentalResultWhileBoxQuery);

	List<SampleWeightBO> getSampleWeightList(GlobalVariableQuery globalVariableQuery);

}
