package cn.realai.online.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.realai.online.core.entity.PersonalHetroResultSet;

public interface PersonalHetroResultSetDao {

	void insertList(@Param("hetroList")List<PersonalHetroResultSet> hetroList);

}
