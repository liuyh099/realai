package cn.realai.online.core.entity;

/**
 * 实验
 *
 * @author lyh
 */
public class Experiment {

    //主键id
    private Long id;

    //训练名称
    private String name;

    //验证集类型
	private Integer validSampleType;
	
	//测试机类型
	private Integer testSampleType;
	
	//时间
	public static final int SAMPLE_SPLIT_TYPE_TIME = 1;
	
	//随机
	public static final int SAMPLE_SPLIT_TYPE_RANDOM = 2;
	
	//训练比例  trainRatio + trainTest + trainValid = 10
	private Integer trainRatio;
	
	//测试比例
	private Integer testRatio;
	
	//验证比例
	private Integer validRatio;
    //服务id
    private long serviceId;

    //状态
    private Integer status;

    //选择文件
    public static final int STATUS_FILE = 1;

    //选择参数
    public static final int STATUS_PARAM = 2;

    //变量筛选
    public static final int STATUS_FILTER = 3;

    //试验训练中
    public static final int STATUS_TRAINING = 4;

    //试验完毕
    public static final int STATUS_TRAINING_OVER = 5;

    //算法类型
    private String algorithmType;

    //发布状态
    private Integer releasStatus;

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
    private Integer verificationSet;

    //未选择
    public static final int SELECT_NO = 1;

    //选择
    public static final int SELECT_YES = 2;

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
    private Integer tuningCount;

    //创建人
    private Integer createUserId;

    //备注
    private String remark;

    //样本综述
    private String sampleReview;

    //模型路径
    private String modelUrl;

    //分段统计图片地址（千人千面也是他）
    private String segmentationStatisticsImageUrl;

    //badTop总数图片地址
    private String badTopCountImageUrl;

    //roc图片地址
    private String rocTestImageUrl;

    private String rocTrainImageUrl;

    private String rocValidateImageUrl;


    //ks图片地址
    private String ksTestImageUrl;

    private String ksTrainImageUrl;

    private String ksValidateImageUrl;

    private Integer preFinish;

    public static final int PREFINISH_NO = 1; // 预处理未完成

    public static final int PREFINISH_YES = 2; //预处理完成

    public Integer getPreFinish() {
        return preFinish;
    }

    public void setPreFinish(Integer preFinish) {
        this.preFinish = preFinish;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAlgorithmType() {
        return algorithmType;
    }

    public void setAlgorithmType(String algorithmType) {
        this.algorithmType = algorithmType;
    }

    public Integer getReleasStatus() {
        return releasStatus;
    }

    public void setReleasStatus(Integer releasStatus) {
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

    public String getXtableMeaningDataSource() {
        return xtableMeaningDataSource;
    }

    public void setXtableMeaningDataSource(String xtableMeaningDataSource) {
        this.xtableMeaningDataSource = xtableMeaningDataSource;
    }

    public Integer getVerificationSet() {
        return verificationSet;
    }

    public void setVerificationSet(Integer verificationSet) {
        this.verificationSet = verificationSet;
    }

    public Integer getValidSampleType() {
		return validSampleType;
	}

	public void setValidSampleType(Integer validSampleType) {
		this.validSampleType = validSampleType;
	}

	public Integer getTestSampleType() {
		return testSampleType;
	}

	public void setTestSampleType(Integer testSampleType) {
		this.testSampleType = testSampleType;
	}

	public Integer getTrainRatio() {
        return trainRatio;
    }

    public void setTrainRatio(Integer trainRatio) {
        this.trainRatio = trainRatio;
    }

    public Integer getTestRatio() {
        return testRatio;
    }

    public void setTestRatio(Integer testRatio) {
        this.testRatio = testRatio;
    }

    public Integer getValidRatio() {
        return validRatio;
    }

    public void setValidRatio(Integer validRatio) {
        this.validRatio = validRatio;
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

    public Integer getTuningCount() {
        return tuningCount;
    }

    public void setTuningCount(Integer tuningCount) {
        this.tuningCount = tuningCount;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSampleReview() {
        return sampleReview;
    }

    public void setSampleReview(String sampleReview) {
        this.sampleReview = sampleReview;
    }

    public String getModelUrl() {
        return modelUrl;
    }

    public void setModelUrl(String modelUrl) {
        this.modelUrl = modelUrl;
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

    public String getRocTestImageUrl() {
        return rocTestImageUrl;
    }

    public void setRocTestImageUrl(String rocTestImageUrl) {
        this.rocTestImageUrl = rocTestImageUrl;
    }

    public String getRocTrainImageUrl() {
        return rocTrainImageUrl;
    }

    public void setRocTrainImageUrl(String rocTrainImageUrl) {
        this.rocTrainImageUrl = rocTrainImageUrl;
    }

    public String getRocValidateImageUrl() {
        return rocValidateImageUrl;
    }

    public void setRocValidateImageUrl(String rocValidateImageUrl) {
        this.rocValidateImageUrl = rocValidateImageUrl;
    }

    public String getKsTestImageUrl() {
        return ksTestImageUrl;
    }

    public void setKsTestImageUrl(String ksTestImageUrl) {
        this.ksTestImageUrl = ksTestImageUrl;
    }

    public String getKsTrainImageUrl() {
        return ksTrainImageUrl;
    }

    public void setKsTrainImageUrl(String ksTrainImageUrl) {
        this.ksTrainImageUrl = ksTrainImageUrl;
    }

    public String getKsValidateImageUrl() {
        return ksValidateImageUrl;
    }

    public void setKsValidateImageUrl(String ksValidateImageUrl) {
        this.ksValidateImageUrl = ksValidateImageUrl;
    }
}
