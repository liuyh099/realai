package cn.realai.online.core.bussiness;

import cn.realai.online.core.bo.ServiceDeployRecordBO;

import java.util.List;

/**
 * 实验发布记录
 */
public interface ServiceDeployRecordBussiness {

    /**
     * 插入服务发布记录
     * @param serviceDeployRecordBO
     * @return
     */
    public int insert(ServiceDeployRecordBO serviceDeployRecordBO);

    /**
     * 根据服务ID查询服务发布记录
     * @param serviceId
     * @return
     */
    public List<ServiceDeployRecordBO> findServiceDeployRecordByServiceId(Long serviceId);
}
