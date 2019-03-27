package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@ApiModel
public class ExperimentalPublishVO {

    @ApiModelProperty(value = "模型名称")
    @NotBlank(message = "请输入模型名称")
    @Length(max = 20, message = "模型名称最多输入20个字节")
    private String name;

    @ApiModelProperty(value = "实验ID")
    @NotNull(message = "请选择实验")
    private Long experimentId;

    @ApiModelProperty(value = "备注")
    @Length(max = 200, message = "备注最大200个字节")
    private String remark;

    @ApiModelProperty(value = "发布方式 2:离线发布 3:在线发布")
    @NotNull(message = "请选择发布方式")
    //@Pattern(regexp = "/^[23]$/", message = "请选择正确的发布方式")
    private Integer status;


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(Long experimentId) {
        this.experimentId = experimentId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
