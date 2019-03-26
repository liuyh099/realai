package cn.realai.online.lic;

import cn.realai.online.core.entity.Service;
import cn.realai.online.core.service.ServiceService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Description:
 * <br>
 * <br>Author:  Shunping.Fu
 * <br>Date: 2019/3/18
 */
@org.springframework.stereotype.Service
public class LicenseCheckHandlerService implements LicenseCheckHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ServiceService serviceService;

    @Override
    public String getDataCiphertext(long serviceId) {
        Service service = serviceService.selectServiceById(serviceId);
        return service.getDetail();
    }


    @Override
    public String getServiceCiphertext(long serviceId, SecretKeyType secretKeyType) throws LicenseException {
        Service service = serviceService.selectServiceById(serviceId);
        String ciphertext = service.getSecretKey();
//        if(secretKeyType == SecretKeyType.COMMON) {
//            ciphertext = service.getSecretKey();
//        }else {
//            ciphertext = service.getTuningSecretKey();
//        }

        if(StringUtils.isBlank(ciphertext)) {
            String msg = "请先绑定服务"+secretKeyType.getMsg()+"秘钥!";
            logger.error(msg);
            throw new LicenseException(msg);
        }
        return ciphertext;
    }


    @Override
    @Transactional(readOnly = false)
    public void updateServiceDetail(long serviceId, String dataCiphertext, String tuningSecretKey, ServiceDetail serviceDetail) throws LicenseException {

        Service service = serviceService.selectServiceById(serviceId);

        if(!StringUtils.equals(serviceDetail.getServiceName(), service.getName())) {
            logger.error("调优次数数据异常！");
            throw new LicenseException("系统异常！数据错误！");
        }

        String tuningKey = service.getTuningSecretKey();
        if(StringUtils.isNotBlank(tuningKey)) {
            if(tuningKey.indexOf(tuningSecretKey) != -1) {
                throw new LicenseException("调优秘钥已被使用！");
            }
            tuningKey += tuningSecretKey + ",";
        }else {
            tuningKey = "," + tuningSecretKey + ",";
        }

        service.setTuningSecretKey(tuningKey);
        service.setDetail(dataCiphertext);
        serviceService.update(service);

    }

    @Override
    @Transactional(readOnly = false)
    public void clearTuningKey(long serviceId, ServiceLicenseInfoSource serviceLicenseInfoSource) {

        Service service = serviceService.selectServiceById(serviceId);

        String tuningKey = service.getTuningSecretKey();

        String[] tuningkeys = tuningKey.split(",");
        for (String key : tuningkeys) {

            if(StringUtils.isNotBlank(key)) {
                //检查过期
                try {
                    serviceLicenseInfoSource.checkSource(key);
                } catch (LicenseException e) {
                    tuningKey = tuningKey.replaceAll(","+key+",", ",");
                }
            }
        }
        if(StringUtils.equals(tuningKey, ",")) {
            tuningKey = "";
        }

        service.setTuningSecretKey(tuningKey);
        serviceService.update(service);
    }


}
