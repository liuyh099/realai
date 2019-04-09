package cn.realai.online.core.query.service;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Description: 编辑服务
 * <br>
 * <br>Author:  Shunping.Fu
 * <br>Date: 2019/3/20
 */
@ApiModel(description = "编辑服务")
public class EditServiceQuery {

    @ApiModelProperty(value = "服务ID")
    private long serviceId;

    @ApiModelProperty(value = "服务名称")
    private String name;

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
