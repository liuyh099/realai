package cn.realai.online.core.entity;

/**
 * 样本权重
 * @author lyh
 */
public class SampleWeight {

	private long id;
	
	private long experimentId;
	
	private long groupId;
	
	//变量名称
	private String sampleName;
	
	//变量备注
	private String samplereMark;
	
	//变量权重
	private String sampleWeight;
	
	//变量类型
	private int sampleType;
	
	//同质
	public static final int SAMPLE_TYPE_HOMO = 1;
	
	//异质
	public static final int SAMPLE_TYPE_HETERO = 2;
	
	//分箱特征
	private String boxBeans;
	
	//图片路径
	private String imgUrl;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public String getSampleName() {
		return sampleName;
	}

	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}

	public long getExperimentId() {
		return experimentId;
	}

	public void setExperimentId(long experimentId) {
		this.experimentId = experimentId;
	}

	public String getSamplereMark() {
		return samplereMark;
	}

	public void setSamplereMark(String samplereMark) {
		this.samplereMark = samplereMark;
	}

	public String getSampleWeight() {
		return sampleWeight;
	}

	public void setSampleWeight(String sampleWeight) {
		this.sampleWeight = sampleWeight;
	}

	public int getSampleType() {
		return sampleType;
	}

	public void setSampleType(int sampleType) {
		this.sampleType = sampleType;
	}

	public String getBoxBeans() {
		return boxBeans;
	}

	public void setBoxBeans(String boxBeans) {
		this.boxBeans = boxBeans;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
}
