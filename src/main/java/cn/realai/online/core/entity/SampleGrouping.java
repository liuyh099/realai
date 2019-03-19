package cn.realai.online.core.entity;

/**
 * 样本分组
 * @author lyh
 */
public class SampleGrouping {

	private long id;
	
	private long experimentId;
	
	private String groupName;
	
	private long createTime;
	
	//组数量
	private int count;
	
	//组占总数的比例
	private Double percentage;
	
	//正本比例
	private Double positiveRatio;

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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public Double getPositiveRatio() {
		return positiveRatio;
	}

	public void setPositiveRatio(Double positiveRatio) {
		this.positiveRatio = positiveRatio;
	}
	
}
