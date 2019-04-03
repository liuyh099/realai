package cn.realai.online.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.realai.online.core.dao.GroupDifDao;
import cn.realai.online.core.entity.GroupDif;
import cn.realai.online.core.service.GroupDifService;

@Service
public class GroupDifServiceImpl implements GroupDifService {

	@Autowired
	private GroupDifDao groupDifDao;
	
	@Override
	public void insertList(List<GroupDif> gdList) {
		groupDifDao.insertList(gdList);
	}

	@Override
	public List<GroupDif> selectList(GroupDif groupDif) {
		return groupDifDao.selectList(groupDif);
	}

	@Override
	public List<String> selectGroupNameList(Long experimentId) {
		return groupDifDao.selectGroupNameList(experimentId);
	}
}
