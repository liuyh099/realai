package cn.realai.online.core.bussiness.impl;

import java.util.Map;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.realai.online.core.bo.ExperimentBO;
import cn.realai.online.core.bo.ServiceBO;
import cn.realai.online.core.bussiness.RealTimeBussiness;
import cn.realai.online.core.service.ExperimentService;
import cn.realai.online.core.service.ServiceService;
import cn.realai.online.common.Constant;
import cn.realai.online.tool.calculationthreadpool.CalculationQueue;
import cn.realai.online.tool.calculationthreadpool.CalculationTask;

@Service
public class RealTimeBussinessImpl implements RealTimeBussiness {

    private static Logger logger = LoggerFactory.getLogger(RealTimeBussinessImpl.class);

    @Autowired
    private ExperimentService experimentService;

    @Autowired
    private ServiceService serviceService;

    @Override
    public String getForecastResult(String jsonData, long serviceId) {

        //判断访问服务是否是已经部署并且是在线模式的
        //ServiceBO servicebo = serviceService.selectServiceById(serviceId);

        //ExperimentBO experimentbo = experimentService.selectExperimentById(servicebo.getOnlineExperiment());

        //放到队列里去python计算
        CalculationTask ct = new CalculationTask(jsonData, 1L);//experimentbo.getId());
        FutureTask<String> ft = new FutureTask<String>(ct);
        CalculationQueue.queue.submit(ft);
        String ret = null;
        try {
            ret = ft.get(Constant.TIMEOUT_CALCULATION, TimeUnit.SECONDS);
        } catch (Exception e) {
            ft.cancel(true);
            logger.error("RealTimeBussinessImpl getForecastResult timeoutException, serviceId{}, jsonData{}",
                    serviceId, jsonData);
            return null;
        }
        return ret;
    }

}
