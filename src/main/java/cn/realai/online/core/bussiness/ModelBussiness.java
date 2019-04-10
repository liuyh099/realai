package cn.realai.online.core.bussiness;

import cn.realai.online.common.page.PageBO;
import cn.realai.online.core.bo.ModelBO;
import cn.realai.online.core.entity.Model;
import cn.realai.online.core.query.ModelListQuery;
import cn.realai.online.core.vo.ModelDetailVO;
import cn.realai.online.core.vo.ModelListVO;
import cn.realai.online.core.vo.ModelNameSelectVO;
import cn.realai.online.core.vo.ModelSelectVO;

import java.util.List;
import java.util.Map;

public interface ModelBussiness {
    PageBO<ModelListVO> pageList(ModelListQuery query);

    ModelDetailVO selectModelDetail(Long modelId);

    List<ModelNameSelectVO> selectModelNameList(Long serviceId);

    ModelSelectVO selectRecentModelNameList();

    /**
     * 检查模型名称是否可用
     * @param name
     * @return
     */
    Boolean checkName(String name);

    /**
     * 发布实验
     * @param modelBO
     * @return
     */
    Map<String,Object> publish(ModelBO modelBO);

    /**
     * 根据模型ID查询实验ID
     * @param id
     * @return
     */
    Model getTrainByModelId(Long id);

    /**
     * 获得服务发布模型的历史记录
     * @param serviceId
     * @return
     */
    List<ModelBO> findModelOptionHistory(Long serviceId);

    /**
     * 根据服务ID查询所有的模型选择
     * @param serviceId
     * @return
     */
    List<ModelNameSelectVO> selectAllModelNameList(Long serviceId);

    /**
     * 获得根据发布时间排序的模型下拉
     * @return
     */
    List<ModelBO> findLastModelSelect();
}
