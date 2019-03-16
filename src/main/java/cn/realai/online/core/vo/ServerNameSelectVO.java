package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("服务名选项")
public class ServerNameSelectVO {

    @ApiModelProperty(value = "服务ID",example = "1")
    private long id;
    @ApiModelProperty(value = "服务名称",example = "小微-A卡")
    private String name;
    @ApiModelProperty(value = "服务类型",example = "1:风控 2:营销")
    private String serverType;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }
}
