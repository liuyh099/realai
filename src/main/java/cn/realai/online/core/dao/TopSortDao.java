package cn.realai.online.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.realai.online.core.entity.TopSort;

public interface TopSortDao {

    void insertList(@Param("tsList") List<TopSort> tsList);

    /**
     * 查找TopSortList
     *
     * @param topSort
     * @return
     */
    List<TopSort> findList(TopSort topSort);
}
