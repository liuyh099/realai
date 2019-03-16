package cn.realai.online.core.dao;

import org.apache.ibatis.annotations.Param;

import cn.realai.online.core.entity.Experiment;

import java.util.List;

public interface ExperimentDao {

	int insertModel(@Param("experiment") Experiment experiment);

	Experiment selectExperimentById(@Param("id") long id);

    List<Experiment> findList(Experiment experiment);
}
