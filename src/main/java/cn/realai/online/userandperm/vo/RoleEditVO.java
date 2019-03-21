package cn.realai.online.userandperm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel
public class RoleEditVO {

    @NotNull(message = "角色Id不能为空")
    @ApiModelProperty(value = "id")
    private Long id;

    //角色名称
    @NotBlank(message = "角色名称不能为空")
    @Length(max = 20, min = 1, message = "角色名称为1-20个字节")
    @ApiModelProperty(value = "角色名称")
    private String name;
    //创建备注
    @ApiModelProperty(value = "备注")
    private String notes;

    @ApiModelProperty(value = "选择的菜单ID集合")
    private List<Long> menu;

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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Long> getMenu() {
        return menu;
    }

    public void setMenu(List<Long> menu) {
        this.menu = menu;
    }
}
