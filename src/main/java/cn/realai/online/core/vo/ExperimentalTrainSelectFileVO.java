package cn.realai.online.core.vo;

import cn.realai.online.common.validation.annotation.SimultaneousNotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@ApiModel
public class ExperimentalTrainSelectFileVO {

    @ApiModelProperty(value = "实验id", required = false, example = "新增非必填，更新需要")
    private Long id;

    //主键id
    @ApiModelProperty(value = "服务ID", required = true)
    @NotNull(message = "服务不能为空")
    private Long serverId;

    @ApiModelProperty(value = "实验名称", required = true)
    @NotBlank(message = "实验名称不能为空")
    private String name;

    @ApiModelProperty(value = "y表数据源", required = true)
    @NotBlank(message = "y表数据源不能为空")
    @Pattern( regexp = ".+(.csv)$",message = "文件格式不正确")
    private String ytableDataSource;

    //x表同质数据源
    @ApiModelProperty(value = "模型X表同质数据源")
    @SimultaneousNotNull(checkCount = 2, message = "模型X表同质数据源和模型X表异质数据源必须选择一个")
    @Pattern( regexp = ".+(.csv)$",message = "文件格式不正确")
    private String xtableHomogeneousDataSource;

    //x表异质数据源
    @ApiModelProperty(value = "模型X表异质数据源")
    @SimultaneousNotNull(checkCount = 2, message = "模型X表同质数据源和模型X表异质数据源必须选择一个")
    @Pattern( regexp = ".+(.csv)$",message = "文件格式不正确")
    private String xtableHeterogeneousDataSource;

    //x轴含义（变量名解释）
    @ApiModelProperty(value = "模型X表变量名称映射表")
    @Pattern( regexp = ".+(.csv)$",message = "文件格式不正确")
    private String xtableMeaningDataSource;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
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
