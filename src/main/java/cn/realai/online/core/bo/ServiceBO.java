package cn.realai.online.core.bo;

import cn.realai.online.core.entity.Service;

public class ServiceBO extends Service {


    /**
     * 是否可续期  true: 可续期 false: 不可续期
     */
    private boolean renewable;

    /**
     * 是否续期
     */
    private boolean renewal = false;

    public boolean isRenewal() {
        return renewal;
    }

    public void setRenewal(boolean renewal) {
        this.renewal = renewal;
    }

    public boolean isRenewable() {
        return renewable;
    }

    public void setRenewable(boolean renewable) {
        this.renewable = renewable;
    }

}
