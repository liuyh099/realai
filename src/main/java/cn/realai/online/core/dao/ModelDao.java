package cn.realai.online.core.dao;

import cn.realai.online.core.bo.ModelDetailBO;
import cn.realai.online.core.bo.ModelListBO;
import cn.realai.online.core.entity.Model;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ModelDao {
    //插入模型
    Integer insert(Model model);

    //更新模型
    Integer update(Model model);

    //获取模型
    Model get(Long id);

    //获取最近的模型
    Model selectLatest();

    //查询模型列表
    List<ModelListBO> selectList(@Param("name") String name, @Param("serviceId") Long serviceId);

    //查询模型详情
    ModelDetailBO selectDetail(@Param("modelId") Long modelId);

}
