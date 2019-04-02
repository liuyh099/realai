package cn.realai.online.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.realai.online.core.entity.GroupDif;

public interface GroupDifDao {

	void insertList(@Param("gdList")List<GroupDif> gdList);
	
}
