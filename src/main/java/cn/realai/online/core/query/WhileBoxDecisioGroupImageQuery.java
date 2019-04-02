package cn.realai.online.core.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel
public class WhileBoxDecisioGroupImageQuery {

    @ApiModelProperty(value="模型ID",required = true)
    @NotNull
    private Long modelId;

    @ApiModelProperty(value="分组1", required = true)
    @NotNull
    private String groupName1;

    @ApiModelProperty(value="分组2", required = true)
    @NotNull
    private String groupName2;

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public String getGroupName1() {
        return groupName1;
    }

    public void setGroupName1(String groupName1) {
        this.groupName1 = groupName1;
    }

    public String getGroupName2() {
        return groupName2;
    }

    public void setGroupName2(String groupName2) {
        this.groupName2 = groupName2;
    }
}
