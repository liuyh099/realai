package cn.realai.online.core.entity;

/**
 * 样本分组
 *
 * @author lyh
 */
public class SampleGrouping {

    private Long id;

    private Long experimentId;

    private String groupName;

    private Long createTime;

    //组数量
    private Integer count;

    //组占总数的比例
    private Double percentage;

    //正本比例
    private Double positiveRatio;

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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Double getPositiveRatio() {
        return positiveRatio;
    }

    public void setPositiveRatio(Double positiveRatio) {
        this.positiveRatio = positiveRatio;
    }
}
