package cn.realai.online.core.bo;

import cn.realai.online.core.entity.Model;
import io.swagger.models.auth.In;

public class ModelBO extends Model {

    private Integer status;

    private Integer tuningType;

    public Integer getTuningType() {
        return tuningType;
    }

    public void setTuningType(Integer tuningType) {
        this.tuningType = tuningType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
