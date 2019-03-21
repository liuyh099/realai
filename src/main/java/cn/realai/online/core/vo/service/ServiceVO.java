package cn.realai.online.core.vo.service;

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

    @ApiModelProperty(value = "秘钥起始时间")
    private long startTime;

    @ApiModelProperty(value = "秘钥到期时间")
    private long expireDate;

    @ApiModelProperty(value = "发布次数")
    private int releaseCount;

    @ApiModelProperty(value = "创建时间")
    private long createTime;

    @ApiModelProperty(value = "服务类型", example = "1:风控 2:营销")
    private int type;

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
