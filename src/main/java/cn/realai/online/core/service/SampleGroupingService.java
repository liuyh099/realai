package cn.realai.online.core.service;

import java.util.List;

import cn.realai.online.core.entity.SampleGrouping;

public interface SampleGroupingService {

    void insertList(List<SampleGrouping> sgList);

    List<SampleGrouping> findListByExperimentId(Long experimentId);

}
