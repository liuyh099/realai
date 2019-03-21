package cn.realai.online.calculation.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.realai.online.calculation.TrainService;
import cn.realai.online.calculation.requestbo.PreprocessRequestBO;
import cn.realai.online.common.Constant;
import cn.realai.online.common.config.Config;
import cn.realai.online.core.entity.Experiment;
import cn.realai.online.util.ConvertJavaBean;
import cn.realai.online.util.HttpUtil;

@Service
public class TrainServiceImpl implements TrainService {

    @Autowired
    private Config config;

    /*
     * 预处理
     */
    @Override
    public void preprocess(Experiment experiment) {
        PreprocessRequestBO prbo = new PreprocessRequestBO(Constant.TASK_PREPROCESS);
        ConvertJavaBean.convertJavaBean(prbo, experiment);
        String url = config.getUrl();
        String ret = HttpUtil.postRequest(url, JSON.toJSONString(prbo), String.class);
        if (ret == null) {
            throw new RuntimeException("TrainServiceImpl preprocess. 调用python预处理接口失败. prbo{}" + JSON.toJSONString(prbo));
        }
    }

    /*
     * 训练
     */
    @Override
    public int training(Experiment experiment) {
        return 0;
    }

}
