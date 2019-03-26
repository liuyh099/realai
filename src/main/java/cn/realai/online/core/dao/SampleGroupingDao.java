package cn.realai.online.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.realai.online.core.entity.SampleGrouping;

public interface SampleGroupingDao {

    void insertList(@Param("sgList") List<SampleGrouping> sgList);

    List<SampleGrouping> findListByExperimentId(@Param("experimentId") Long experimentId, 
    		@Param("isExceptionGroup")boolean isExceptionGroup,@Param("isAllGroup")boolean isAllGroup);

}
