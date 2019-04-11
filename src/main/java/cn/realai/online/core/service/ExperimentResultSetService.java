package cn.realai.online.core.service;

import java.util.List;

import cn.realai.online.core.entity.ExperimentResultSet;

public interface ExperimentResultSetService {

    void insertList(List<ExperimentResultSet> ersList);

    /**
     * 查询实验评估结果
     *
     * @param experimentResultSet
     * @return
     */
    List<ExperimentResultSet> findList(ExperimentResultSet experimentResultSet);

	int deleteByExperimentId(Long experimentId);
}
