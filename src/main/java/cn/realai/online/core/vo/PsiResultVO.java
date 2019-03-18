package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 功能描述：TODO
 *
 * @author admin
 * @create 2019-03-18 14:28
 */
@ApiModel
public class PsiResultVO {

    @ApiModelProperty(value="PSI检测结果Id")
    private long psiId;

    @ApiModelProperty(value="变量来源")
    private String varSource;

    @ApiModelProperty(value="变量含义")
    private String mean;

    @ApiModelProperty(value="PSI")
    private String psi;

    @ApiModelProperty(value="是否预警 ")
    private int aler;

    @ApiModelProperty(value="是否预警中文名称")
    private String alerName;

    public long getPsiId() {
        return psiId;
    }

    public void setPsiId(long psiId) {
        this.psiId = psiId;
    }

    public String getVarSource() {
        return varSource;
    }

    public void setVarSource(String varSource) {
        this.varSource = varSource;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public String getPsi() {
        return psi;
    }

    public void setPsi(String psi) {
        this.psi = psi;
    }

    public int getAler() {
        return aler;
    }

    public void setAler(int aler) {
        this.aler = aler;
    }

    public String getAlerName() {
        return alerName;
    }

    public void setAlerName(String alerName) {
        this.alerName = alerName;
    }
}
