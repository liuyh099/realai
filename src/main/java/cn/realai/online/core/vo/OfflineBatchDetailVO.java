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
    private long batchId;

    @ApiModelProperty(value = "模型名称")
    private String modelName;

    @ApiModelProperty(value = "服务名称")
    private String serviceName;

    @ApiModelProperty(value = "训练状态")
    private String trainStatusName;

    @ApiModelProperty(value = "跑批次数")
    private String batchNum;

    @ApiModelProperty(value = "模型Y表数据源")
    private String YDataSource;

    @ApiModelProperty(value = "模型X表同质数据源")
    private String XHomoDataSource;

    @ApiModelProperty(value = "模型X表异质数据源")
    private String XHeteroDataSource;

    @ApiModelProperty(value = "跑批时间")
    private String runTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    public long getBatchId() {
        return batchId;
    }

    public void setBatchId(long batchId) {
        this.batchId = batchId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getTrainStatusName() {
        return trainStatusName;
    }

    public void setTrainStatusName(String trainStatusName) {
        this.trainStatusName = trainStatusName;
    }

    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }

    public String getYDataSource() {
        return YDataSource;
    }

    public void setYDataSource(String YDataSource) {
        this.YDataSource = YDataSource;
    }

    public String getXHomoDataSource() {
        return XHomoDataSource;
    }

    public void setXHomoDataSource(String XHomoDataSource) {
        this.XHomoDataSource = XHomoDataSource;
    }

    public String getXHeteroDataSource() {
        return XHeteroDataSource;
    }

    public void setXHeteroDataSource(String XHeteroDataSource) {
        this.XHeteroDataSource = XHeteroDataSource;
    }

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
