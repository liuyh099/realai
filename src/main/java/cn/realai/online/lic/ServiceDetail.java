package cn.realai.online.lic;

/**
 * Description:
 * <br>
 * <br>Author:  Shunping.Fu
 * <br>Date: 2019/3/15
 */

public class ServiceDetail {

    private String serviceName;

    private String deployUseTimes;

    private int version;

    private String tuningKeyIds;

    private int status = STATUS_NORMAL;

    public static final Integer STATUS_STOP = 0;

    public static final Integer STATUS_NORMAL = 1;

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
