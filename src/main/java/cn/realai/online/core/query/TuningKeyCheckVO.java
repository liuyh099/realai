package cn.realai.online.core.query;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;

/**
 * 功能描述：TODO
 *
 * @author admin
 * @create 2019-04-11 18:31
 */

@ApiModel
public class TuningKeyCheckVO {

    @ApiModelProperty(name = "调优秘钥Key")
    @NotNull(message = "密钥不能为空")
    private String tuningKey;

    @ApiModelProperty(name = "服务ID")
    @NotNull(message = "服务ID不能为空")
    private Long serviceId;

    public String getTuningKey() {
        return tuningKey;
    }

    public void setTuningKey(String tuningKey) {
        this.tuningKey = tuningKey;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
}
