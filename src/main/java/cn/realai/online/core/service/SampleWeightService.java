package cn.realai.online.core.service;

import java.util.List;

import cn.realai.online.core.bo.SampleWeightBO;
import cn.realai.online.core.entity.SampleWeight;

public interface SampleWeightService {

    void insertList(List<SampleWeight> sgList);

    List<SampleWeightBO> findList(SampleWeight sampleWeight);

}
