package cn.realai.online.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.realai.online.core.entity.SampleWeight;

public interface SampleWeightDao {

    void insertList(@Param("swList") List<SampleWeight> swList);

}
