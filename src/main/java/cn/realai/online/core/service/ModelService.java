package cn.realai.online.core.service;


import cn.realai.online.core.bo.ModelDetailBO;
import cn.realai.online.core.bo.ModelListBO;
import cn.realai.online.core.entity.Model;

import java.util.List;

/**
 * 功能描述：TODO
 *
 * @author admin
 * @create 2019-03-19 16:51
 */
public interface ModelService {

    //查询模型
    Model get(Long modelId);

    //查询模型列表
    List<ModelListBO> selectList(String name, Long serviceId);

    //查询模型详情
    ModelDetailBO selectDetail(Long modelId);

    //更新模型
    Integer update(Model model);

    //获取最近的模型
    Model selectLatest();

}
