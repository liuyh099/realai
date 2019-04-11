package cn.realai.online.core.service;

import java.util.List;

import cn.realai.online.core.entity.SampleSummary;

public interface SampleSummaryService {

    void insertList(List<SampleSummary> ssList);

    /**
     * 查询样本综述
     *
     * @param sampleSummary
     * @return
     */
    List<SampleSummary> findList(SampleSummary sampleSummary);

	int deleteByExperimentId(Long experimentId);
}
