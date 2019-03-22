package cn.realai.online.core.bo;

import cn.realai.online.core.entity.Service;

public class ServiceBO extends Service {


    /**
     * 是否可续期  true: 可续期 false: 不可续期
     */
    private boolean renewable;


    public boolean isRenewable() {
        return renewable;
    }

    public void setRenewable(boolean renewable) {
        this.renewable = renewable;
    }

}
