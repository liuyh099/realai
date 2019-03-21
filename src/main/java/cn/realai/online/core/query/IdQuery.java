package cn.realai.online.core.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel
public class IdQuery extends PageQuery {
    @ApiModelProperty(value = "id")
    @NotNull(message = "请先选择组")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
