package cn.realai.online.userandperm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

@ApiModel
public class RoleAddVO {

    //角色名称
    @NotBlank(message = "角色名称不能为空")
    @Length(max = 20, min = 1, message = "角色名称为1-20个字节")
    @ApiModelProperty(value = "角色名称")
    private String name;
    //创建备注
    @ApiModelProperty(value = "备注")
    private String notes;

    @ApiModelProperty(value = "半选的菜单ID集合")
    private List<Long> halfMenu;

    @ApiModelProperty(value = "全选的菜单ID集合")
    private List<Long> checkMenu;

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

    public List<Long> getHalfMenu() {
        return halfMenu;
    }

    public void setHalfMenu(List<Long> halfMenu) {
        this.halfMenu = halfMenu;
    }

    public List<Long> getCheckMenu() {
        return checkMenu;
    }

    public void setCheckMenu(List<Long> checkMenu) {
        this.checkMenu = checkMenu;
    }
}
