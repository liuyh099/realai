package cn.realai.online.core.bo;

/**
 * 功能描述：TODO
 *
 * @author admin
 * @create 2019-03-18 14:28
 */
public class BatchDetailBO {

    private Long batchId;
    private Long  serviceId;
    private String serviceName;
    private Long experimentId;
    private Integer status;
    private Integer offlineTimes;
    private String xtabaleHeter;
    private String xtableHomo;
    private String ytable;
    private Long createTime;
    private String remark;

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Long getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(Long experimentId) {
        this.experimentId = experimentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOfflineTimes() {
        return offlineTimes;
    }

    public void setOfflineTimes(Integer offlineTimes) {
        this.offlineTimes = offlineTimes;
    }

    public String getXtabaleHeter() {
        return xtabaleHeter;
    }

    public void setXtabaleHeter(String xtabaleHeter) {
        this.xtabaleHeter = xtabaleHeter;
    }

    public String getXtableHomo() {
        return xtableHomo;
    }

    public void setXtableHomo(String xtableHomo) {
        this.xtableHomo = xtableHomo;
    }

    public String getYtable() {
        return ytable;
    }

    public void setYtable(String ytable) {
        this.ytable = ytable;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
