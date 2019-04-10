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

    //获取最近发布的模型
    Model selectLatest();

    //根据服务选择发布的模型
    Model selectByServiceId(@Param("serviceId") Long serviceId);

    //查询模型列表
    List<ModelListBO> selectList(@Param("name") String name, @Param("serviceId") Long serviceId);

    //查询模型详情
    ModelDetailBO selectDetail(@Param("modelId") Long modelId);

    /**
     * 查询模型列表
     * @param model
     * @return
     */
    List<Model> findList(Model model);

    /**
     * 模型下线
     * @param serviceId
     * @param id
     * @param value
     */
    void offline(@Param("serviceId") Long serviceId, @Param("id") Long id, @Param("status") String value);

    /**
     * 获得模型操作记录
     * @param serviceId
     * @return
     */
    List<Model> findModelOptionHistory(@Param("serviceId") Long serviceId);

    //根据服务ID获取模型数量
    Integer selectCountByServiceId(@Param("serviceId") Long serviceId);

    /**
     * 根据服务ID查询所有的模型列表
     * @param serviceId
     * @return
     */
    List<Model> selectAllModelNameList(@Param("serviceId")Long serviceId);

    /**
     * 获得根据发布时间排序的模型ID
     * @return
     */
    List<Model> findLastModelSelect();
}
