package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ExperimentalResultImageVO {

    //roc图片地址
    @ApiModelProperty(value = "rocTestImageUrl",name = "roc测试图片地址")
    private String rocTestImageUrl;

    @ApiModelProperty(value = "rosTrainImageUrl",name = "roc训练图片地址")
    private String rocTrainImageUrl;

    @ApiModelProperty(value = "rosValidateImageUrl",name = "roc验证图片地址")
    private String rocValidateImageUrl;


    //roc图片地址
    @ApiModelProperty(value = "ksTestImageUrl",name = "ks测试图片地址")
    private String ksTestImageUrl;

    @ApiModelProperty(value = "ksTrainImageUrl",name = "ks训练图片地址")
    private String ksTrainImageUrl;

    @ApiModelProperty(value = "ksValidateImageUrl",name = "ks验证图片地址")
    private String ksValidateImageUrl;

    public String getRocTestImageUrl() {
        return rocTestImageUrl;
    }

    public void setRocTestImageUrl(String rocTestImageUrl) {
        this.rocTestImageUrl = rocTestImageUrl;
    }

    public String getRocTrainImageUrl() {
        return rocTrainImageUrl;
    }

    public void setRocTrainImageUrl(String rocTrainImageUrl) {
        this.rocTrainImageUrl = rocTrainImageUrl;
    }

    public String getRocValidateImageUrl() {
        return rocValidateImageUrl;
    }

    public void setRocValidateImageUrl(String rocValidateImageUrl) {
        this.rocValidateImageUrl = rocValidateImageUrl;
    }

    public String getKsTestImageUrl() {
        return ksTestImageUrl;
    }

    public void setKsTestImageUrl(String ksTestImageUrl) {
        this.ksTestImageUrl = ksTestImageUrl;
    }

    public String getKsTrainImageUrl() {
        return ksTrainImageUrl;
    }

    public void setKsTrainImageUrl(String ksTrainImageUrl) {
        this.ksTrainImageUrl = ksTrainImageUrl;
    }

    public String getKsValidateImageUrl() {
        return ksValidateImageUrl;
    }

    public void setKsValidateImageUrl(String ksValidateImageUrl) {
        this.ksValidateImageUrl = ksValidateImageUrl;
    }
}
