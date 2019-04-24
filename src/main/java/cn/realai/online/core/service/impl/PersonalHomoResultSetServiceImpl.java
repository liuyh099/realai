package cn.realai.online.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.realai.online.core.dao.PersonalHomoResultSetDao;
import cn.realai.online.core.entity.PersonalHetroResultSet;
import cn.realai.online.core.entity.PersonalHomoResultSet;
import cn.realai.online.core.service.PersonalHomoResultSetService;
import cn.realai.online.util.CollectionUtil;

@Service
public class PersonalHomoResultSetServiceImpl implements PersonalHomoResultSetService {

    @Autowired
    private PersonalHomoResultSetDao personalHomoResultSetDao;

    @Override
    public void insertList(List<PersonalHomoResultSet> homoList) {
        if (homoList == null || homoList.size() == 0) {
            return;
        }
        List<List<PersonalHomoResultSet>> collectionList = CollectionUtil.segmentationList((List) homoList, PersonalHomoResultSet.class, 1000);
        for (List<PersonalHomoResultSet> list : collectionList) {
        	personalHomoResultSetDao.insertList(list);
        }
    }

    @Override
    public List<PersonalHomoResultSet> findList(PersonalHomoResultSet queryCondition) {
        return personalHomoResultSetDao.findList(queryCondition);
    }

    @Override
    public List<PersonalHomoResultSet> listCharts(Long id) {
        return personalHomoResultSetDao.listCharts(id);
    }

}
