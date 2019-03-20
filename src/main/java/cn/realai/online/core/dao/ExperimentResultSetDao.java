package cn.realai.online.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.realai.online.core.entity.ExperimentResultSet;

public interface ExperimentResultSetDao {

	void insertList(@Param("ersList")List<ExperimentResultSet> ersList);

	
	
}
