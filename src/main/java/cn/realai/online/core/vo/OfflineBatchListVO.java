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

    @ApiModelProperty(value="模型名称")
    private String modeleName;

    @ApiModelProperty(value="服务名称")
    private String serviceName;

    @ApiModelProperty(value="发布时间")
    private String releaseTime;

    @ApiModelProperty(value="跑批次数")
    private String runBatchNum;

    @ApiModelProperty(value="跑批时间")
    private String runBatchTime;

    @ApiModelProperty(value="训练状态")
    private String trainStatusName;

    @ApiModelProperty(value="离线跑批结果下载地址")
    private String downUrl;

    @ApiModelProperty(value="备注")
    private String remark;

    public long getBatchId() {
        return batchId;
    }

    public void setBatchId(long batchId) {
        this.batchId = batchId;
    }

    public String getModeleName() {
        return modeleName;
    }

    public void setModeleName(String modeleName) {
        this.modeleName = modeleName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getRunBatchNum() {
        return runBatchNum;
    }

    public void setRunBatchNum(String runBatchNum) {
        this.runBatchNum = runBatchNum;
    }

    public String getRunBatchTime() {
        return runBatchTime;
    }

    public void setRunBatchTime(String runBatchTime) {
        this.runBatchTime = runBatchTime;
    }

    public String getTrainStatusName() {
        return trainStatusName;
    }

    public void setTrainStatusName(String trainStatusName) {
        this.trainStatusName = trainStatusName;
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
