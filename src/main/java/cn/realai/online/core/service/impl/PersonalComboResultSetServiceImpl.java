package cn.realai.online.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.realai.online.core.dao.PersonalComboResultSetDao;
import cn.realai.online.core.entity.PersonalComboResultSet;
import cn.realai.online.core.service.PersonalComboResultSetService;
import cn.realai.online.util.CollectionUtil;

@Service
public class PersonalComboResultSetServiceImpl implements PersonalComboResultSetService {

    @Autowired
    private PersonalComboResultSetDao personalComboResultSetDao;

	@Override
    @Transactional
    public void insertList(List<PersonalComboResultSet> comboList) {
        if (comboList == null || comboList.size() == 0) {
            return;
        }
        List<List<PersonalComboResultSet>> collectionList = CollectionUtil.segmentationList((List) comboList, PersonalComboResultSet.class, 1000);
        for (List<PersonalComboResultSet> list : collectionList) {
        	personalComboResultSetDao.insertList(list);
        }
        
    }

    @Override
    public List<PersonalComboResultSet> findList(PersonalComboResultSet personal) {
        return personalComboResultSetDao.findList(personal);
    }

}
