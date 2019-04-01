package cn.realai.online.core.dao;

import cn.realai.online.core.entity.ServiceDeployRecord;

import java.util.List;

/**
 * 服务发布记录
 * @author shifengfan
 */
public interface ServiceDeployRecordDao {

    /**
     * 插入服务发布记录
     * @param serviceDeployRecord
     * @return
     */
    public int insert(ServiceDeployRecord serviceDeployRecord);

    /**
     * 查询服务发不记录
     * @param serviceDeployRecord
     * @return
     */
    public List<ServiceDeployRecord> findList(ServiceDeployRecord serviceDeployRecord);

}
