package cn.realai.online.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.realai.online.core.entity.PersonalHomoResultSet;

public interface PersonalHomoResultSetDao {

    void insertList(@Param("homoList") List<PersonalHomoResultSet> homoList);

    /**
     * 查询同质数据列表
     *
     * @param queryCondition
     * @return
     */
    List<PersonalHomoResultSet> findList(PersonalHomoResultSet queryCondition);

    /**
     * echarts数据
     *
     * @param id
     * @return
     */
    List<PersonalHomoResultSet> listCharts(@Param("id") Long id);
}
