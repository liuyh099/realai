package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.models.auth.In;

import java.util.List;

@ApiModel
public class EchartsTotalDataVo {


   private List<Integer> countList;

   private List<EchartsDataVo> data;

    public List<EchartsDataVo> getData() {
        return data;
    }

    public void setData(List<EchartsDataVo> data) {
        this.data = data;
    }

    public List<Integer> getCountList() {
        return countList;
    }

    public void setCountList(List<Integer> countList) {
        this.countList = countList;
    }
}
