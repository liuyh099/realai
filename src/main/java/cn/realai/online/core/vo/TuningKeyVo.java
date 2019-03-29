package cn.realai.online.core.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;

@ApiModel
public class TuningKeyVo {

    @ApiModelProperty(value = "调优模型ID")
    @NotNull(message="模型ID不能为空")
    private Long modelId;
    @NotNull(message="密钥串不能为空")
    @ApiModelProperty(value = "调优密钥串")
    private String pkstr;

    public @NotNull Long getModelId() {
        return modelId;
    }

    public void setModelId(@NotNull Long modelId) {
        this.modelId = modelId;
    }

    public @NotNull String getPkstr() {
        return pkstr;
    }

    public void setPkstr(@NotNull String pkstr) {
        this.pkstr = pkstr;
    }
}
