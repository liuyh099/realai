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
public class OfflineBatchListQuery extends PageQuery {

    @ApiModelProperty(value="模型名称")
    private String name;

    @ApiModelProperty(value="跑批状态跑批状态 1:新建 2：处理中 3：处理完成 4：处理有误")
    private Integer status;

    @ApiModelProperty(value="最小跑批时间，格式：yyyy-MM-dd")
    private String minDate;

    @ApiModelProperty(value="最大跑批时间，格式：yyyy-MM-dd")
    private String maxDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMinDate() {
        return minDate;
    }

    public void setMinDate(String minDate) {
        this.minDate = minDate;
    }

    public String getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(String maxDate) {
        this.maxDate = maxDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
