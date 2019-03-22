package cn.realai.online.lic;

import cn.realai.online.core.entity.Service;
import cn.realai.online.core.service.ServiceService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Description:
 * <br>
 * <br>Author:  Shunping.Fu
 * <br>Date: 2019/3/18
 */
@Component
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
        String ciphertext = "";
        if(secretKeyType == SecretKeyType.COMMON) {
            ciphertext = service.getSecretKey();
        }else {
            ciphertext = service.getTuningSecretKey();
        }

        if(StringUtils.isBlank(ciphertext)) {
            String msg = "缺少服务"+secretKeyType.getMsg()+"秘钥!";
            logger.error(msg);
            throw new LicenseException(msg);
        }
        return ciphertext;
    }


    @Override
    public void updateServiceDetail(long serviceId, String dataCiphertext) {
        Service service = serviceService.selectServiceById(serviceId);
        service.setDetail(dataCiphertext);
        serviceService.update(service);
    }


}
