package cn.realai.online.core.entity;

/**
 * 跑批任务执行批次(批次基础执行任务)
 * python会将一个跑批批次按分割基数分割成一个个基础执行任务去执行
 * 每计算出一个基础执行任务的千人千面信息，就调用Java服务器一次
 * @author 86183
 */
public class BatchExecutionTask {

	private long id;
	
	private Long experimentId;
	
	private Long batchId;
	
	private String type;
	
	public static final String TYPE_PERSONALCOMBORESULTSET = "personalComboResultSet";
	public static final String TYPE_PERSONALHETRORESULTSET = "personalHetroResultSet";
	public static final String TYPE_PERSONALHOMORESULTSET = "personalHomoResultSet";
	public static final String TYPE_PERSONALINFORMATION = "personalInformation";
	public static final String TYPE_THOUSANDPEOPLE_INFORMATION = "thousandPeopleInformation";
	public static final String TYPE_PSICHECKRESULT = "psiCheckResult";
	public static final String TYPE_DOENURL = "downUrl";
	public static final String TYPE_DONE = "done";
	
	private String redisKey;
	
	private Integer count;
	
	private Boolean done;
	
	private Integer status;
	
	private String downUrl;
	
	private Long createTime;
	
	private String redisValue; //redisKey 对应的value,如果当前任务出错才会记录否则不会记录
	
	public static final int STATUS_NEW = 1;     //新建
	
	public static final int STATUS_SUCCESS = 2; //成功
	
	public static final int STATUS_FAIL = 3;    //失败
	
	public BatchExecutionTask(Long experimentId, Long batchId, String type, String redisKey, String downUrl,
			Boolean done, Integer status, Integer count) {
		this.experimentId = experimentId;
		this.batchId = batchId;
		this.type = type;
		this.redisKey = redisKey;
		this.downUrl = downUrl;
		this.done = done;
		this.status = status;
		this.count = count;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public String getRedisKey() {
		return redisKey;
	}

	public void setRedisKey(String redisKey) {
		this.redisKey = redisKey;
	}

	public Long getExperimentId() {
		return experimentId;
	}

	public void setExperimentId(Long experimentId) {
		this.experimentId = experimentId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public Boolean getDone() {
		return done;
	}

	public void setDone(Boolean done) {
		this.done = done;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDownUrl() {
		return downUrl;
	}

	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getRedisValue() {
		return redisValue;
	}

	public void setRedisValue(String redisValue) {
		this.redisValue = redisValue;
	}
	
}
