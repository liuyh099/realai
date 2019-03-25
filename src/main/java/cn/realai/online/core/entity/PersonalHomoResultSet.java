package cn.realai.online.core.entity;

/**
 * 千人千面同质数据（热力图数据来源）
 *
 * @author lyh
 */
public class PersonalHomoResultSet {

    private Long id;

    private Long experimentId;

    //批次id
    private Long batchId;

    //用户消费流水序号（第几条流水）(x轴)
    private Integer k;

    //实验样本人员信息id 主键id
    private Long pid;

    //python计算返回 实验样本人员信息id（千人千面人员信息id）
    private String personalId;

    //变量名(Y轴)
    private Long variableId;

    //值
    private String value;

    private String variableName;
    
    //权重(显示值)
    private double weight;

    //python返回的批次标记字段，只做解析不需要入库
    private String batchStr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(Long experimentId) {
        this.experimentId = experimentId;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public Integer getK() {
        return k;
    }

    public void setK(Integer k) {
        this.k = k;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public Long getVariableId() {
        return variableId;
    }

    public void setVariableId(Long variableId) {
        this.variableId = variableId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getBatchStr() {
        return batchStr;
    }

    public void setBatchStr(String batchStr) {
        this.batchStr = batchStr;
    }

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
    
}
