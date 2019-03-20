package cn.realai.online.core.service;

import java.util.List;

import cn.realai.online.core.bo.VariableDataBO;
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
	 * 根据实验id与模式类型查询变量数据
	 * @param variableData
	 * @return
	 */
	List<VariableDataBO> findList(VariableData variableData);

}
