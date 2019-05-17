package cn.realai.online.core.entity;

/**
 * 服务（我的服务）
 * @author lyh
 */
public class Service {

	//主键
	private long id;
	
	//服务类型
	private Integer type;
	
	//服务名称
	private String name;
	
	public static final int STATUS_ONLINE = 1; //上线  
	
	public static final int STATUS_OFFLINE = 2; //下线

	/**
	 * 服务子类型 1：A卡 2：B卡 3：C卡 4：营销
	 */
	private Integer businessType;
	
	public static final int TYPE_WIND = 1; //A卡/B卡/C卡
	
	public static final int TYPE_MARKETING = 2; //营销

	public static final int BUSINESSTYPE_A = 1; //A卡

	public static final int BUSINESSTYPE_B = 2; //B卡

	public static final int BUSINESSTYPE_C = 3; //C卡

	public static final int BUSINESSTYPE_MARKETING = 4; //营销

	public static final String TYPE_EXPLAIN = "1:A卡/B卡/C卡 2:营销";

	public static final String BUSINESSTYPE_EXPLAIN = "1:A卡 2:B卡 3:C卡 4:营销";
	
	//创建时间
	private long createTime;
	
	//起始时间
	private long startTime;
	
	//到期时间
	private long expireDate;

	//受控信息
	private String detail;

	//调优秘钥
	private String tuningSecretKey;

	//秘钥
	private String secretKey;
	
	//发布次数
	private int releaseCount;

	//调优次数上限
	private int deployTimesUpper;
	
	//上线实验
	private long onlineExperiment;

	//离线跑批次数
	private long batchTimes;

	private int delFlag;  //删除标记 0 : 未删除   1：已删除

	/**
	 * 是否可用
	 */
	private boolean available;

	private boolean discard = false;

	private boolean expired = false;  // true 过期

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public boolean isDiscard() {
		return discard;
	}

	public void setDiscard(boolean discard) {
		this.discard = discard;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public int getDeployTimesUpper() {
		return deployTimesUpper;
	}

	public void setDeployTimesUpper(int deployTimesUpper) {
		this.deployTimesUpper = deployTimesUpper;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public long getBatchTimes() {
		return batchTimes;
	}

	public void setBatchTimes(long batchTimes) {
		this.batchTimes = batchTimes;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public int getReleaseCount() {
		return releaseCount;
	}

	public void setReleaseCount(int releaseCount) {
		this.releaseCount = releaseCount;
	}

	public long getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(long expireDate) {
		this.expireDate = expireDate;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public long getOnlineExperiment() {
		return onlineExperiment;
	}

	public void setOnlineExperiment(long onlineExperiment) {
		this.onlineExperiment = onlineExperiment;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public String getTuningSecretKey() {
		return tuningSecretKey;
	}

	public void setTuningSecretKey(String tuningSecretKey) {
		this.tuningSecretKey = tuningSecretKey;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}


	public static String getDescByType(Integer type) {
		if (type != null) {
			if (type == TYPE_WIND) {
				return "风控";
			} else if (type == TYPE_MARKETING) {
				return "营销";
			}
		}
		return null;
	}

	public static String getDescByBusinessType(Integer businessType) {
		if (businessType != null) {
			if (businessType == BUSINESSTYPE_A)  {
				return "A卡";
			} else if (businessType == BUSINESSTYPE_B) {
				return "B卡";
			} else if (businessType == BUSINESSTYPE_C) {
				return "C卡";
			} else if (businessType == BUSINESSTYPE_MARKETING) {
				return "营销";
			}
		}
		return null;
	}

	public static String getServiceTypeName(Integer type, Integer businessType) {
		if (type == null) {
			return null;
		}
		if (type == TYPE_MARKETING) {
			return "营销";
		}
		if (type == TYPE_WIND) {
			if (businessType == null) {
				return "风控";
			} else if (businessType == BUSINESSTYPE_A || businessType == BUSINESSTYPE_B || businessType == BUSINESSTYPE_C) {
				return getDescByType(type) + getDescByBusinessType(businessType);
			}
			return "风控";
		}
		return null;
	}
}
