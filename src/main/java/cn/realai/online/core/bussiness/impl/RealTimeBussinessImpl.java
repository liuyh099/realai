package cn.realai.online.core.bussiness.impl;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.realai.online.core.bussiness.RealTimeBussiness;
import cn.realai.online.core.entity.DeployInfo;
import cn.realai.online.core.entity.Model;
import cn.realai.online.core.query.realtime.RealTimeData;
import cn.realai.online.core.service.DeployInfoService;
import cn.realai.online.core.service.ModelService;
import cn.realai.online.core.service.ServiceService;
import cn.realai.online.tool.realtimethreadpool.CalculationQueue;
import cn.realai.online.tool.realtimethreadpool.CalculationTask;
import cn.realai.online.common.Constant;  

@Service
public class RealTimeBussinessImpl implements RealTimeBussiness {

    private static Logger logger = LoggerFactory.getLogger(RealTimeBussinessImpl.class);

    @Autowired
    private ServiceService serviceService;
    
    @Autowired
    private ModelService modelService;
    
    @Autowired
    private DeployInfoService deployInfoService;

    @Override
    public String getForecastResult(RealTimeData realTimeData) throws Exception {

    	Long experimentId = getexperimentId(realTimeData.getServiceId(), realTimeData.getType());
    	
    	if (experimentId < 0) {
    		return experimentId + "";
    	}
    	
        realTimeData.setModelId(experimentId);
        realTimeData.setServiceId(experimentId);//只是测试，要去掉
        
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
            return "-4";
		} catch (Exception e) {
            throw e;
        }
        return ret;
    }

    private Long getexperimentId(Long serviceId, int type) {
    	if (type == RealTimeData.DEPLOY_TYPE_ONLINE) {
    		if (!serviceService.checkService(serviceId)) {
            	return -2L;
            }
            Model model = modelService.selectOnlineModel(serviceId, null);
            if (model == null) {
            	return -3L;
            }
            return model.getExperimentId();
    	} else if (type == RealTimeData.DEPLOY_TYPE_ALONE) {
    		DeployInfo deployInfo = deployInfoService.selectDeployInfoById(serviceId);
    		if (deployInfoService.checkgetSecretKey(deployInfo.getSecretKey())) {
    			return -2L;
    		}
    		return deployInfo.getExperimentId();
    	}
    	
    	return -1L;
    }
    
}
