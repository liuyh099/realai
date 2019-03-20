package cn.realai.online.core.service;

import java.util.List;

import cn.realai.online.core.entity.VariableData;

public interface VariableDataService {

	/*
	 * 插入变量数据
	 */
	int insertVariableDataList(List<VariableData> vdList);

	/*
	 * 根据实验id查询变量数据
	 * @param experimentId
	 * @return
	 */
	List<VariableData> findListByExperimentId(Long experimentId);

	/**
	 * 查询实验参数
	 * @param variableData
	 * @return
	 */
    List<VariableData> findList(VariableData variableData);

	/**
	 * 删除数据
	 * @param experimentId
	 * @param ids
	 */
	void deleteVariableData(Long experimentId, List<Long> ids);
}
