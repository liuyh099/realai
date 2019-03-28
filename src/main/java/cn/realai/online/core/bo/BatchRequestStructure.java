package cn.realai.online.core.bo;

public class BatchRequestStructure {

    private Long experimentId;

    private String type;
    
	public static final String TYPE_PERSONALCOMBORESULTSET = "personalComboResultSet";
	public static final String TYPE_PERSONALHETRORESULTSET = "personalHetroResultSet";
	public static final String TYPE_PERSONALHOMORESULTSET = "personalHomoResultSet";
	public static final String TYPE_PERSONALINFORMATION = "personalInformation";
	public static final String TYPE_PSICHECKRESULT = "psiCheckResult";
	public static final String TYPE_DOENURL = "downUrl";
    
    private String redisKey;
    
    //每日跑批的批次 信息
    private String date;
    
    //离线跑批数据下载路径
    private String downUrl;
    
    //离线跑批id
    private Long batchId;
    
    public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public Long getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(Long experimentId) {
        this.experimentId = experimentId;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

	public String getRedisKey() {
		return redisKey;
	}

	public void setRedisKey(String redisKey) {
		this.redisKey = redisKey;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
