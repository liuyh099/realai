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

	List<VariableData> findListByExperimentId(Long experimentId);

}
