package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("最近发布模型名选项")
public class ModelSelectVO<T> {
    @ApiModelProperty(value = "最近发布的服务ID")
    private Long serviceId;
    @ApiModelProperty(value = "最近发布的服务名称")
    private String serviceName;
    @ApiModelProperty(value = "最近发布的模型ID")
    private Long modelId;
    @ApiModelProperty(value = "最近发布的模型名称")
    private String modelName;
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

}

