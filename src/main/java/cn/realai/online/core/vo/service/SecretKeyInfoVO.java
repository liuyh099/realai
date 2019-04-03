package cn.realai.online.core.vo.service;

import cn.realai.online.core.entity.Service;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Description:
 * <br>
 * <br>Author:  Shunping.Fu
 * <br>Date: 2019/3/20
 */
@ApiModel(description = "秘钥信息")
public class SecretKeyInfoVO {


    @ApiModelProperty(value = "秘钥起始时间")
    private long startTime;

    @ApiModelProperty(value = "秘钥到期时间")
    private long expireDate;

    @ApiModelProperty(value = "服务类型", example = Service.TYPE_EXPLAIN)
    private Integer type;

    @ApiModelProperty(value = "服务子类型", example = Service.BUSINESSTYPE_EXPLAIN)
    private Integer businessType;

    @ApiModelProperty(value = "服务类型名称")
    private String serviceTypeName;

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(long expireDate) {
        this.expireDate = expireDate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public String getServiceTypeName() {
        return serviceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }
}
