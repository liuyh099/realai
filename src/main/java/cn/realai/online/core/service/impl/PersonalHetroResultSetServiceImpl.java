package cn.realai.online.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.realai.online.core.dao.PersonalHetroResultSetDao;
import cn.realai.online.core.entity.PersonalHetroResultSet;
import cn.realai.online.core.service.PersonalHetroResultSetService;

@Service
public class PersonalHetroResultSetServiceImpl implements PersonalHetroResultSetService {

	@Autowired
	private PersonalHetroResultSetDao personalHetroResultSetDao;
	
	@Override
	public void insertList(List<PersonalHetroResultSet> hetroList) {
		if (hetroList == null || hetroList.size() == 0) {
			return ;
		}
		personalHetroResultSetDao.insertList(hetroList);
	}

	@Override
	public List<PersonalHetroResultSet> findList(PersonalHetroResultSet query) {
		return personalHetroResultSetDao.findList(query);
	}

}
