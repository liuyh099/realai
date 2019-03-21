package cn.realai.online.core.service;

import cn.realai.online.core.entity.ModelPerformance;

import java.util.List;

public interface ModelPerformanceService {

    void insertList(List<ModelPerformance> modelPerformanceList);

    //查询模型表现列表
    List<ModelPerformance> selectList(Long modelId);

}
