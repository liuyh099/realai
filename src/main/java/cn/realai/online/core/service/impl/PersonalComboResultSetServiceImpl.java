package cn.realai.online.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.realai.online.core.dao.PersonalComboResultSetDao;
import cn.realai.online.core.entity.PersonalComboResultSet;
import cn.realai.online.core.service.PersonalComboResultSetService;

@Service
public class PersonalComboResultSetServiceImpl implements PersonalComboResultSetService {

	@Autowired
	private PersonalComboResultSetDao personalComboResultSetDao;
	
	@Override
	public void insertList(List<PersonalComboResultSet> comboList) {
		if (comboList == null || comboList.size() == 0) {
			return ;
		}
		personalComboResultSetDao.insertList(comboList);
	}

}
