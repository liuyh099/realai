package cn.realai.online.core.service;

import java.util.List;

import cn.realai.online.core.entity.PersonalHetroResultSet;

public interface PersonalHetroResultSetService {

    void insertList(List<PersonalHetroResultSet> hetroList);

    /**
     * 查询异质数据
     *
     * @param query
     * @return
     */
    List<PersonalHetroResultSet> findList(PersonalHetroResultSet query);
}
