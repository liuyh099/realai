package cn.realai.online.core.bussiness.impl;

import cn.realai.online.core.bo.ServiceBO;
import cn.realai.online.core.bussiness.ServiceBussiness;
import cn.realai.online.core.entity.Model;
import cn.realai.online.core.entity.Service;
import cn.realai.online.core.service.ModelService;
import cn.realai.online.core.service.ServiceService;
import cn.realai.online.core.vo.service.SecretKeyInfoVO;
import cn.realai.online.core.vo.service.ServiceVO;
import cn.realai.online.lic.*;
import cn.realai.online.util.DateUtil;
import cn.realai.online.util.StringUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private ModelService modelService;

    @Autowired
    private DataCipherHandler dataCipherHandler;

    @Autowired
    private ServiceLicenseInfoSource serviceLicenseInfoSource;

    @Autowired
    private LicenseCheckHandler licenseCheckHandler;

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
    @Transactional(readOnly = false)
    public boolean editService(ServiceBO serviceBO) throws LicenseException {

        ServiceBO serviceBOold = serviceService.selectServiceById(serviceBO.getId());

        if (serviceBOold == null) {
            logger.error("编辑服务，服务ID不存在！ID="+serviceBO.getId());
            return false;
        }

        String oldKey = serviceBOold.getSecretKey();

        cn.realai.online.core.entity.Service searchService = new cn.realai.online.core.entity.Service();

        if(StringUtils.isNotBlank(serviceBO.getName()) && !StringUtils.equals(serviceBOold.getName(), serviceBO.getName())) {
            searchService.setName(serviceBO.getName());
            List old = serviceService.list(searchService);
            if(old != null && old.size() > 0) {
                logger.error("服务名称已存在！");
                throw new RuntimeException("服务名称已存在！");
            }
        }

        BeanUtils.copyProperties(serviceBO, serviceBOold);

        ServiceDetail serviceDetail = dataCipherHandler.getDateJsonByCiphertext(serviceBOold.getDetail());
        String sk = dataCipherHandler.getOriginalSecretKey(oldKey);
        serviceDetail.setServiceName(serviceBOold.getName());

        //续期
        if(serviceBO.isRenewal()) {
            searchService = new cn.realai.online.core.entity.Service();

            List<Service> old = serviceService.list(searchService);
            old.forEach(service -> {
                if(StringUtils.isNotEmpty(service.getSecretKey())) {
                    String secretkey = dataCipherHandler.getOriginalSecretKey(service.getSecretKey());
                    if(StringUtils.equals(secretkey, serviceBO.getSecretKey())) {
                        logger.error("服务秘钥已被使用！");
                        throw new RuntimeException("服务秘钥已被使用！");
                    }
                }
            });

            FileLicenseInfo fileLicenseInfo = serviceLicenseInfoSource.checkSource(serviceBO.getSecretKey());
            if(fileLicenseInfo.getOverdue() > 0 && (new Date().getTime() > fileLicenseInfo.getOverdue())) {
                throw new RuntimeException("秘钥已过期！");
            }

            int version = serviceDetail.getVersion();
            String newkey = dataCipherHandler.initSecretKey(serviceBO.getSecretKey(), version);
            serviceBOold.setSecretKey(newkey);

            licenseCheckHandler.cancelSecretKeyCheck(serviceBO.getSecretKey());

            FileLicenseInfo fileLicenseInfoOld = serviceLicenseInfoSource.checkSource(dataCipherHandler.getOriginalSecretKey(oldKey));
            if(DateUtil.stringToLong(fileLicenseInfo.getRangeTimeUpper(), LicenseConstants.DATE_FORMART) < DateUtil.stringToLong(fileLicenseInfoOld.getRangeTimeUpper(), LicenseConstants.DATE_FORMART)
                    || Integer.parseInt(fileLicenseInfo.getDeployTimesUpper()) < Integer.parseInt(fileLicenseInfoOld.getDeployTimesUpper())) {
                throw new RuntimeException("当前服务有效期限或使用次数高于续期秘钥！");
            }

            if(!StringUtils.equals(fileLicenseInfo.getServiceName(),fileLicenseInfoOld.getServiceName())
                    || !StringUtils.equals(fileLicenseInfo.getCompanyName(), fileLicenseInfoOld.getCompanyName())) {
                throw new RuntimeException("当前续期秘钥与服务秘钥不匹配！");
            }

            List<String> cancelSecretKeyList = licenseCheckHandler.getCancelSecretKeyList(fileLicenseInfo);

            if(!cancelSecretKeyList.isEmpty()) {
                String cancelKeys = serviceDetail.getTuningKeyIds();

                for (String cancelSecretKey : cancelSecretKeyList) {
                    if(StringUtils.isNotEmpty(cancelSecretKey)
                            && cancelKeys.indexOf(cancelSecretKey) == -1) {
                        cancelKeys += cancelSecretKey + ",";
                    }
                }
                if(!cancelKeys.startsWith(",")) {
                    cancelKeys = "," + cancelKeys;
                }
                serviceDetail.setTuningKeyIds(cancelKeys);
            }
        }

        serviceBOold.setDetail(dataCipherHandler.encryptData(serviceDetail, sk));

        if (serviceService.update(serviceBOold) <= 0) {
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
    public FileLicenseInfo getSecretKeyInfo(String serviceKey) throws LicenseException {
        FileLicenseInfo fileLicenseInfo = serviceLicenseCheck.checkServiceLic(serviceKey);
        return fileLicenseInfo;
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
    public List<ServiceBO> getServiceList(ServiceBO serviceBO) {
        Service service = new Service();
        BeanUtils.copyProperties(serviceBO, service);
        List<Service> services = serviceService.list(service);
        List<ServiceBO> serviceBos = new ArrayList<>();
        if(services != null && !services.isEmpty()) {
            for (Service s : services) {
                ServiceBO bo = new ServiceBO();
                BeanUtils.copyProperties(s, bo);
                convertData(bo);
                serviceBos.add(bo);
            }
        }
        return serviceBos;
    }

    @Override
    @Transactional(readOnly = false)
    public void renewalService(ServiceBO serviceBO) throws LicenseException {
        serviceBO.setRenewal(true);
        editService(serviceBO);
    }

    @Override
    public List<ServiceBO> findAlreadyPublish() {
        Service service = new Service();
//        service.setStatus(1);
        List<Service> services = serviceService.list(service);
        List<ServiceBO> list=JSON.parseArray(JSON.toJSONString(services),ServiceBO.class);
        return list;
    }

    @Override
    public List<ServiceBO> getAlreadyPublishService() {
        return serviceService.getAlreadyPublishService();
    }

    @Override
    public boolean checkService(long serviceId, String releaseStatus) {
        Model model = new Model();
        model.setReleaseStatus(releaseStatus);
        model.setServiceId(serviceId);
        List<Model> models = modelService.findList(model);
        if(models != null && !models.isEmpty()) {
            return serviceService.checkService(serviceId);
        }
        return false;
    }

    @Override
    public void deleteService(List<Long> ids) {
        if (serviceService.delete(ids) <= 0) {
            throw new RuntimeException("删除服务异常！");
        }
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
        String expireStr = DateUtil.milliLongToString(serviceBO.getExpireDate(), LicenseConstants.DATE_FORMART);
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
