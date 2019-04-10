package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class PersonalInformationVO {

    //数据id
    @ApiModelProperty(value = "id")
    private long id;

    @ApiModelProperty(value = "组名")
    private String groupName;

    @ApiModelProperty(value = "批次")
    private String batch;

    //用户姓名
    @ApiModelProperty(value = "用户姓名")
    private String personalName;

    //身份证号
    @ApiModelProperty(value = "身份证号")
    private String personalCardId;

    //用户id 用户在行方的id（无需处理）
    @ApiModelProperty(value = "Id")
    private String personalId;


    //手机号
    @ApiModelProperty(value = "手机号")
    private String phoneNum;

    //创建时间
    @ApiModelProperty(value = "创建时间")
    private long createTime;

    @ApiModelProperty(value = "订单日期")
    private String orderDate;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getPersonalName() {
        return personalName;
    }

    public void setPersonalName(String personalName) {
        this.personalName = personalName;
    }

    public String getPersonalCardId() {
        return personalCardId;
    }

    public void setPersonalCardId(String personalCardId) {
        this.personalCardId = personalCardId;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
