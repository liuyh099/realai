package cn.realai.online.core.bo;

import cn.realai.online.common.page.PageBO;
import cn.realai.online.core.vo.VariableDataVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "实验详情信息")
public class ExperimentalTrainDetailBO {

    //实验id
    @ApiModelProperty(value="实验id")
    private long id;

    //实验名称
    @ApiModelProperty(value="实验名称",example = "实验名称")
    private String name;

    //服务名称
    @ApiModelProperty(value="服务名称",example = "服务名称")
    private String serviceName ;

    //服务Id
    @ApiModelProperty(value="服务Id",example = "1")
    private String serviceId;

    //训练状态
    @ApiModelProperty(value="训练状态", example = "1")
    private int status;

    //实验状态
    @ApiModelProperty(value="实验状态",example = "1")
    private int releasStatus;

    //y表数据源
    @ApiModelProperty(value="y表数据源")
    private String ytableDataSource;

    //x表同质数据源
    @ApiModelProperty(value="x表同质数据源")
    private String xtableHomogeneousDataSource;

    //x表异质数据源
    @ApiModelProperty(value="x表异质数据源")
    private String xtableHeterogeneousDataSource;

    //是否选择验证集
    @ApiModelProperty(value="是否选择验证集",example = "1")
    private Integer verificationSet;

    //训练比例  trainRatio + trainTest + trainValid = 10
    @ApiModelProperty(value="训练比例")
    private Integer trainRatio;

    //测试比例
    @ApiModelProperty(value="测试比例")
    private Integer testRatio;

    //验证比例
    @ApiModelProperty(value="验证比例")
    private Integer validRatio;

    //创建时间
    @ApiModelProperty(value="创建时间",example = "1552621502000")
    private long createTime;

    //训练时间
    @ApiModelProperty(value="训练时间",example = "1552621502000")
    private long trainingTime;

    //发布时间
    @ApiModelProperty(value="发布时间",example = "1552621502000")
    private long releaseTime;

    //调优次数
    @ApiModelProperty(value="调优次数", example = "1")
    private int tuningCount;

    //备注
    @ApiModelProperty(value="备注",example = "1")
    private String remark;

    //（同质/异质）数据schema
    private PageBO<VariableDataBO> VariableDataList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getReleasStatus() {
        return releasStatus;
    }

    public void setReleasStatus(int releasStatus) {
        this.releasStatus = releasStatus;
    }

    public String getYtableDataSource() {
        return ytableDataSource;
    }

    public void setYtableDataSource(String ytableDataSource) {
        this.ytableDataSource = ytableDataSource;
    }

    public String getXtableHomogeneousDataSource() {
        return xtableHomogeneousDataSource;
    }

    public void setXtableHomogeneousDataSource(String xtableHomogeneousDataSource) {
        this.xtableHomogeneousDataSource = xtableHomogeneousDataSource;
    }

    public String getXtableHeterogeneousDataSource() {
        return xtableHeterogeneousDataSource;
    }

    public void setXtableHeterogeneousDataSource(String xtableHeterogeneousDataSource) {
        this.xtableHeterogeneousDataSource = xtableHeterogeneousDataSource;
    }

    public Integer getVerificationSet() {
        return verificationSet;
    }

    public void setVerificationSet(Integer verificationSet) {
        this.verificationSet = verificationSet;
    }

    public Integer getTrainRatio() {
        return trainRatio;
    }

    public void setTrainRatio(Integer trainRatio) {
        this.trainRatio = trainRatio;
    }

    public Integer getTestRatio() {
        return testRatio;
    }

    public void setTestRatio(Integer testRatio) {
        this.testRatio = testRatio;
    }

    public Integer getValidRatio() {
        return validRatio;
    }

    public void setValidRatio(Integer validRatio) {
        this.validRatio = validRatio;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getTrainingTime() {
        return trainingTime;
    }

    public void setTrainingTime(long trainingTime) {
        this.trainingTime = trainingTime;
    }

    public long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(long releaseTime) {
        this.releaseTime = releaseTime;
    }

    public int getTuningCount() {
        return tuningCount;
    }

    public void setTuningCount(int tuningCount) {
        this.tuningCount = tuningCount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public PageBO<VariableDataBO> getVariableDataList() {
        return VariableDataList;
    }

    public void setVariableDataList(PageBO<VariableDataBO> variableDataList) {
        VariableDataList = variableDataList;
    }
}
