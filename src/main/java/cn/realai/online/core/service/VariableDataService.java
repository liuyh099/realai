package cn.realai.online.core.service;

import java.util.List;

import cn.realai.online.core.bo.HomoAndHetroBO;
import cn.realai.online.core.bo.VariableDataBO;
import cn.realai.online.core.entity.VariableData;

public interface VariableDataService {

    /*
     * 插入变量数据
     */
    int insertVariableDataList(List<VariableData> vdList);

    /*
     * 根据实验id查询变量数据
     * @param experimentId
     * @return
     */
    List<VariableData> findListByExperimentId(Long experimentId);

    /**
     * 根据实验id与模式类型查询变量数据
     *
     * @param variableData
     * @return
     */
    List<VariableDataBO> findList(VariableData variableData);


    /**
     * 查询实验参数
     *
     * @param variableData
     * @return
     */
    List<VariableData> findVariableDataList(VariableData variableData);

    /**
     * 删除数据
     *
     * @param experimentId
     * @param ids
     */
    void deleteVariableData(Long experimentId, List<Long> ids);

	/**
	 * 查询训练时要删除的列
	 * @param experimentId
	 * @return
	 */
	HomoAndHetroBO selectDeleteByExperimentId(Long experimentId, int deleteStatus);

    /**
     * 获得二次创建实验的参数
     * @param variableIdList
     * @return
     */
    List<VariableData> findDoubleCreateVariableDataList(List<Long> variableIdList);

    /**
     * 获得变量名称
     * @param variableId
     * @return
     */
    VariableData getById(Long variableId);
}
