package cn.realai.online.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.realai.online.core.entity.VariableData;

public interface VariableDataDao {

	int insertVariableDataList(@Param("vdList")List<VariableData> vdList);

	List<VariableData> selectVariableDataByExperimentId(long experimentId);

}
