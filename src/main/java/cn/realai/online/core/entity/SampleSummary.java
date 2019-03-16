package cn.realai.online.core.entity;

/**
 * 样本摘要
 * @author lyh
 */
public class SampleSummary {

	private long id;
	
	//实验id
	private long experimentId;
	
	//数据集类型
	private int dataSetType;
	
	//样本描述
	private String sampleDesc;
	
	//样本数量
	private int sampleCount;
	
	//样本用户数
	private int numberOfLabelUsers;
	
	//标签样本比例
	private String labelSampleRatio;
	
	//预测最小值
	private String min;
	
	//预测最大值
	private String max;
	
	//预测平均值
	private String mean;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getExperimentId() {
		return experimentId;
	}

	public void setExperimentId(long experimentId) {
		this.experimentId = experimentId;
	}

	public int getDataSetType() {
		return dataSetType;
	}

	public void setDataSetType(int dataSetType) {
		this.dataSetType = dataSetType;
	}

	public String getSampleDesc() {
		return sampleDesc;
	}

	public void setSampleDesc(String sampleDesc) {
		this.sampleDesc = sampleDesc;
	}

	public int getSampleCount() {
		return sampleCount;
	}

	public void setSampleCount(int sampleCount) {
		this.sampleCount = sampleCount;
	}

	public int getNumberOfLabelUsers() {
		return numberOfLabelUsers;
	}

	public void setNumberOfLabelUsers(int numberOfLabelUsers) {
		this.numberOfLabelUsers = numberOfLabelUsers;
	}

	public String getLabelSampleRatio() {
		return labelSampleRatio;
	}

	public void setLabelSampleRatio(String labelSampleRatio) {
		this.labelSampleRatio = labelSampleRatio;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getMean() {
		return mean;
	}

	public void setMean(String mean) {
		this.mean = mean;
	}
	
}
