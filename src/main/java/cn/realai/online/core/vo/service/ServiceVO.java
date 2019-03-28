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
@ApiModel(description = "服务信息")
public class ServiceVO {

    @ApiModelProperty(value = "服务ID")
    private long id;

    @ApiModelProperty(value = "秘钥起始时间")
    private long startTime;

    @ApiModelProperty(value = "秘钥到期时间")
    private long expireDate;

    @ApiModelProperty(value = "发布次数")
    private int releaseCount;

    @ApiModelProperty(value = "创建时间")
    private long createTime;

    @ApiModelProperty(value = "服务类型", example = Service.TYPE_EXPLAIN)
    private int type;

    @ApiModelProperty(value = "服务子类型", example = Service.BUSINESSTYPE_EXPLAIN)
    private int businessType;


    @ApiModelProperty(value = "是否可续期", example = "true: 可续期 false: 不可续期")
    private boolean renewable;

    @ApiModelProperty(value = "发布次数上限")
    private int deployTimesUpper;

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDeployTimesUpper() {
        return deployTimesUpper;
    }

    public void setDeployTimesUpper(int deployTimesUpper) {
        this.deployTimesUpper = deployTimesUpper;
    }

    public boolean isRenewable() {
        return renewable;
    }

    public void setRenewable(boolean renewable) {
        this.renewable = renewable;
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

    public int getReleaseCount() {
        return releaseCount;
    }

    public void setReleaseCount(int releaseCount) {
        this.releaseCount = releaseCount;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
