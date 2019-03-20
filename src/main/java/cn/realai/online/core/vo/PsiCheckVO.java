package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 功能描述：TODO
 *
 * @author admin
 * @create 2019-03-18 14:28
 */
@ApiModel
public class PsiCheckVO {

    @ApiModelProperty(value="modelId")
    private long modelId;

    @ApiModelProperty(value="最大Psi")
    private double maxPsi;

    @ApiModelProperty(value="是否可以检测Psi（true：是 false：否）")
    private boolean flag;

    public long getModelId() {
        return modelId;
    }

    public void setModelId(long modelId) {
        this.modelId = modelId;
    }

    public double getMaxPsi() {
        return maxPsi;
    }

    public void setMaxPsi(double maxPsi) {
        this.maxPsi = maxPsi;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
