package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel
public class ExperimentalTrainCreateModelVO {

    @ApiModelProperty(value = "id",name = "实验id",dataType = "long")
    @NotNull(message = "请选择实验")
    private Long id;

    @ApiModelProperty(value = "ids",name = "选择的id集合",dataType = "list")
    @NotNull(message = "请选择要删除的数据")
    private List<Long> ids;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
