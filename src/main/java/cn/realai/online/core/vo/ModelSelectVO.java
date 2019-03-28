package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("模型名选项")
public class ModelSelectVO<T> {
    @ApiModelProperty(value = "传入或者最近发布的服务ID")
    private Long serviceId;
    @ApiModelProperty(value = "传入或者最近发布的服务名称")
    private String serviceName;
    @ApiModelProperty(value = "传入或者最近发布的模型ID")
    private Long modelId;
    @ApiModelProperty(value = "传入或者最近发布的模型名称")
    private String modelName;
    @ApiModelProperty(value = "服务发布的所有模型", dataType="List")
    private List<T> modelList;

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

    public List<T> getModelList() {
        return modelList;
    }

    public void setModelList(List<T> modelList) {
        this.modelList = modelList;
    }
}

