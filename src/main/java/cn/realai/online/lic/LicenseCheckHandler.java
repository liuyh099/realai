package cn.realai.online.lic;


import java.util.List;

/**
 * Description: 授权检查扩展逻辑
 * <br>
 * <br>Author:  Shunping.Fu
 * <br>Date: 2019/3/15
 */

public interface LicenseCheckHandler {

    /**
     * 获取授权信息使用密文
     *
     * @return ciphertext 数据库密文
     */
    String getDataCiphertext(long serviceId);

    /**
     * 获取服务密文
     *
     * @param serviceId
     * @param secretKeyType 秘钥类型
     * @return
     */
    String getServiceCiphertext(long serviceId, SecretKeyType secretKeyType) throws LicenseException;


    /**
     * 更新授权服务信息（部署次数..）
     *
     * @param tuningSecretKey
     */
    void updateServiceDetail(long serviceId, String tuningSecretKey, ServiceDetail serviceDetail) throws LicenseException;


    /**
     * 清除过期调优秘钥
     *
     * @param serviceId
     */
    void clearTuningKey(long serviceId, ServiceLicenseInfoSource serviceLicenseInfoSource) throws LicenseException;

    /**
     * 检查调优秘钥是否使用过
     *
     * @param serviceId
     * @param secretKey
     */
    void checkSecretKeyApply(long serviceId, String secretKey, ServiceDetail sd) throws LicenseException;


    void cancelSecretKeyCheck(String secretKey) throws LicenseException;

    List<String> getCancelSecretKeyList(FileLicenseInfo fileLicenseInfo);

}
