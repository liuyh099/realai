package cn.realai.online.core.vo;

import cn.realai.online.core.entity.Service;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Description:
 * <br>
 * <br>Author:  Shunping.Fu
 * <br>Date: 2019/4/1
 */
@ApiModel(description = "发布详情")
public class ServiceDeployRecordVO {

    @ApiModelProperty(value = "模型名称")
    private String modelName;

    @ApiModelProperty(value = "创建时间")
    private Long createTime;

    @ApiModelProperty(value = "操作类型", example = "1:发布模型")
    private int opertionType;

    @ApiModelProperty(value = "操作人")
    private String userName;

    @ApiModelProperty(value = "备注")
    private String remark;

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public int getOpertionType() {
        return opertionType;
    }

    public void setOpertionType(int opertionType) {
        this.opertionType = opertionType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
