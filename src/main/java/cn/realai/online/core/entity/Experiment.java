package cn.realai.online.core.entity;

/**
 * 实验
 * @author lyh
 */
public class Experiment {

	//主键id
	private long id;
	
	//训练名称
	private String name;
	
	//服务id
	private String serviceId;
	
	//状态
	private int status;
	
	//选择文件
	public static final int STATUS_FILE = 1;
	
	//选择参数
	public static final int STATUS_PARAM = 2;
	
	//变量筛选
	public static final int STATUS_FILTER = 3;
	
	//试验训练
	public static final int STATUS_TRAINING = 4;
	
	//试验完毕
	public static final int STATUS_TRAINING_OVER = 5;

	//算法类型
	private String algorithmType;
	
	//发布状态
	private int releasStatus;
	
	//未发布
	public static final int RELEAS_NO = 1;
	
	//已发布
	public static final int RELEAS_YES = 2;
	
	//y表数据源
	private String ytableDataSource;
	
	//x表同质数据源
	private String xtableHomogeneousDataSource;
	
	//x表异质数据源
	private String xtableHeterogeneousDataSource;
	
	//x轴含义（变量名解释）
	private String xtableMeaningDataSource;
	
	//是否选择验证集
	private int verificationSet;
	
	//未选择
	public static final int SELECT_NO = 1;
	
	//选择
	public static final int SELECT_YES = 2;

	//样本选择类型
	private int sampleType;
	
	//时间
	public static final int SAMPLE_TYPE_TIME = 1;
	
	//随机
	public static final int SAMPLE_TYPE_RANDOM = 2;
	
	//训练比例  trainRatio + trainTest + trainValid = 10
	private int trainRatio;
	
	//测试比例
	private int testRatio;
	
	//验证比例
	private int validRatio;
	
	public static final int DATA_SET_TRAIN = 1;
	
	public static final int DATA_SET_TEST = 2;
	
	public static final int DATA_SET_VALID = 3;
	
	//创建时间
	private long createTime;
	
	//训练时间
	private long trainingTime;
	
	//发布时间
	private long releaseTime;
	
	//调优次数
	private int tuningConter;
	
	//创建人
	private int createUser;
	
	//备注
	private String remark;
	
	//样本综述
	private String sampleReview;
	
	//模型路径
	private String modelUrl;
	
	//分段统计图片地址
	private String segmentationStatisticsImageUrl;
	
	//badTop总数图片地址
	private String badTopCountImageUrl;
	
	//roc图片地址
	private String rocImageUrl;

	public String getSampleReview() {
		return sampleReview;
	}

	public void setSampleReview(String sampleReview) {
		this.sampleReview = sampleReview;
	}

	public int getSampleType() {
		return sampleType;
	}

	public void setSampleType(int sampleType) {
		this.sampleType = sampleType;
	}

	public String getModelUrl() {
		return modelUrl;
	}

	public void setModelUrl(String modelUrl) {
		this.modelUrl = modelUrl;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getXtableMeaningDataSource() {
		return xtableMeaningDataSource;
	}

	public void setXtableMeaningDataSource(String xtableMeaningDataSource) {
		this.xtableMeaningDataSource = xtableMeaningDataSource;
	}

	public String getAlgorithmType() {
		return algorithmType;
	}

	public void setAlgorithmType(String algorithmType) {
		this.algorithmType = algorithmType;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getReleasStatus() {
		return releasStatus;
	}

	public void setReleasStatus(int releasStatus) {
		this.releasStatus = releasStatus;
	}

	public String getYtableDataSource() {
		return ytableDataSource;
	}

	public void setYtableDataSource(String ytableDataSource) {
		this.ytableDataSource = ytableDataSource;
	}

	public String getXtableHomogeneousDataSource() {
		return xtableHomogeneousDataSource;
	}

	public void setXtableHomogeneousDataSource(String xtableHomogeneousDataSource) {
		this.xtableHomogeneousDataSource = xtableHomogeneousDataSource;
	}

	public String getXtableHeterogeneousDataSource() {
		return xtableHeterogeneousDataSource;
	}

	public void setXtableHeterogeneousDataSource(String xtableHeterogeneousDataSource) {
		this.xtableHeterogeneousDataSource = xtableHeterogeneousDataSource;
	}

	public int getVerificationSet() {
		return verificationSet;
	}

	public void setVerificationSet(int verificationSet) {
		this.verificationSet = verificationSet;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getTrainingTime() {
		return trainingTime;
	}

	public void setTrainingTime(long trainingTime) {
		this.trainingTime = trainingTime;
	}

	public long getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(long releaseTime) {
		this.releaseTime = releaseTime;
	}

	public int getTuningConter() {
		return tuningConter;
	}

	public void setTuningConter(int tuningConter) {
		this.tuningConter = tuningConter;
	}

	public int getCreateUser() {
		return createUser;
	}

	public void setCreateUser(int createUser) {
		this.createUser = createUser;
	}

	public int getTrainRatio() {
		return trainRatio;
	}

	public void setTrainRatio(int trainRatio) {
		this.trainRatio = trainRatio;
	}

	public int getTestRatio() {
		return testRatio;
	}

	public void setTestRatio(int testRatio) {
		this.testRatio = testRatio;
	}

	public int getValidRatio() {
		return validRatio;
	}

	public void setValidRatio(int validRatio) {
		this.validRatio = validRatio;
	}

	public String getSegmentationStatisticsImageUrl() {
		return segmentationStatisticsImageUrl;
	}

	public void setSegmentationStatisticsImageUrl(String segmentationStatisticsImageUrl) {
		this.segmentationStatisticsImageUrl = segmentationStatisticsImageUrl;
	}

	public String getBadTopCountImageUrl() {
		return badTopCountImageUrl;
	}

	public void setBadTopCountImageUrl(String badTopCountImageUrl) {
		this.badTopCountImageUrl = badTopCountImageUrl;
	}

	public String getRocImageUrl() {
		return rocImageUrl;
	}

	public void setRocImageUrl(String rocImageUrl) {
		this.rocImageUrl = rocImageUrl;
	}

}
