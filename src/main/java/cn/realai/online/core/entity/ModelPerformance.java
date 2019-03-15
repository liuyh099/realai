package cn.realai.online.core.entity;

/**
 * 模型表现
 * 描述实验的效果
 * @author lyh
 */
public class ModelPerformance {

	private long id;
	
	//指标名
	private String metricName;
	
	//实验id
	private long experimentId;
	
	//训练集
	private Double trainingSet;
	
	//测试集
	private Double testSet;
	
	//验证集
	private Double validSet;
	
	private long createTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMetricName() {
		return metricName;
	}

	public void setMetricName(String metricName) {
		this.metricName = metricName;
	}

	public long getExperimentId() {
		return experimentId;
	}

	public void setExperimentId(long experimentId) {
		this.experimentId = experimentId;
	}

	public Double getTrainingSet() {
		return trainingSet;
	}

	public void setTrainingSet(Double trainingSet) {
		this.trainingSet = trainingSet;
	}

	public Double getTestSet() {
		return testSet;
	}

	public void setTestSet(Double testSet) {
		this.testSet = testSet;
	}

	public Double getValidSet() {
		return validSet;
	}

	public void setValidSet(Double validSet) {
		this.validSet = validSet;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
}
