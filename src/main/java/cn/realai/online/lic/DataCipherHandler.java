package cn.realai.online.lic;

import com.alibaba.fastjson.JSONObject;
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


    /**
     * 入库前数据加密
     *
     * @param serviceDetail
     * @return
     * @throws LicenseException
     */
    public String getDataCiphertext(long serviceId, ServiceDetail serviceDetail) throws LicenseException {

        String secretPublicKey = serviceLicenseInfoSource.getServiceLicInfo(serviceId, SecretKeyType.TUNING).getSecretPublicKey();

        try {
            return RSAUtils.encryptByPublicKey(JSONObject.toJSONString(serviceDetail), secretPublicKey);
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
            log.error("数据解密失败", e);
            throw new LicenseException("数据解密失败!");
        }

    }





}
