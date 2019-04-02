package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel
public class ExperimentalTrainDelRecommendVO {

    @ApiModelProperty(value = "id", name = "实验id", dataType = "long")
    @NotNull(message = "请选择实验")
    private Long id;

    @ApiModelProperty(value = "ids", name = "需要从推荐删除数据中排除的数据", dataType = "list")
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
