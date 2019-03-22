package cn.realai.online.core.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel
public class FaceListDataQuery extends PageQuery {

    @ApiModelProperty(value = "实验id")
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "分组")
    private Long groupId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "ID")
    private String personalId;

    @ApiModelProperty(value = "进件开始日期(时间戳毫秒)")
    private Long inputStartDate;

    @ApiModelProperty(value = "进件结束日期(时间戳毫秒)")
    private Long inputStartEnd;

    @ApiModelProperty(value = "批次Id")
    private Long batchId;

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public Long getInputStartDate() {
        return inputStartDate;
    }

    public void setInputStartDate(Long inputStartDate) {
        this.inputStartDate = inputStartDate;
    }

    public Long getInputStartEnd() {
        return inputStartEnd;
    }

    public void setInputStartEnd(Long inputStartEnd) {
        this.inputStartEnd = inputStartEnd;
    }
}
