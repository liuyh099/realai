package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModelProperty;

public class DataOverviewServiceSelectOptionVO {

    @ApiModelProperty(value = "")
    private Long id;

    private String name;

    private boolean checkFlag;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(boolean checkFlag) {
        this.checkFlag = checkFlag;
    }
}
