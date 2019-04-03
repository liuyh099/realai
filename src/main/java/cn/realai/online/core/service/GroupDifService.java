package cn.realai.online.core.service;

import java.util.List;

import cn.realai.online.core.entity.GroupDif;

public interface GroupDifService {

	/*
	 * 批量插入分组对比
	 * @param gdList
	 */
	void insertList(List<GroupDif> gdList);

	List<GroupDif> selectList(GroupDif groupDif);

	//获取分组名称
	List<String> selectGroupNameList(Long experimentId);
}
