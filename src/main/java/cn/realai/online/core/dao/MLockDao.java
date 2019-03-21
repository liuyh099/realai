package cn.realai.online.core.dao;

import org.apache.ibatis.annotations.Param;

import cn.realai.online.core.entity.MLock;

public interface MLockDao {

    int tryLock(@Param("mlock") MLock mlock);

    int unLock(@Param("mlock") MLock mlock);

}
