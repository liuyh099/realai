package cn.realai.online.lic;

/**
 * Description: 服务扩展字段信息
 *     加密入库，记录服务调优次数及对应的废弃秘钥ID
 * <br>
 * <br>Author:  Shunping.Fu
 * <br>Date: 2019/3/15
 */

public class ServiceDetail {

    private String serviceName;  //服务名称

    private String deployUseTimes;    //调优部署累计已使用次数

    private int version;   //当前版本

    private String tuningKeyIds;   //废弃秘钥ID集合

    private int status = STATUS_NORMAL;

    public static final Integer STATUS_STOP = 0;   //停用废弃

    public static final Integer STATUS_NORMAL = 1;   //正常

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getTuningKeyIds() {
        return tuningKeyIds;
    }

    public void setTuningKeyIds(String tuningKeyIds) {
        this.tuningKeyIds = tuningKeyIds;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDeployUseTimes() {
        return deployUseTimes;
    }

    public void setDeployUseTimes(String deployUseTimes) {
        this.deployUseTimes = deployUseTimes;
    }
}
