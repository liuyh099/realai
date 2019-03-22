package cn.realai.online.lic;

import cn.realai.online.core.entity.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Description: 服务秘钥检查相关接口
 * <br>
 * <br>Author:  Shunping.Fu
 * <br>Date: 2019/3/18
 */
@Component
public class ServiceLicenseCheck {

    @Autowired
    private ServiceLicenseInfoSource serviceLicenseInfoSource;

    @Autowired
    private DataCipherHandler dataCipherHandler;

    @Autowired
    private LicenseCheckHandler licenseCheckHandler;

    /**
     * 服务授权检查
     *
     * @param serviceId
     */
   public void checkServiceLic(long serviceId, SecretKeyType secretKeyType) throws LicenseException {
       String serviceCiphertext = licenseCheckHandler.getServiceCiphertext(serviceId,secretKeyType);
       serviceLicenseInfoSource.checkSource(serviceCiphertext);
    }

    /**
     * 服务授权检查（解密）
     *
     * @param secretKey  秘钥
     */
    public FileLicenseInfo checkServiceLic(String secretKey) throws LicenseException {
        return serviceLicenseInfoSource.checkSource(secretKey);
    }


    /**
     * 服务使用(强制调优)
     *
     * @param serviceId
     */
    public void applyService(long serviceId) throws LicenseException {
        String serviceCiphertext = licenseCheckHandler.getServiceCiphertext(serviceId, SecretKeyType.TUNING);
        FileLicenseInfo fileLicenseInfo = serviceLicenseInfoSource.checkSource(serviceCiphertext);
        ServiceDetail serviceDetail = serviceLicenseInfoSource.licenseCheck(fileLicenseInfo, serviceId);
        String dataCiphertext = dataCipherHandler.getDataCiphertext(serviceId, serviceDetail);
        licenseCheckHandler.updateServiceDetail(serviceId, dataCiphertext);
    }




}
