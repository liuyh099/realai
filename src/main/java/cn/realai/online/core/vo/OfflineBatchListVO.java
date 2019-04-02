package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 功能描述：TODO
 *
 * @author admin
 * @create 2019-03-18 14:28
 */
@ApiModel
public class OfflineBatchListVO {

    @ApiModelProperty(value="离线跑批id")
    private long batchId;

    @ApiModelProperty(value="模型ID")
    private Long modelId;

    @ApiModelProperty(value="模型名称")
    private String modelName;

    @ApiModelProperty(value="实验ID")
    private Long experimentId;

    @ApiModelProperty(value="实验名称")
    private String experimentName;

    @ApiModelProperty(value="发布时间")
    private Long releaseTime;

    @ApiModelProperty(value="服务Id")
    private Long serviceId;

    @ApiModelProperty(value="服务名称")
    private String serviceName;

    @ApiModelProperty(value="跑批次数")
    private Integer batchTimes;

    @ApiModelProperty(value="跑批状态 1:新建 2：处理中 3：处理完成 4：处理有误")
    private Integer status;

    @ApiModelProperty(value="离线跑批时间")
    private Long batchCreateTime;

    @ApiModelProperty(value="下载结果")
    private String downUrl;

    @ApiModelProperty(value="备注")
    private String remark;

    public long getBatchId() {
        return batchId;
    }

    public void setBatchId(long batchId) {
        this.batchId = batchId;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Long getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(Long experimentId) {
        this.experimentId = experimentId;
    }

    public String getExperimentName() {
        return experimentName;
    }

    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }

    public Long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Long releaseTime) {
        this.releaseTime = releaseTime;
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

    public Integer getBatchTimes() {
        return batchTimes;
    }

    public void setBatchTimes(Integer batchTimes) {
        this.batchTimes = batchTimes;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getBatchCreateTime() {
        return batchCreateTime;
    }

    public void setBatchCreateTime(Long batchCreateTime) {
        this.batchCreateTime = batchCreateTime;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
