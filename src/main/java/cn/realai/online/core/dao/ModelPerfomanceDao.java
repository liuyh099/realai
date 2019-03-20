package cn.realai.online.core.dao;

import cn.realai.online.core.entity.ModelPerformance;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ModelPerfomanceDao {
    //插入模型表现
    Integer insert(ModelPerformance perfomance);

    //更新模型表现
    Integer update(ModelPerformance perfomance);

    //获取模型表现
    ModelPerformance get(Long id);

    //查询模型列表
    List<ModelPerformance> selectList(@Param("modelId") Long modelId);

    //批量插入
	void insertList(@Param("modelPerformanceList")List<ModelPerformance> modelPerformanceList);

}
