package cn.realai.online.core.query.service;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Description: 服务续期
 * <br>
 * <br>Author:  Shunping.Fu
 * <br>Date: 2019/3/22
 */
@ApiModel(description = "服务续期")
public class ApplyServiceQuery {

    @ApiModelProperty(value = "服务ID")
    private long id;

    @ApiModelProperty(value = "服务秘钥")
    private String secretKey;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

}
