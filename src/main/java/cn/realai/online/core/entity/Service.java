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
	
	//状态
	private Integer status;
	
	public static final int STATUS_ONLINE = 1; //上线
	
	public static final int STATUS_OFFLINE = 2; //下线
	
	private int businessType;
	
	public static final int BUSINESSTYPE_WIND = 1; //风控
	
	public static final int BUSINESSTYPE_MARKETING = 2; //营销
	
	//部署类型
	private Integer deploymentType;
	
	public static final int DEPLOYMENT_TYPE_ONLINE = 1; //在线部署
	
	public static final int DEPLOYMENT_TYPE_OFFLINE = 2; //离线部署
	
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

	public Integer getDeploymentType() {
		return deploymentType;
	}

	public void setDeploymentType(Integer deploymentType) {
		this.deploymentType = deploymentType;
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

	public int getBusinessType() {
		return businessType;
	}

	public void setBusinessType(int businessType) {
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
