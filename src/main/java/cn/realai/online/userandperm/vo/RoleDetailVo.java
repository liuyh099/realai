package cn.realai.online.userandperm.vo;

import java.util.List;

public class RoleDetailVo {
    //角色名称
    private String name;
    //创建时间
    private Long createTime;
    //创建备注
    private String notes;

    private String createUser;

    private List<MenuTreeNodeVo> menu;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public List<MenuTreeNodeVo> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuTreeNodeVo> menu) {
        this.menu = menu;
    }
}
