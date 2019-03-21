package cn.realai.online.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.realai.online.core.entity.SampleSummary;

public interface SampleSummaryDao {

	void insertList(@Param("ssList")List<SampleSummary> ssList);
	
}
