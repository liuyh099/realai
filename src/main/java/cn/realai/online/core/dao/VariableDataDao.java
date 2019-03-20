package cn.realai.online.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.realai.online.core.entity.VariableData;

public interface VariableDataDao {

	int insertList(@Param("vdList")List<VariableData> vdList);

	List<VariableData> findListByExperimentId(@Param("experimentId")Long experimentId);

	List<VariableData> findList(VariableData variableData);
}
