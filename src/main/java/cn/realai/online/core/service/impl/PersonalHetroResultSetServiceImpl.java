package cn.realai.online.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.realai.online.core.dao.PersonalHetroResultSetDao;
import cn.realai.online.core.entity.PersonalHetroResultSet;
import cn.realai.online.core.service.PersonalHetroResultSetService;
import cn.realai.online.util.CollectionUtil;

@Service
public class PersonalHetroResultSetServiceImpl implements PersonalHetroResultSetService {

    @Autowired
    private PersonalHetroResultSetDao personalHetroResultSetDao;

    @Override
    public void insertList(List<PersonalHetroResultSet> hetroList) {
        if (hetroList == null || hetroList.size() == 0) {
            return;
        }
        
        List<List<PersonalHetroResultSet>> collectionList = CollectionUtil.segmentationList((List) hetroList, PersonalHetroResultSet.class, 1000);
        for (List<PersonalHetroResultSet> list : collectionList) {
        	personalHetroResultSetDao.insertList(list);
        }
    }

    @Override
    public List<PersonalHetroResultSet> findList(PersonalHetroResultSet query) {
        return personalHetroResultSetDao.findList(query);
    }

}
