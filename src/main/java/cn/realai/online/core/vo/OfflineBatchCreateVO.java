package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;

/**
 * 功能描述：TODO
 *
 * @author admin
 * @create 2019-03-18 14:28
 */
@ApiModel
public class OfflineBatchCreateVO {

    @ApiModelProperty(value="服务ID")
    @NotNull(message = "请选择服务")
    private Long serviceId;

    @ApiModelProperty(value="模型X表同质数据源")
    @NotNull(message = "请选择模型X表同质数据源")
    private String xHomoDataSource;

    @ApiModelProperty(value="模型X表异质数据源")
    @NotNull(message = "请选择模型X表异质数据源")
    private String xHeteroDataSource;

    @ApiModelProperty(value="模型Y表数据源")
    @NotNull(message = "请选择模型Y表数据源")
    private String yDataSource;

    @ApiModelProperty(value="备注")
    private String remark;

    public @NotNull Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(@NotNull Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getxHomoDataSource() {
        return xHomoDataSource;
    }

    public void setxHomoDataSource(String xHomoDataSource) {
        this.xHomoDataSource = xHomoDataSource;
    }

    public String getxHeteroDataSource() {
        return xHeteroDataSource;
    }

    public void setxHeteroDataSource(String xHeteroDataSource) {
        this.xHeteroDataSource = xHeteroDataSource;
    }

    public String getyDataSource() {
        return yDataSource;
    }

    public void setyDataSource(String yDataSource) {
        this.yDataSource = yDataSource;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
