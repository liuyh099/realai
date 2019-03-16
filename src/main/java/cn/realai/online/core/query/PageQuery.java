package cn.realai.online.core.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class PageQuery  {

    @ApiModelProperty(value = "当前页数" ,name="pageNum")
    private Integer pageNum;

    @ApiModelProperty(value = "页面显示数据",name = "pageSize")
    private Integer pageSize;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
