package cn.realai.online.core.entity;

/**
 * 批次记录
 * @author lyh
 */
public class BatchRecord {

	private Long id;
	
	//y表数据源
	private String ytableDataSource;
	
	//x表同质数据源
	private String xtableHomogeneousDataSource;
	
	//x表异质数据源
	private String xtableHeterogeneousDataSource;

	//跑批模型Id
	private Long modelId;
	
	//实验id
	private Long experimentId;
	
	//服务id
	private Long serviceId;

	//创建时间
	private Long createTime;  
	
	//每日跑批 批次日期
	private String batchDate;
	
	//结果下载路径
	private String downUrl;
	
	//批次类型
	private Integer batchType;
	
	public static final int BATCH_TYPE_OFFLINE = 1; // 离线跑批
	
	public static final int BATCH_TYPE_DAILY = 2; //每日跑批

	public static final int BATCH_TYPE_TRAIN = 3; //训练批次

	//批次名称
	private String batchName;

	private String remark;

	//查询状态-非实体字段
	private boolean tranFlag;
	
	private int status;
	
	public static int BATCH_STATUS_NEW = 1;  //新建
	
	public static int BATCH_STATUS_EXECUTING = 2;  //执行中
	
	public static int BATCH_STATUS_OVER = 3;  //处理完成
	
	public static int BATCH_STATUS_ERROR = 4;  //处理错误
	
	public String errorMsg;

	//删除标志位
	private int delFlag;

	public static int NO_DEL = 0; //未删除

	public static int YES_DEL = 1; //已删除

	//第几次离线跑批
	private Integer offlineTimes;

	public Integer getOfflineTimes() {
		return offlineTimes;
	}

	public void setOfflineTimes(Integer offlineTimes) {
		this.offlineTimes = offlineTimes;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean getTranFlag() {
		return tranFlag;
	}

	public void setTranFlag(boolean tranFlag) {
		this.tranFlag = tranFlag;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public Long getExperimentId() {
		return experimentId;
	}

	public void setExperimentId(Long experimentId) {
		this.experimentId = experimentId;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getDownUrl() {
		return downUrl;
	}

	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}

	public Integer getBatchType() {
		return batchType;
	}

	public void setBatchType(Integer batchType) {
		this.batchType = batchType;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public String getBatchDate() {
		return batchDate;
	}

	public void setBatchDate(String batchDate) {
		this.batchDate = batchDate;
	}
	
}
