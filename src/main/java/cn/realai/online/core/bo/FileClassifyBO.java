package cn.realai.online.core.bo;

import org.apache.commons.lang3.StringUtils;

public class FileClassifyBO {
    /*父*/
    private String parent;
    /*自己*/
    private String self;
    /*子*/
    private String child;

    /*前缀*/
    private String prefix;

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public FileClassifyBO(String parent, String self, String child, String prefix) {
        this.parent = parent;
        this.self = self;
        this.child = child;
        this.prefix = prefix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileClassifyBO fileClassify = (FileClassifyBO) o;
        if (StringUtils.equals(fileClassify.getSelf(), this.getSelf()) &&
                StringUtils.equals(fileClassify.getChild(), this.getChild()) &&
                StringUtils.equals(fileClassify.getPrefix(), this.getPrefix())) {

            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        int result =  (self + child + prefix).hashCode();
        return result;
    }

}
