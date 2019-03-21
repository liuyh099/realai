package cn.realai.online.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.realai.online.core.entity.VariableData;

public interface VariableDataDao {

    int insertList(@Param("vdList") List<VariableData> vdList);

    List<VariableData> findListByExperimentId(@Param("experimentId") Long experimentId);

    /**
     * 查询同质异质参数
     *
     * @param variableData
     * @return
     */
    List<VariableData> findList(VariableData variableData);

    /**
     * 删除数据
     *
     * @param experimentId
     * @param ids
     */
    void deleteVariableData(@Param("experimentId") Long experimentId, @Param("ids") List<Long> ids);
}
