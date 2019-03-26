package cn.realai.online.core.entity;

/**
 * 千人千面异质数据
 *
 * @author lyh
 */
public class PersonalHetroResultSet {

    private Long id;

    //批次id
    private Long batchId;

    //实验样本人员信息id 主键id
    private Long pid;

    //python计算返回 实验样本人员信息id（千人千面人员信息id）
    private String personalId;

    //实验id
    private Long experimentId;

    private String variableName;
    
    //变量id
    private Long variableId;

    //值
    private String value;

    //权重(权重排序取绝对值)
    private Double weight;

    //python返回的批次标记字段，只做解析不需要入库
    private String batchStr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
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

    public Long getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(Long experimentId) {
        this.experimentId = experimentId;
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

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getBatchStr() {
        return batchStr;
    }

    public void setBatchStr(String batchStr) {
        this.batchStr = batchStr;
    }
}
