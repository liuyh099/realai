package cn.realai.online.core.vo.service;

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

    @ApiModelProperty(value = "服务类型",example = "1:风控 2:营销")
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
