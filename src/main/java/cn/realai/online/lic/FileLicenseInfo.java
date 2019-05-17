package cn.realai.online.lic;

import org.springframework.util.StringUtils;

/**
 * 
 * <p>Title: 文件授权信息类        </p>
 */
public class FileLicenseInfo {

    private String id;
    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 服务类型
     */
    private String serviceType;

    /**
     * 服务子类型 1：A卡 2：B卡 3：C卡 4：营销
     */
    private String businessType;

    /**
     * 秘钥类型
     */
    private String secretKeyType;

    /**
     * 服务授权私钥
     */
    private String privateKey;
    /**
     * 服务授权公钥
     */
    private String publicKey;

    /**
     * 服务失效时间
     */
    private String rangeTimeUpper;

    /**
     * 服务起效时间
     */
    private String rangeTimeLower;
    /**
     * 强制调优次数
     */
    private String deployTimesUpper;

    /**
     * 数据加密私钥
     */
    private String secretPrivateKey;

    /**
     * 数据解密公钥
     */
    private String secretPublicKey;

    /**
     * 校验位
     */
    private String checkbit;

    /**
     * 秘钥过期时间
     */
    private long overdue;

    /**
     * 作废秘钥
     */
    private String cancelSecretKey;

    /**
     * 停用秘钥
     */
    private String stopSecretKey;


    public String getStopSecretKey() {
        return stopSecretKey;
    }

    public void setStopSecretKey(String stopSecretKey) {
        this.stopSecretKey = stopSecretKey;
    }

    public String getCancelSecretKey() {
        return cancelSecretKey;
    }

    public void setCancelSecretKey(String cancelSecretKey) {
        this.cancelSecretKey = cancelSecretKey;
    }

    public long getOverdue() {
        return overdue;
    }

    public void setOverdue(long overdue) {
        this.overdue = overdue;
    }


    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getSecretKeyType() {
        return secretKeyType;
    }

    public void setSecretKeyType(String secretKeyType) {
        this.secretKeyType = secretKeyType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getRangeTimeUpper() {
        return rangeTimeUpper;
    }

    public void setRangeTimeUpper(String rangeTimeUpper) {
        this.rangeTimeUpper = rangeTimeUpper;
    }

    public String getRangeTimeLower() {
        return rangeTimeLower;
    }

    public void setRangeTimeLower(String rangeTimeLower) {
        this.rangeTimeLower = rangeTimeLower;
    }

    public String getDeployTimesUpper() {
        return deployTimesUpper;
    }

    public void setDeployTimesUpper(String deployTimesUpper) {
        this.deployTimesUpper = deployTimesUpper;
    }

    public String getSecretPrivateKey() {
        return secretPrivateKey;
    }

    public void setSecretPrivateKey(String secretPrivateKey) {
        this.secretPrivateKey = secretPrivateKey;
    }

    public String getSecretPublicKey() {
        return secretPublicKey;
    }

    public void setSecretPublicKey(String secretPublicKey) {
        this.secretPublicKey = secretPublicKey;
    }

    public String getCheckbit() {
        return checkbit;
    }

    public void setCheckbit(String checkbit) {
        this.checkbit = checkbit;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileLicenseInfo licenseInfo = (FileLicenseInfo) o;

        if(!StringUtils.isEmpty(serviceName) && serviceName.equals(licenseInfo.getServiceName())) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        int result = serviceName != null ? serviceName.hashCode() : 0;
        return result;
    }

}
