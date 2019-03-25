package cn.realai.online.core.vo;

import java.util.List;

public class FileTreeVo {

    private String key;

    private String title;

    private List<FileTreeVo> children;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<FileTreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<FileTreeVo> children) {
        this.children = children;
    }
}
