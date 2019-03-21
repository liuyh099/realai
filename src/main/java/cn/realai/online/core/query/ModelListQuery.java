package cn.realai.online.core.query;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 功能描述：TODO
 *
 * @author admin
 * @create 2019-03-18 14:19
 */
@ApiModel
public class ModelListQuery extends PageQuery {

    @ApiModelProperty(value = "模型名称")
    private String name;

    @ApiModelProperty(value = "服务Id")
    private Long serviceId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
}
