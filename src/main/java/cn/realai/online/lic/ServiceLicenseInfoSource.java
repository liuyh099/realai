package cn.realai.online.lic;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description:  服务授权检查
 * <br>
 * <br>Author:  Shunping.Fu
 * <br>Date: 2019/3/18
 */
@Component
public class ServiceLicenseInfoSource {

    private static final Logger logger = LoggerFactory.getLogger(ServiceLicenseInfoSource.class);

    @Autowired
    private LicenseCheckHandler licenseCheckHandler;

    @Autowired
    private DataCipherHandler dataCipherHandler;


    @Value("${lic.publicKey}")
    public String publicKey;

    @Value("${lic.md5salt}")
    public String md5salt;

    /**
     * 根据服务名获取服务授权信息（解密后）
     *
     * @param serviceId
     * @return
     * @throws LicenseException
     */
    public FileLicenseInfo getServiceLicInfo(long serviceId) throws LicenseException {
        String serviceCiphertext = licenseCheckHandler.getServiceCiphertext(serviceId,SecretKeyType.COMMON);
        FileLicenseInfo licenseInfo = servicLicDecrypt(serviceCiphertext);
        return licenseInfo;
    }


    public FileLicenseInfo checkSource(String ciphertext) throws LicenseException {
        FileLicenseInfo licenseInfo = servicLicDecrypt(ciphertext);

        /* 检查秘钥是否仍然存在或在是否被篡改 */
        if (licenseInfo == null) {
            throw new LicenseException("秘钥非法! ciphertext = "+ciphertext);
        }

        //指纹检验
        logger.info("授权指纹检验...");
        md5saltCheck(licenseInfo);
        logger.info("授权有效期检验...");
        verifyLimit(licenseInfo);
//        logger.info("授权业务检验...");
//        licenseCheck(licenseInfo);
        return licenseInfo;
    }


    public FileLicenseInfo servicLicDecrypt(String encodeData) throws LicenseException {
        FileLicenseInfo licenseInfo;
        try {
            logger.info("授权信息解密...");
            String decryptData = RSAUtils.decryptByPublicKey(encodeData, publicKey);

            licenseInfo = JSONObject.parseObject(decryptData, FileLicenseInfo.class);
            logger.debug("授权信息..." +licenseInfo);
        } catch (Exception e) {
            logger.error("授权解密失败！", e);
            throw new LicenseException("授权解密失败！");
        }
        return licenseInfo;
    }


    private void md5saltCheck(FileLicenseInfo licenseInfo) throws LicenseException {
        FileLicenseInfo fileLicenseInfo = new FileLicenseInfo();
        fileLicenseInfo.setId(licenseInfo.getId());
        fileLicenseInfo.setRangeTimeLower(licenseInfo.getRangeTimeLower());
        fileLicenseInfo.setDeployTimesUpper(licenseInfo.getDeployTimesUpper());
        fileLicenseInfo.setRangeTimeUpper(licenseInfo.getRangeTimeUpper());
        fileLicenseInfo.setServiceName(licenseInfo.getServiceName());
        fileLicenseInfo.setCompanyName(licenseInfo.getCompanyName());
        fileLicenseInfo.setServiceType(licenseInfo.getServiceType());

        if(StringUtils.isBlank(licenseInfo.getDeployTimesUpper())
                || StringUtils.isBlank(licenseInfo.getDeployTimesUpper())
                || StringUtils.isBlank(licenseInfo.getDeployTimesUpper())) {
            throw new LicenseException("授权信息缺失!");
        }

        String checkbit = MD5Util.MD5Encrypt(JSONObject.toJSONString(fileLicenseInfo), md5salt);
        if(!StringUtils.equals(licenseInfo.getCheckbit(), checkbit)) {
            logger.debug(" lic-checkbit:"+fileLicenseInfo.getCheckbit() + "   checkbit:"+checkbit);
            throw new LicenseException("授权指纹不匹配!");
        }
    }

    protected void verifyLimit(FileLicenseInfo licenseInfo) throws LicenseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date begin = null;
        Date end = null;
        try {
            begin = df.parse(licenseInfo.getRangeTimeLower());
            end = df.parse(licenseInfo.getRangeTimeUpper());
        } catch (ParseException e) {
            throw new LicenseException("授权时间格式不正确");
        }

        if(new Date().before(begin)) {
            throw new LicenseException("授权未到使用时间! 生效时间：" + licenseInfo.getRangeTimeLower());
        }

        if(end.before(new Date())) {
            throw new LicenseException("授权过期! 过期时间："+ licenseInfo.getRangeTimeUpper());
        }

    }

    /**
     * 检查授权使用并返回服务描述
     *
     * @param licenseInfo
     * @return
     * @throws LicenseException
     */
    public ServiceDetail licenseCheck(FileLicenseInfo licenseInfo,long serviceId) throws LicenseException {

        synchronized (licenseInfo) {
            String dataCiphertext = licenseCheckHandler.getDataCiphertext(serviceId);
            ServiceDetail serviceDetail = dataCipherHandler.getDateJsonByCiphertext(dataCiphertext);

            int deployUseTimes = Integer.parseInt(serviceDetail.getDeployUseTimes());

            if(deployUseTimes >= Integer.parseInt(licenseInfo.getDeployTimesUpper())) {
                throw new LicenseException("部署使用次数超过最大限制！");
            }

            deployUseTimes++;

            serviceDetail.setDeployUseTimes(deployUseTimes+"");
            return serviceDetail;
        }
    }






}
