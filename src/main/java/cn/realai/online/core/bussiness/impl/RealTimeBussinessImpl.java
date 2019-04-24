package cn.realai.online.core.bussiness.impl;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.realai.online.core.bussiness.RealTimeBussiness;
import cn.realai.online.core.entity.Model;
import cn.realai.online.core.query.realtime.RealTimeData;
import cn.realai.online.core.service.ModelService;
import cn.realai.online.core.service.ServiceService;
import cn.realai.online.common.Constant;
import cn.realai.online.tool.calculationthreadpool.CalculationQueue;
import cn.realai.online.tool.calculationthreadpool.CalculationTask;

@Service
public class RealTimeBussinessImpl implements RealTimeBussiness {

    private static Logger logger = LoggerFactory.getLogger(RealTimeBussinessImpl.class);

    @Autowired
    private ServiceService serviceService;
    
    @Autowired
    private ModelService modelService;

    @Override
    public String getForecastResult(RealTimeData realTimeData) throws Exception {

    	if (!serviceService.checkService(realTimeData.getServiceId())) {
        	return "EXPIRED";
        }
    	
        Model model = modelService.selectOnlineModelByServiceId(realTimeData.getServiceId());
        if (model == null) {
        	return "NO_RELEASE";
        }
        
        realTimeData.setModelId(model.getExperimentId());
        realTimeData.setServiceId(model.getExperimentId());//只是测试，要去掉
        
        //放到队列里去python计算
        CalculationTask ct = new CalculationTask(realTimeData);
        FutureTask<String> ft = new FutureTask<String>(ct);
        CalculationQueue.queue.submit(ft);
        String ret = null;
        try {
			ret = ft.get(Constant.TIMEOUT_CALCULATION, TimeUnit.SECONDS);
		} catch (TimeoutException e1) {
			ft.cancel(true);
            logger.error("RealTimeBussinessImpl getForecastResult timeoutException");
            return "TIME_OUT";
		} catch (Exception e) {
            throw e;
        }
        return ret;
    }

}
