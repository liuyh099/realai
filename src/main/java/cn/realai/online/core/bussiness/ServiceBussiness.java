package cn.realai.online.core.bussiness;

import cn.realai.online.core.bo.ServiceBO;
import cn.realai.online.core.entity.Service;
import cn.realai.online.core.vo.service.SecretKeyInfoVO;
import cn.realai.online.core.vo.service.ServiceVO;
import cn.realai.online.lic.LicenseException;

import java.util.List;

/**
 * Description:
 * <br>
 * <br>Author:  Shunping.Fu
 * <br>Date: 2019/3/20
 */

public interface ServiceBussiness {


    boolean addService(ServiceBO serviceBO) throws LicenseException;

    boolean editService(ServiceBO serviceBO);

    ServiceBO getServiceDetails(ServiceBO serviceBO);

    SecretKeyInfoVO getSecretKeyInfo(String serviceKey) throws LicenseException;

    /**
     * 绑定服务对应的调优秘钥
     *
     * @param ServiceId
     * @param tuningSecretKey
     */
    void bindTuningSecretKey(long ServiceId, String tuningSecretKey);

    List<ServiceVO> getServiceList(ServiceBO serviceBO);
}
