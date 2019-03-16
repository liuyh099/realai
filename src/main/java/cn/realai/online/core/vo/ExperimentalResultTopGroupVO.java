package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel
public class ExperimentalResultTopGroupVO {

    @ApiModelProperty(value = "响应结果集")
    private List<ExperimentalResultTopGroupDataVO> responseDataList;
    @ApiModelProperty(value = "未响应结果集")
    private List<ExperimentalResultTopGroupDataVO> noResponseDataList;

    public List<ExperimentalResultTopGroupDataVO> getResponseDataList() {
        return responseDataList;
    }

    public void setResponseDataList(List<ExperimentalResultTopGroupDataVO> responseDataList) {
        this.responseDataList = responseDataList;
    }

    public List<ExperimentalResultTopGroupDataVO> getNoResponseDataList() {
        return noResponseDataList;
    }

    public void setNoResponseDataList(List<ExperimentalResultTopGroupDataVO> noResponseDataList) {
        this.noResponseDataList = noResponseDataList;
    }
}
