package cn.realai.online.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ExperimentalTrainSelectFileVO {

    @ApiModelProperty( value = "实验id" ,required = false, example = "新增非必填，更新需要")
    private long id;

    //主键id
    @ApiModelProperty( value = "服务ID" ,required = true)
    private long serverId;

    @ApiModelProperty( value = "实验名称" ,required = true)
    private String name;

    @ApiModelProperty( value = "y表数据源" ,required = true)
    private String ytableDataSource;

    //x表同质数据源
    @ApiModelProperty( value = "模型X表同质数据源" ,required = true)
    private String xtableHomogeneousDataSource;

    //x表异质数据源
    @ApiModelProperty( value = "模型X表异质数据源" ,required = true)
    private String xtableHeterogeneousDataSource;

    //x轴含义（变量名解释）
    @ApiModelProperty( value = "模型X表变量名称映射表" ,required = true)
    private String xtableMeaningDataSource;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getServerId() {
        return serverId;
    }

    public void setServerId(long serverId) {
        this.serverId = serverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYtableDataSource() {
        return ytableDataSource;
    }

    public void setYtableDataSource(String ytableDataSource) {
        this.ytableDataSource = ytableDataSource;
    }

    public String getXtableHomogeneousDataSource() {
        return xtableHomogeneousDataSource;
    }

    public void setXtableHomogeneousDataSource(String xtableHomogeneousDataSource) {
        this.xtableHomogeneousDataSource = xtableHomogeneousDataSource;
    }

    public String getXtableHeterogeneousDataSource() {
        return xtableHeterogeneousDataSource;
    }

    public void setXtableHeterogeneousDataSource(String xtableHeterogeneousDataSource) {
        this.xtableHeterogeneousDataSource = xtableHeterogeneousDataSource;
    }

    public String getXtableMeaningDataSource() {
        return xtableMeaningDataSource;
    }

    public void setXtableMeaningDataSource(String xtableMeaningDataSource) {
        this.xtableMeaningDataSource = xtableMeaningDataSource;
    }
}
