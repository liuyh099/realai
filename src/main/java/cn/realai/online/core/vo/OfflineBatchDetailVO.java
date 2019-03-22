package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 功能描述：TODO
 *
 * @author admin
 * @create 2019-03-18 14:28
 */
@ApiModel
public class OfflineBatchDetailVO {
    @ApiModelProperty(value = "跑批ID")
    private Long batchId;
    @ApiModelProperty(value="服务ID")
    private Long  serviceId;
    @ApiModelProperty(value="服务名称")
    private String serviceName;
    @ApiModelProperty(value="实验ID")
    private Long experimentId;
    @ApiModelProperty(value="实验状态")
    private Integer status;
    @ApiModelProperty(value="离线跑批次数")
    private Integer offlineTimes;
    @ApiModelProperty(value="X表异质数据源")
    private String xtabaleHeter;
    @ApiModelProperty(value="X表同质数据源")
    private String xtableHomo;
    @ApiModelProperty(value="Y表数据源")
    private String ytable;
    @ApiModelProperty(value="创建时间")
    private Long createTime;
    @ApiModelProperty(value="备注")
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
