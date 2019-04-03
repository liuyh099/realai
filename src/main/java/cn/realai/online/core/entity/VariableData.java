package cn.realai.online.core.entity;

/**
 * X轴变量数据
 *
 * @author lyh
 */
public class VariableData {

    //主键
    private Long id;

    //实验id
    private Long experimentId;

    //名称
    private String name;

    //变量含义
    private String meaning;

    //数据类型
    private String dataType;

    //数值
    public static final int DATATYPE_NUM = 1;

    //类别
    public static final int DATATYPE_ENUM = 3;

    //总数
    private Integer count;

    //最大值
    private String max;

    //最小值
    private String min;

    //平均值
    private String mean;

    //中间值 中位数
    private String median;

    //25%
    private String percent25;

    //75%
    private String percent75;

    //模式类型
    private Integer variableType;

    //同质
    public static final int VARIABLE_TYPE_HOMO = 2;

    //异质
    public static final int VARIABLE_TYPE_HETRO = 1;

    //推荐删除
    private int recommendedDelete;

    public static final int RECOMMENDED_DELETE_YES = 2;

    public static final int RECOMMENDED_DELETE_NO = 1;

    //删除
    private int delete;

    public static final int DELETE_YES = 2;

    public static final int DELETE_NO = 1;

    //创建时间
    private long createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(Long experimentId) {
        this.experimentId = experimentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public String getMedian() {
        return median;
    }

    public void setMedian(String median) {
        this.median = median;
    }

    public String getPercent25() {
        return percent25;
    }

    public void setPercent25(String percent25) {
        this.percent25 = percent25;
    }

    public String getPercent75() {
        return percent75;
    }

    public void setPercent75(String percent75) {
        this.percent75 = percent75;
    }

    public Integer getVariableType() {
        return variableType;
    }

    public void setVariableType(Integer variableType) {
        this.variableType = variableType;
    }

    public int getRecommendedDelete() {
        return recommendedDelete;
    }

    public void setRecommendedDelete(int recommendedDelete) {
        this.recommendedDelete = recommendedDelete;
    }

    public int getDelete() {
        return delete;
    }

    public void setDelete(int delete) {
        this.delete = delete;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
