package cn.realai.online.userandperm.bo;

import cn.realai.online.userandperm.entity.User;

public class UserBO extends User {

    private String group;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
