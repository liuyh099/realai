package cn.realai.online.core.service;


import cn.realai.online.core.bo.ModelDetailBO;
import cn.realai.online.core.bo.ModelListBO;
import cn.realai.online.core.entity.Model;
import org.apache.ibatis.annotations.Param;

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

    //根据服务选择发布的模型
    Model selectByServiceId(Long serviceId);

    /**
     * 查询模型列表
     *
     * @param model
     * @return
     */
    List<Model> findList(Model model);

    /**
     * 插入模型数据
     *
     * @param model
     * @return
     */
    int insert(Model model);

    /**
     * 模型下线
     * @param serviceId
     * @param id
     * @param value
     */
    void offline(Long serviceId, Long id, String value);

    /**
     * 根据服务ID获得模型发布记录
     * @param serviceId
     * @return
     */
    List<Model> findModelOptionHistory(Long serviceId);
}
