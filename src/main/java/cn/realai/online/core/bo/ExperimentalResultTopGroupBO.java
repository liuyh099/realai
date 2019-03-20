package cn.realai.online.core.bo;

import java.util.List;

public class ExperimentalResultTopGroupBO {


    private List<TopSortBO> responseDataList;

    private List<TopSortBO> noResponseDataList;

    public List<TopSortBO> getResponseDataList() {
        return responseDataList;
    }

    public void setResponseDataList(List<TopSortBO> responseDataList) {
        this.responseDataList = responseDataList;
    }

    public List<TopSortBO> getNoResponseDataList() {
        return noResponseDataList;
    }

    public void setNoResponseDataList(List<TopSortBO> noResponseDataList) {
        this.noResponseDataList = noResponseDataList;
    }
}
