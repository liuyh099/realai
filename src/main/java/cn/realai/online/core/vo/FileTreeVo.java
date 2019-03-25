package cn.realai.online.core.vo;

import java.util.List;

public class FileTreeVo {
    private String name;

    private List<FileTreeVo> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FileTreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<FileTreeVo> children) {
        this.children = children;
    }

}
