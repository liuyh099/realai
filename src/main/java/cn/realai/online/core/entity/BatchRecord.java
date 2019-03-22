package cn.realai.online.core.entity;

/**
 * 批次记录
 *
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

    //实验id
    private Long experimentId;

    //服务id
    private Long serviceId;

    //创建时间
    private Long createTime;
    
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

	//第几次离线跑批
	private Integer offlineTimes;
    
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

	public Integer getOfflineTimes() {
		return offlineTimes;
	}

	public void setOfflineTimes(Integer offlineTimes) {
		this.offlineTimes = offlineTimes;
	}

}
