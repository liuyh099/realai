package cn.realai.online.core.query.service;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Description:
 * <br>
 * <br>Author:  Shunping.Fu
 * <br>Date: 2019/3/22
 */
@ApiModel(description = "获取秘钥信息")
public class GetSecretKeyInfoQuery {

    @ApiModelProperty(value = "服务秘钥")
    private String serviceKey;

    public String getServiceKey() {
        return serviceKey;
    }

    public void setServiceKey(String serviceKey) {
        this.serviceKey = serviceKey;
    }
}
