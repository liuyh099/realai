package cn.realai.online.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.realai.online.core.entity.PersonalComboResultSet;

public interface PersonalComboResultSetDao {

	void insertList(@Param("comboList")List<PersonalComboResultSet> comboList);

}
