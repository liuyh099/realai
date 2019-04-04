package cn.realai.online.lic;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Description:
 * <br>
 * <br>Author:  Shunping.Fu
 * <br>Date: 2019/3/15
 */
@Component
public class DataCipherHandler {

    private static final Logger log = LoggerFactory.getLogger(DataCipherHandler.class);

    @Autowired
    private ServiceLicenseInfoSource serviceLicenseInfoSource;

    @Value("${data.privateKey}")
    public String secretPrivateKey;

    private final int KEY_INDEX = 21;

    private final int KEY_LEN = 32;

    private final String M_KEY = "gt81";

    /**
     * 入库前数据加密
     *
     * @param serviceDetail
     * @return
     * @throws LicenseException
     */
    public String getDataCiphertext(long serviceId, ServiceDetail serviceDetail) throws LicenseException {

        String secretPublicKey = serviceLicenseInfoSource.getServiceLicInfo(serviceId).getSecretPublicKey();

        try {
            return RSAUtils.encryptByPublicKey(JSONObject.toJSONString(serviceDetail), secretPublicKey);
        } catch (Exception e) {
            log.error("数据加密失败", e);
            throw new LicenseException("数据加密失败!");
        }
    }

    public String encryptData(ServiceDetail serviceDetail, String secretKey) throws LicenseException {
        FileLicenseInfo fileLicenseInfo = serviceLicenseInfoSource.checkSource(secretKey);
        try {
            return RSAUtils.encryptByPublicKey(JSONObject.toJSONString(serviceDetail), fileLicenseInfo.getSecretPublicKey());
        } catch (Exception e) {
            log.error("数据加密失败", e);
            throw new LicenseException("数据加密失败!");
        }
    }

    /**
     * 数据解密
     *
     * @param dataCiphertext
     * @return
     * @throws LicenseException
     */
    public ServiceDetail getDateJsonByCiphertext(String dataCiphertext) throws LicenseException {

        try {
            String json = RSAUtils.decryptByPrivateKey(dataCiphertext, secretPrivateKey);

            return JSONObject.parseObject(json, ServiceDetail.class);
        } catch (Exception e) {
//            log.error("数据解密失败", e);
            throw new LicenseException("数据解密失败!");
        }

    }


    public String initSecretKey(String secretkey, int detailVersion) {
        String versionKey = MD5Util.MD5Encrypt(detailVersion+"", M_KEY);
        secretkey = secretkey.substring(0, KEY_INDEX) + versionKey +secretkey.substring(KEY_INDEX);
        return secretkey;
    }


    public String updateSecretKey(String secretkey, int detailVersion) throws LicenseException {
        String versionKey = MD5Util.MD5Encrypt(detailVersion+"", M_KEY);
        secretkey = secretkey.substring(0, KEY_INDEX) + versionKey +secretkey.substring(KEY_INDEX + KEY_LEN);
        return secretkey;
    }


    public void checkVersionSecretKey(String secretkey, int detailVersion) throws LicenseException {
        String versionKey = MD5Util.MD5Encrypt(detailVersion+"", M_KEY);
        String versionSecretKey = secretkey.substring(KEY_INDEX, KEY_INDEX+KEY_LEN);
        if(!StringUtils.equals(versionSecretKey, versionKey)) {
            throw new LicenseException("秘钥错误！");
        }
    }

    public String getOriginalSecretKey(String secretkey, int deailVersion) throws LicenseException {
        checkVersionSecretKey(secretkey, deailVersion);
        secretkey = secretkey.substring(0, KEY_INDEX) + secretkey.substring(KEY_INDEX + KEY_LEN);
        return secretkey;
    }

    public String getOriginalSecretKey(String secretkey) {
        if(secretkey.length() < (KEY_INDEX + KEY_LEN))return secretkey;
        secretkey = secretkey.substring(0, KEY_INDEX) + secretkey.substring(KEY_INDEX + KEY_LEN);
        return secretkey;
    }


}
