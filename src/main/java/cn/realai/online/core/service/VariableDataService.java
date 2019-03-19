package cn.realai.online.core.service;

import java.util.List;

import cn.realai.online.core.bo.VariableDataBO;
import cn.realai.online.core.entity.VariableData;

public interface VariableDataService {

	/*
	 * 插入变量数据
	 */
	int insertVariableDataList(List<VariableData> vdList);

	List<VariableDataBO> selectVariableDataByExperimentId(long experimentId);

	/*
	 * 根据实验id查询变量数据
	 * @param experimentId
	 * @return
	 */
	List<VariableData> findListByExperimentId(Long experimentId);

}
