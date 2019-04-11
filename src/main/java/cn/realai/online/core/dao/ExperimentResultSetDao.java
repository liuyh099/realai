package cn.realai.online.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.realai.online.core.entity.ExperimentResultSet;

public interface ExperimentResultSetDao {

    void insertList(@Param("ersList") List<ExperimentResultSet> ersList);

    /**
     * 查询实验评估结果
     *
     * @param experimentResultSet
     * @return
     */
    List<ExperimentResultSet> findList(ExperimentResultSet experimentResultSet);

	int deleteByExperimentId(@Param("experimentId")Long experimentId);
}
