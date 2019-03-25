package cn.realai.online.core.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ExperimentalTrainQuery extends PageQuery {

    //训练名称
    @ApiModelProperty(value = "实验名称", dataType = "String", name = "name")
    private String name;

    @ApiModelProperty(value = "训练状态", dataType = "int", name = "status", notes = "1:选择文件 2:选择参数 3:变量筛选 4:试验训练 5:试验完毕", example = "1:选择文件 2:选择参数 3:变量筛选 4:试验训练 5:试验完毕")
    private Integer status;

    @ApiModelProperty(value = "发布状态", dataType = "int", name = "releasStatus", notes = "1:已发布 2：未发布", example = "1:未发布 2：离线发布 3:线上发布")
    private Integer releasStatus;

    @ApiModelProperty(value = "服务ID")
    private Long serviceId;

    @ApiModelProperty(value = "调优类型 1:代表PSI调优 2:强制调优" )
    private Integer tuningType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getReleasStatus() {
        return releasStatus;
    }

    public void setReleasStatus(Integer releasStatus) {
        this.releasStatus = releasStatus;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getTuningType() {
        return tuningType;
    }

    public void setTuningType(Integer tuningType) {
        this.tuningType = tuningType;
    }
}
