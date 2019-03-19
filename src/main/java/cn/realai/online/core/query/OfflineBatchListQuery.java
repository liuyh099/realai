package cn.realai.online.core.query;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 功能描述：TODO
 *
 * @author admin
 * @create 2019-03-18 14:19
 */
@ApiModel
public class OfflineBatchListQuery extends PageQuery {

    @ApiModelProperty(value="模型名称")
    private String name;

    @ApiModelProperty(value="训练状态")
    private String trainStatus;

    @ApiModelProperty(value="跑批时间，格式：yyyy-MM-dd")
    private String runBatchTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrainStatus() {
        return trainStatus;
    }

    public void setTrainStatus(String trainStatus) {
        this.trainStatus = trainStatus;
    }

    public String getRunBatchTime() {
        return runBatchTime;
    }

    public void setRunBatchTime(String runBatchTime) {
        this.runBatchTime = runBatchTime;
    }
}
