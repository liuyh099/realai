package cn.realai.online.core.bussiness.impl;

import cn.realai.online.core.bo.ServiceBO;
import cn.realai.online.core.bussiness.ServiceBussiness;
import cn.realai.online.core.entity.Service;
import cn.realai.online.core.service.ServiceService;
import cn.realai.online.core.vo.service.SecretKeyInfoVO;
import cn.realai.online.lic.LicenseException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Description:
 * <br>
 * <br>Author:  Shunping.Fu
 * <br>Date: 2019/3/20
 */
@org.springframework.stereotype.Service
public class ServiceBussinessImpl implements ServiceBussiness {


    @Autowired
    private ServiceService serviceService;

    @Override
    public boolean addService(ServiceBO serviceBO) throws LicenseException {
        Service service = new Service();
        BeanUtils.copyProperties(serviceBO, service);
        if (serviceService.insert(service) <= 0) {
            return false;
        }
        return true;
    }

    @Override
    public boolean editService(ServiceBO serviceBO) {

        serviceBO = serviceService.selectServiceById(serviceBO.getId());

        if (serviceBO == null) {
            return false;
        }
        Service service = new Service();
        BeanUtils.copyProperties(serviceBO, service);
        if (serviceService.update(service) <= 0) {
            return false;
        }
        return true;
    }

    @Override
    public ServiceBO getServiceDetails(ServiceBO serviceBO) {

        serviceBO = serviceService.selectServiceById(serviceBO.getId());

        return serviceBO;
    }

    @Override
    public SecretKeyInfoVO getSecretKeyInfo(String serviceKey) {
        return null;
    }
}
