package cn.realai.online.lic;

import cn.realai.online.core.entity.Service;
import cn.realai.online.core.service.ServiceService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Autowired
    private DataCipherHandler dataCipherHandler;

    @Autowired
    private ServiceLicenseInfoSource serviceLicenseInfoSource;

    @Override
    public String getDataCiphertext(long serviceId) {
        Service service = serviceService.selectServiceById(serviceId);
        return service.getDetail();
    }


    @Override
    public String getServiceCiphertext(long serviceId, SecretKeyType secretKeyType) throws LicenseException {
        Service service = serviceService.selectServiceById(serviceId);
        ServiceDetail serviceDetail = dataCipherHandler.getDateJsonByCiphertext(service.getDetail());
        String ciphertext = dataCipherHandler.getOriginalSecretKey(service.getSecretKey(), serviceDetail.getVersion());
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
    public void updateServiceDetail(long serviceId, String tuningSecretKey, ServiceDetail serviceDetail) throws LicenseException {

        Service service = serviceService.selectServiceById(serviceId);
        String secretKey = service.getSecretKey();
        String tuningKeyIds = serviceDetail.getTuningKeyIds();

//        if(!StringUtils.equals(serviceDetail.getServiceName(), service.getName())) {
//            logger.error("调优次数数据异常！");
//            throw new LicenseException("系统异常！数据错误！");
//        }

        if(StringUtils.isNotBlank(tuningSecretKey)) {
            FileLicenseInfo licenseInfo = serviceLicenseInfoSource.checkSource(tuningSecretKey);
            String tuningKey = service.getTuningSecretKey();
            if(StringUtils.isNotBlank(tuningKey)) {
                if(tuningKey.indexOf(tuningSecretKey) != -1) {
                    throw new LicenseException("该秘钥已作废！");
                }
                tuningKey += tuningSecretKey + ",";
            }else {
                tuningKey = "," + tuningSecretKey + ",";
            }
            service.setTuningSecretKey(tuningKey);

            if(StringUtils.isNotBlank(tuningKeyIds)) {
                if(tuningKeyIds.indexOf(licenseInfo.getId()) != -1) {
                    throw new LicenseException("该秘钥已作废！");
                }
                tuningKeyIds += licenseInfo.getId() + ",";
            }else {
                tuningKeyIds = "," + licenseInfo.getId() + ",";
            }

            List<String> cancelSecretKeyList = getCancelSecretKeyList(licenseInfo);
            if(!cancelSecretKeyList.isEmpty()) {
                for (String cancelSecretKey : cancelSecretKeyList) {
                    tuningKeyIds += cancelSecretKey + ",";
                }
            }
        }

        serviceDetail.setTuningKeyIds(tuningKeyIds);
        String dataCiphertext = dataCipherHandler.getDataCiphertext(serviceId, serviceDetail);
        String newSecretKey = dataCipherHandler.updateSecretKey(secretKey, serviceDetail.getVersion());
        service.setDetail(dataCiphertext);
        service.setSecretKey(newSecretKey);
        serviceService.update(service);

    }

    @Override
    @Transactional(readOnly = false)
    public void clearTuningKey(long serviceId, ServiceLicenseInfoSource serviceLicenseInfoSource) throws LicenseException {

        Service service = serviceService.selectServiceById(serviceId);

        ServiceDetail serviceDetail = dataCipherHandler.getDateJsonByCiphertext(service.getDetail());
        String tuningKeyIds = serviceDetail.getTuningKeyIds();

        String tuningKey = service.getTuningSecretKey();
        List<String> overdueTuningKeyIds = new ArrayList<>();

        String[] tuningkeys = tuningKey.split(",");
        for (String key : tuningkeys) {

            if(StringUtils.isNotBlank(key)) {
                //检查过期
                FileLicenseInfo licenseInfo = null;
                try {
                    licenseInfo = serviceLicenseInfoSource.checkSource(key);
                } catch (LicenseException e) {
                    tuningKey = tuningKey.replaceAll(","+key+",", ",");
                    overdueTuningKeyIds.add(licenseInfo.getId());
                }
            }
        }
        if(StringUtils.equals(tuningKey, ",")) {
            tuningKey = "";
        }

        if(!overdueTuningKeyIds.isEmpty() && StringUtils.isNotEmpty(tuningKeyIds)) {
            for (String overdueId : overdueTuningKeyIds) {
                if(tuningKeyIds.indexOf(","+overdueId+",") != -1) {
                    tuningKeyIds = tuningKeyIds.replaceAll(","+overdueId+",", ",");
                }
            }
        }
        serviceDetail.setTuningKeyIds(tuningKeyIds);
        String detailCiphertext = dataCipherHandler.getDataCiphertext(serviceId, serviceDetail);
        service.setDetail(detailCiphertext);
        service.setTuningSecretKey(tuningKey);
        serviceService.update(service);
    }

    @Override
    public void checkSecretKeyApply(long serviceId, String tuningSecretKey, ServiceDetail serviceDetail) throws LicenseException {
        Service service = serviceService.selectServiceById(serviceId);
        String tuningKeyIds = serviceDetail.getTuningKeyIds();

        if(StringUtils.isNotBlank(tuningSecretKey)) {
            FileLicenseInfo licenseInfo = serviceLicenseInfoSource.checkSource(tuningSecretKey);
            String tuningKey = service.getTuningSecretKey();
            if(StringUtils.isNotBlank(tuningKey)) {
                if(tuningKey.indexOf(tuningSecretKey) != -1) {
                    throw new LicenseException("该秘钥已作废！");
                }
            }

            if(StringUtils.isNotBlank(tuningKeyIds)) {
                if(tuningKeyIds.indexOf(licenseInfo.getId()) != -1) {
                    throw new LicenseException("该秘钥已作废！");
                }
            }

        }
    }

    @Override
    public void cancelSecretKeyCheck(String secretKey) throws LicenseException {
        List<Service> olds = serviceService.list(new Service());
        FileLicenseInfo licenseInfo = serviceLicenseInfoSource.checkSource(secretKey);

        if(olds != null && !olds.isEmpty()) {
            for (Service oldService : olds) {
                if(StringUtils.isNotEmpty(oldService.getDetail())) {
                    ServiceDetail serviceDetail = dataCipherHandler.getDateJsonByCiphertext(oldService.getDetail());
                    String tuningKeyIds = serviceDetail.getTuningKeyIds();

                    if(StringUtils.isNotBlank(tuningKeyIds)) {
                        if(tuningKeyIds.indexOf(licenseInfo.getId()) != -1) {
                            throw new LicenseException("该秘钥已作废！");
                        }
                    }
                }
            }
        }
    }

    public List<String> getCancelSecretKeyList(FileLicenseInfo fileLicenseInfo) {
        String cancelKeys = fileLicenseInfo.getCancelSecretKey();
        List<String> cancelKeyList = new ArrayList<>();
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(cancelKeys)) {
            for(String s : cancelKeys.split(",")) {
                if(StringUtils.isNotEmpty(s)) {
                    cancelKeyList.add(s);
                }
            }

        }
        return cancelKeyList;
    }


}
