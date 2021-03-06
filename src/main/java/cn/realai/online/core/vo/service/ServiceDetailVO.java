package cn.realai.online.core.vo.service;

import cn.realai.online.core.entity.Service;
import cn.realai.online.core.vo.ServiceDeployRecordVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Description:
 * <br>
 * <br>Author:  Shunping.Fu
 * <br>Date: 2019/3/20
 */
@ApiModel(description = "服务详情")
public class ServiceDetailVO {

    @ApiModelProperty(value = "服务ID")
    private Long id;

    @ApiModelProperty(value = "秘钥")
    private String secretKey;

    @ApiModelProperty(value = "服务名称")
    private String name;

    @ApiModelProperty(value = "秘钥起始时间")
    private Long startTime;

    @ApiModelProperty(value = "秘钥到期时间")
    private Long expireDate;

    @ApiModelProperty(value = "发布次数")
    private Integer releaseCount;

    @ApiModelProperty(value = "创建时间")
    private Long createTime;

    @ApiModelProperty(value = "服务类型", example = Service.TYPE_EXPLAIN)
    private Integer type;

    @ApiModelProperty(value = "服务子类型", example = Service.BUSINESSTYPE_EXPLAIN)
    private Integer businessType;

    @ApiModelProperty(value = "服务类型名称，服务类型和服务子类型组合")
    private String serviceTypeName;


    @ApiModelProperty(value = "是否可续期", example = "true: 可续期 false: 不可续期")
    private boolean renewable;

    @ApiModelProperty(value = "发布次数上限")
    private Integer deployTimesUpper;

    @ApiModelProperty(value = "发布详情")
    private List<ServiceDeployRecordVO> serviceDeployRecordVOs;

    public List<ServiceDeployRecordVO> getServiceDeployRecordVOs() {
        return serviceDeployRecordVOs;
    }

    public void setServiceDeployRecordVOs(List<ServiceDeployRecordVO> serviceDeployRecordVOs) {
        this.serviceDeployRecordVOs = serviceDeployRecordVOs;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Long expireDate) {
        this.expireDate = expireDate;
    }

    public Integer getReleaseCount() {
        return releaseCount;
    }

    public void setReleaseCount(Integer releaseCount) {
        this.releaseCount = releaseCount;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public String getServiceTypeName() {
        return serviceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }

    public boolean isRenewable() {
        return renewable;
    }

    public void setRenewable(boolean renewable) {
        this.renewable = renewable;
    }

    public Integer getDeployTimesUpper() {
        return deployTimesUpper;
    }

    public void setDeployTimesUpper(Integer deployTimesUpper) {
        this.deployTimesUpper = deployTimesUpper;
    }
}
