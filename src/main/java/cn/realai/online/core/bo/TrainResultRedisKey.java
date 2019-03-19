package cn.realai.online.core.bo;

/**
 * 实验训练的结果的key
 * @author lyh
 */
public class TrainResultRedisKey {
	
	//模型表现
	private String modelperformance;
	
	//top排序
	private String topsort;
	
	//样本摘要
	private String sampleSummary;
	
	//样本分组
	private String sampleGrouping;
	
	//样本权重
	private String sampleWeight;
	
	//实验样本人员信息（千人千面人员信息）
	private String personalInformation;
	
	//千人千面同质数据
	private String personalHomoResultSet;
	
	//千人千面异质数据
	private String personalHetroResultSet;
	
	//千人千面组合和特征
	private String personalComboResultSet;
	
	//psi检测结果
	private String psiCheckResult;

	public String getModelperformance() {
		return modelperformance;
	}

	public void setModelperformance(String modelperformance) {
		this.modelperformance = modelperformance;
	}

	public String getTopsort() {
		return topsort;
	}

	public void setTopsort(String topsort) {
		this.topsort = topsort;
	}

	public String getSampleSummary() {
		return sampleSummary;
	}

	public void setSampleSummary(String sampleSummary) {
		this.sampleSummary = sampleSummary;
	}

	public String getSampleGrouping() {
		return sampleGrouping;
	}

	public void setSampleGrouping(String sampleGrouping) {
		this.sampleGrouping = sampleGrouping;
	}

	public String getSampleWeight() {
		return sampleWeight;
	}

	public void setSampleWeight(String sampleWeight) {
		this.sampleWeight = sampleWeight;
	}

	public String getPersonalInformation() {
		return personalInformation;
	}

	public void setPersonalInformation(String personalInformation) {
		this.personalInformation = personalInformation;
	}

	public String getPersonalHomoResultSet() {
		return personalHomoResultSet;
	}

	public void setPersonalHomoResultSet(String personalHomoResultSet) {
		this.personalHomoResultSet = personalHomoResultSet;
	}

	public String getPersonalHetroResultSet() {
		return personalHetroResultSet;
	}

	public void setPersonalHetroResultSet(String personalHetroResultSet) {
		this.personalHetroResultSet = personalHetroResultSet;
	}

	public String getPersonalComboResultSet() {
		return personalComboResultSet;
	}

	public void setPersonalComboResultSet(String personalComboResultSet) {
		this.personalComboResultSet = personalComboResultSet;
	}

	public String getPsiCheckResult() {
		return psiCheckResult;
	}

	public void setPsiCheckResult(String psiCheckResult) {
		this.psiCheckResult = psiCheckResult;
	}
	
}
