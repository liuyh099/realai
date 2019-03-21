package cn.realai.online.calculation;

import cn.realai.online.core.entity.Experiment;
import cn.realai.online.core.entity.MLock;

public interface TrainService {

    /**
     * 预处理
     *
     * @param experimentBO
     */
    void preprocess(Experiment experiment);

    int training(Experiment experiment);

}
