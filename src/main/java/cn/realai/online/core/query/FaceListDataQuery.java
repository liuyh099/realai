package cn.realai.online.core.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class FaceListDataQuery extends PageQuery {

    @ApiModelProperty(value="实验id")
    private long trainId;

    @ApiModelProperty(value="分组")
    private long groupId;

    @ApiModelProperty(value="姓名")
    private String name;

    @ApiModelProperty(value="身份证号")
    private String idCard;

    @ApiModelProperty(value="手机号")
    private String phone;

    @ApiModelProperty(value="ID")
    private long id;

    @ApiModelProperty(value="进件开始日期(时间戳毫秒)")
    private long inputStartDate;

    @ApiModelProperty(value="进件结束日期(时间戳毫秒)")
    private long inputStartEnd;

    public long getTrainId() {
        return trainId;
    }

    public void setTrainId(long trainId) {
        this.trainId = trainId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getInputStartDate() {
        return inputStartDate;
    }

    public void setInputStartDate(long inputStartDate) {
        this.inputStartDate = inputStartDate;
    }

    public long getInputStartEnd() {
        return inputStartEnd;
    }

    public void setInputStartEnd(long inputStartEnd) {
        this.inputStartEnd = inputStartEnd;
    }
}
