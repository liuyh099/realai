package cn.realai.online.lic;

import cn.realai.online.core.entity.Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
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
   public void checkServiceLic(long serviceId) throws LicenseException {
       String serviceCiphertext = licenseCheckHandler.getServiceCiphertext(serviceId, SecretKeyType.COMMON);
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
     * 调优秘钥检查是否可用
     *
     * @param secretKey  秘钥
     */
    public void checkTuningServiceLic(String secretKey, long serviceId) throws LicenseException {
        String serviceCiphertext = licenseCheckHandler.getServiceCiphertext(serviceId, SecretKeyType.COMMON);
        FileLicenseInfo tuningLicInfo = checkServiceLic(secretKey);
        FileLicenseInfo licInfo = checkServiceLic(secretKey);

        if(Integer.parseInt(tuningLicInfo.getSecretKeyType()) != SecretKeyType.TUNING.getCode()) {
            throw new LicenseException("秘钥类型不匹配，普通秘钥不能用于强制调优！");
        }

        checkLic(serviceCiphertext, secretKey);

        try {
            ServiceDetail serviceDetail = serviceLicenseInfoSource.licenseCheck(licInfo, serviceId, serviceCiphertext);
            licenseCheckHandler.checkSecretKeyApply(serviceId, secretKey, serviceDetail);
        }catch (Exception e) {
            throw new LicenseException("当前秘钥与该服务类型不匹配！");
        }

    }

    /**
     *
     * 服务使用（普通调优）
     *
     * @param serviceId
     */
    public void applyService(long serviceId) throws LicenseException {
        String serviceCiphertext = licenseCheckHandler.getServiceCiphertext(serviceId, SecretKeyType.COMMON);
        FileLicenseInfo licInfo = checkServiceLic(serviceCiphertext);

        serviceLicenseInfoSource.licenseDiscardCheck(serviceId);

        ServiceDetail serviceDetail = serviceLicenseInfoSource.licenseCheck(licInfo, serviceId, serviceCiphertext);
        licenseCheckHandler.updateServiceDetail(serviceId, null, serviceDetail);
    }


    /**
     * 服务使用(强制调优)  秘钥验证
     *
     * @param serviceId
     */
    public void applyService(long serviceId, String tuningSecretKey) throws LicenseException {
        //获取服务密文
        String serviceCiphertext = licenseCheckHandler.getServiceCiphertext(serviceId, SecretKeyType.COMMON);
        //解密调优秘钥
        FileLicenseInfo tuningLicInfo = checkServiceLic(tuningSecretKey);
        //解密服务秘钥
        FileLicenseInfo licInfo = checkServiceLic(serviceCiphertext);

        if(Integer.parseInt(tuningLicInfo.getSecretKeyType()) != SecretKeyType.TUNING.getCode()) {
            throw new LicenseException("秘钥类型不匹配，普通秘钥不能用于强制调优！");
        }

        //作废秘钥检查
        serviceLicenseInfoSource.licenseDiscardCheck(serviceId);

        try {
            checkLic(serviceCiphertext, tuningSecretKey);
        }catch (Exception e) {
            throw new LicenseException("当前秘钥与该服务类型不匹配！");
        }

        //获取服务扩展信息并且使用调优次数
        ServiceDetail serviceDetail = serviceLicenseInfoSource.licenseCheck(licInfo, serviceId, serviceCiphertext);
        //更新服务扩展信息
        licenseCheckHandler.updateServiceDetail(serviceId, tuningSecretKey, serviceDetail);
        //清除过期
        licenseCheckHandler.clearTuningKey(serviceId, serviceLicenseInfoSource);

    }




    /**
     * 检查服务秘钥与调优秘钥是否匹配
     *
     * @param secretKey
     * @param tuningSecretKey
     * @return
     * @throws LicenseException
     */
    public void checkLic(String secretKey, String tuningSecretKey) throws LicenseException {
        FileLicenseInfo serviceLicInfo = serviceLicenseInfoSource.checkSource(secretKey);
        FileLicenseInfo tuningLicInfo = serviceLicenseInfoSource.checkSource(tuningSecretKey);
        if(!StringUtils.equals(serviceLicInfo.getServiceName(), tuningLicInfo.getServiceName())) {
            throw new LicenseException("服务秘钥与调优秘钥不匹配！");
        }

    }

}
