package cn.realai.online.core.service;

import java.util.List;

import cn.realai.online.core.entity.PersonalComboResultSet;

public interface PersonalComboResultSetService {

    void insertList(List<PersonalComboResultSet> comboList);

    /**
     * 异质最强组合特征
     *
     * @param personal
     * @return
     */
    List<PersonalComboResultSet> findList(PersonalComboResultSet personal);
}
