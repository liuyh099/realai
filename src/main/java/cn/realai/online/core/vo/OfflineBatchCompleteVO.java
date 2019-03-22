package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 功能描述：TODO
 *
 * @author admin
 * @create 2019-03-18 14:28
 */
@ApiModel
public class OfflineBatchCompleteVO {

    @ApiModelProperty(value="跑批记录ID")
    private Long batchid;

    @ApiModelProperty(value="是否完成", example="true:完成 false:未完成")
    private boolean complete;

    public Long getBatchid() {
        return batchid;
    }

    public void setBatchid(Long batchid) {
        this.batchid = batchid;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
