package cn.realai.online.core.entity;

/**
 * top排序
 * 最好和最坏的预测值表现
 * @author lyh
 */
public class TopSort {

	private long id;
	
	//实验id
	private long experimentId;
	
	//数据集类型
	private int dataSetType;
	
	//响应类型
	private int responseType;
	
	//top
	private String top;
	
	//样本数量
	private int sampleCount;
	
	//客户逾期数
	private int customerOverdueCount;
	
	//逾期率
	private Double overdueRate;
	
	//总逾期率 占总逾期客户比例
	private Double totalOverdueRate;
	
	//创建时间
	private long createTime;

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

	public int getResponseType() {
		return responseType;
	}

	public void setResponseType(int responseType) {
		this.responseType = responseType;
	}

	public String getTop() {
		return top;
	}

	public void setTop(String top) {
		this.top = top;
	}

	public int getSampleCount() {
		return sampleCount;
	}

	public void setSampleCount(int sampleCount) {
		this.sampleCount = sampleCount;
	}

	public int getCustomerOverdueCount() {
		return customerOverdueCount;
	}

	public void setCustomerOverdueCount(int customerOverdueCount) {
		this.customerOverdueCount = customerOverdueCount;
	}

	public Double getOverdueRate() {
		return overdueRate;
	}

	public void setOverdueRate(Double overdueRate) {
		this.overdueRate = overdueRate;
	}

	public Double getTotalOverdueRate() {
		return totalOverdueRate;
	}

	public void setTotalOverdueRate(Double totalOverdueRate) {
		this.totalOverdueRate = totalOverdueRate;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
}
