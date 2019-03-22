package cn.realai.online.userandperm.bo;

import java.util.List;

public class MenuTreeNodeBO {

    private Long id;

    private String name;

    private String icon;

    private String url;

    private Boolean check;

    private List<MenuTreeNodeBO> children;

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<MenuTreeNodeBO> getChildren() {
        return children;
    }

    public void setChildren(List<MenuTreeNodeBO> children) {
        this.children = children;
    }
}
