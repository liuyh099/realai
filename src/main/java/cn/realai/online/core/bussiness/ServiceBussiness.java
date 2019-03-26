package cn.realai.online.core.bussiness;

import cn.realai.online.core.bo.ServiceBO;
import cn.realai.online.core.entity.Service;
import cn.realai.online.core.vo.service.SecretKeyInfoVO;
import cn.realai.online.core.vo.service.ServiceVO;
import cn.realai.online.lic.FileLicenseInfo;
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

    FileLicenseInfo getSecretKeyInfo(String serviceKey) throws LicenseException;

    List<ServiceBO> getServiceList(ServiceBO serviceBO);

    /**
     * 服务续期
     *
     * @param serviceBO
     */
    void renewalService(ServiceBO serviceBO);
}
