package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel
public class ExperimentalTrainCreateModelVO {

    @ApiModelProperty(value = "id",name = "实验id",dataType = "long")
    private long id;

    @ApiModelProperty(value = "ids",name = "选择的id集合",dataType = "list")
    private List<Long> ids;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

}
