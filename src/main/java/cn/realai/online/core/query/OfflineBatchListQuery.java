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

    /*@ApiModelProperty(value="训练状态", example = "//选择文件= 1; //选择参数= 2; //变量筛选= 3; //试验训练中= 4; //试验完毕= 5")
    private Integer trainStatus;*/

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
}
