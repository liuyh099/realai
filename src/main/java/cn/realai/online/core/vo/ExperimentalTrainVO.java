package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ExperimentalTrainVO {

    @ApiModelProperty(value = "实验id", example = "1")
    private long id = 1l;

    @ApiModelProperty(value = "实验名称", example = "实验名称")
    private String name;

    @ApiModelProperty(value = "服务名称", example = "服务名称")
    private String serviceName;

    @ApiModelProperty(value = "服务Id", example = "1")
    private String serviceId;

    @ApiModelProperty(value = "训练状态", example = "1")
    private int status;

    @ApiModelProperty(value = "实验状态", example = "1")
    private int releasStatus;

    @ApiModelProperty(value = "创建时间", example = "1552621502000")
    private long createTime;

    @ApiModelProperty(value = "训练时间", example = "1552621502000")
    private long trainingTime;

    @ApiModelProperty(value = "发布时间", example = "1552621502000")
    private long releaseTime;

    @ApiModelProperty(value = "调优次数", example = "1")
    private int tuningCount;

    @ApiModelProperty(value = "备注", example = "1")
    private String remark;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getReleasStatus() {
        return releasStatus;
    }

    public void setReleasStatus(int releasStatus) {
        this.releasStatus = releasStatus;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getTrainingTime() {
        return trainingTime;
    }

    public void setTrainingTime(long trainingTime) {
        this.trainingTime = trainingTime;
    }

    public long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(long releaseTime) {
        this.releaseTime = releaseTime;
    }

    public int getTuningCount() {
        return tuningCount;
    }

    public void setTuningCount(int tuningCount) {
        this.tuningCount = tuningCount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
