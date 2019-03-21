package cn.realai.online.core.service;

import java.util.List;

import cn.realai.online.core.entity.TopSort;

public interface TopSortService {

    void insertList(List<TopSort> tsList);

    /**
     * 查询TopSort
     *
     * @param topSort
     * @return
     */
    List<TopSort> findList(TopSort topSort);
}
