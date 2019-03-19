package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("模型名选项")
public class ModelSelectVO {

    @ApiModelProperty(value = "模型ID")
    private long modelId;
    @ApiModelProperty(value = "模型名称")
    private String modelName;
    @ApiModelProperty(value = "服务Id")
    private String serviceId;
    @ApiModelProperty(value = "服务名称")
    private String serviceName;
    @ApiModelProperty(value = "服务关联的模型")
    private List<ModelNameSelectVO> modelList;

    public long getModelId() {
        return modelId;
    }

    public void setModelId(long modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public List<ModelNameSelectVO> getModelList() {
        return modelList;
    }

    public void setModelList(List<ModelNameSelectVO> modelList) {
        this.modelList = modelList;
    }
}

