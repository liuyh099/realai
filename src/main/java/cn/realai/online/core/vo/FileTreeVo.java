package cn.realai.online.core.vo;

import java.util.List;

public class FileTreeVo {

    private String key;

    private String name;

    private List<FileTreeVo> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getDir(){
        if(this.children!=null){
            return true;
        }
        return false;
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
