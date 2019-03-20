package cn.realai.online.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.realai.online.core.entity.PersonalHomoResultSet;

public interface PersonalHomoResultSetDao {

	void insertList(@Param("homoList")List<PersonalHomoResultSet> homoList);

}
