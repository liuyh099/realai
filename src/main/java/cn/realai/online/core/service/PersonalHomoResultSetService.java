package cn.realai.online.core.service;

import java.util.List;

import cn.realai.online.core.entity.PersonalHomoResultSet;

public interface PersonalHomoResultSetService {

	void insertList(List<PersonalHomoResultSet> homoList);

    /**
     * 同质数据列表
     * @param queryCondition
     * @return
     */
    List<PersonalHomoResultSet> findList(PersonalHomoResultSet queryCondition);
}
