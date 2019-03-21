package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ExperimentalTrainCreateModelCheckVO {

    @ApiModelProperty(value = "name", name = "模型名称", dataType = "String")
    private String name;
    @ApiModelProperty(value = "createModelFlag", name = "是否可以一键建模", dataType = "boolean")
    private boolean createModelFlag;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCreateModelFlag() {
        return createModelFlag;
    }

    public void setCreateModelFlag(boolean createModelFlag) {
        this.createModelFlag = createModelFlag;
    }
}
