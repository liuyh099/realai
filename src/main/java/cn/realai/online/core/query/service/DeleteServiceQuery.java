package cn.realai.online.core.query.service;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Description: 删除服务
 * <br>
 * <br>Author:  Shunping.Fu
 * <br>Date: 2019/4/1
 */
@ApiModel(description = "删除服务")
public class DeleteServiceQuery {

    @ApiModelProperty(value = "服务ID")
    private List<Long> serviceIds;

    public List<Long> getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(List<Long> serviceIds) {
        this.serviceIds = serviceIds;
    }
}
