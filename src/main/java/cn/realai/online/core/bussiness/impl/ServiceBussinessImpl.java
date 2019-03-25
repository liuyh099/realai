package cn.realai.online.core.bussiness.impl;

import cn.realai.online.core.bo.ServiceBO;
import cn.realai.online.core.bussiness.ServiceBussiness;
import cn.realai.online.core.entity.Service;
import cn.realai.online.core.service.ServiceService;
import cn.realai.online.core.vo.service.SecretKeyInfoVO;
import cn.realai.online.core.vo.service.ServiceVO;
import cn.realai.online.lic.FileLicenseInfo;
import cn.realai.online.lic.LicenseConstants;
import cn.realai.online.lic.LicenseException;
import cn.realai.online.lic.ServiceLicenseCheck;
import cn.realai.online.util.DateUtil;
import cn.realai.online.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Description:
 * <br>
 * <br>Author:  Shunping.Fu
 * <br>Date: 2019/3/20
 */
@org.springframework.stereotype.Service
public class ServiceBussinessImpl implements ServiceBussiness {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private ServiceLicenseCheck serviceLicenseCheck;

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

        ServiceBO serviceBOold = serviceService.selectServiceById(serviceBO.getId());

        if (serviceBOold == null) {
            logger.error("编辑服务，服务ID不存在！ID="+serviceBO.getId());
            return false;
        }

        cn.realai.online.core.entity.Service searchService = new cn.realai.online.core.entity.Service();

        if(!StringUtils.equals(serviceBOold.getName(), serviceBO.getName())) {
            searchService.setName(serviceBO.getName());
            List old = serviceService.list(searchService);
            if(old != null && old.size() > 0) {
                logger.error("服务名称已存在！");
                throw new RuntimeException("服务名称已存在！");
            }
        }

        if(!StringUtils.equals(serviceBOold.getSecretKey(), serviceBO.getSecretKey())) {
            searchService = new cn.realai.online.core.entity.Service();
            searchService.setSecretKey(serviceBO.getSecretKey());
            List old = serviceService.list(searchService);
            if(old != null && old.size() > 0) {
                logger.error("服务秘钥已被使用！");
                throw new RuntimeException("服务秘钥已被使用！");
            }
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
        convertData(serviceBO);
        return serviceBO;
    }

    @Override
    public SecretKeyInfoVO getSecretKeyInfo(String serviceKey) throws LicenseException {
        FileLicenseInfo fileLicenseInfo = serviceLicenseCheck.checkServiceLic(serviceKey);
        SecretKeyInfoVO secretKeyInfoVO = new SecretKeyInfoVO();
        secretKeyInfoVO.setStartTime(DateUtil.stringToLong(fileLicenseInfo.getRangeTimeLower(), LicenseConstants.DATE_FORMART));
        secretKeyInfoVO.setExpireDate(DateUtil.stringToLong(fileLicenseInfo.getRangeTimeUpper(), LicenseConstants.DATE_FORMART));
        secretKeyInfoVO.setType(Integer.parseInt(fileLicenseInfo.getServiceType()));
        return secretKeyInfoVO;
    }

//    public void bindTuningSecretKey(long serviceId, String tuningSecretKey) {
//
//        cn.realai.online.core.entity.Service searchService = new cn.realai.online.core.entity.Service();
//            searchService.setTuningSecretKey(tuningSecretKey);
//            List<Service> old = serviceService.list(searchService);
//            if(old != null && old.size() > 0) {
//                if(old.get(0).getId() != serviceId) {
//                    logger.error("调优秘钥已被使用！");
//                    throw new RuntimeException("调优秘钥已被使用！");
//                }
//            }
//
//        ServiceBO serviceBO = serviceService.selectServiceById(serviceId);
//        Service service = new Service();
//        BeanUtils.copyProperties(serviceBO, service);
//        service.setTuningSecretKey(tuningSecretKey);
//        serviceService.update(service);
//    }

    @Override
    public List<ServiceVO> getServiceList(ServiceBO serviceBO) {
        Service service = new Service();
        BeanUtils.copyProperties(serviceBO, service);
        List<Service> services = serviceService.list(service);
        List<ServiceVO> serviceVos = new ArrayList<>();
        if(services != null && !services.isEmpty()) {
            for (Service s : services) {
                ServiceBO bo = new ServiceBO();
                BeanUtils.copyProperties(s, bo);
                convertData(bo);
                ServiceVO vo = new ServiceVO();
                BeanUtils.copyProperties(bo, vo);
                serviceVos.add(vo);
            }
        }
        return serviceVos;
    }

    @Override
    public void renewalService(ServiceBO serviceBO) {
        editService(serviceBO);
    }

    /**
     * 实装BO属性
     *
     * @param serviceBO
     * @return
     */
    private void convertData(ServiceBO serviceBO) {

        Date now = new Date();
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(now);
        int nowDay = aCalendar.get(Calendar.DAY_OF_YEAR);
        String expireStr = DateUtil.longToString(serviceBO.getExpireDate(), LicenseConstants.DATE_FORMART);
        aCalendar.setTime(DateUtil.stringToDate(expireStr, LicenseConstants.DATE_FORMART));
        int expireDay = aCalendar.get(Calendar.DAY_OF_YEAR);

        serviceBO.setRenewable(false);
        /**
         * 距离到期日一个月时或者部署次数剩余3次
         */
        if( (expireDay - nowDay) <= 30
                || (serviceBO.getDeployTimesUpper() - serviceBO.getReleaseCount()) <= 3) {
            serviceBO.setRenewable(true);
        }
    }


}
