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

    @ApiModelProperty(value = "modelId")
    private long modelId;

    @ApiModelProperty(value = "最大Psi")
    private double maxPsi;

    @ApiModelProperty(value = "是否可以检测Psi（true：是 false：否）")
    private boolean psiFlag;

    @ApiModelProperty(value = "不许检测Psi原因")
    private String psiReason;

    @ApiModelProperty(value = "是否可以密钥强制调优（true：是 false：否）")
    private boolean keyFlag;

    @ApiModelProperty(value = "不许强制调优原因")
    private String keyReason;

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

    public boolean isPsiFlag() {
        return psiFlag;
    }

    public void setPsiFlag(boolean psiFlag) {
        this.psiFlag = psiFlag;
    }

    public String getPsiReason() {
        return psiReason;
    }

    public void setPsiReason(String psiReason) {
        this.psiReason = psiReason;
    }

    public boolean isKeyFlag() {
        return keyFlag;
    }

    public void setKeyFlag(boolean keyFlag) {
        this.keyFlag = keyFlag;
    }

    public String getKeyReason() {
        return keyReason;
    }

    public void setKeyReason(String keyReason) {
        this.keyReason = keyReason;
    }
}
