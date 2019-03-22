package cn.realai.online.core.bo;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ExperimentalTrainDoubleCreateBO {

    @NotNull(message = "实验ID不能为空")
    private Long trainId;

    private List<Long> variableIdList;

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public List<Long> getVariableIdList() {
        return variableIdList;
    }

    public void setVariableIdList(List<Long> variableIdList) {
        this.variableIdList = variableIdList;
    }
}
