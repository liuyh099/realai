package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel
public class ExperimentalUpdateNameAndRemarkVo {

    @ApiModelProperty(value = "实验ID")
    @NotNull(message = "请选择实验")
    private Long id;

    @ApiModelProperty(value = "实验名称")
    @NotBlank(message = "实验名称不能为空")
    private String name;

    @ApiModelProperty(value = "实验备注")
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
