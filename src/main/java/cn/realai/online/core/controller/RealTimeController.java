package cn.realai.online.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cn.realai.online.common.vo.ResultCode;
import cn.realai.online.common.vo.ResultMessage;
import cn.realai.online.common.vo.ResultUtils;
import cn.realai.online.core.bussiness.RealTimeBussiness;
import cn.realai.online.core.query.realtime.RealTimeData;

/**
 * 实时返回行方请求的类
 * @author lyh
 */
@Controller
@RequestMapping("/realTime")
public class RealTimeController {

    private static Logger logger = LoggerFactory.getLogger(RealTimeController.class);

    @Autowired
    public RealTimeBussiness realTimeBussiness;

    @ResponseBody
    @RequestMapping(value = "/forecast", method = RequestMethod.POST)
    public String getForecastResult(String realTimeJson) {
    	logger.info("RealTimeController getForecastResult.线上预测请求开始, realTimeJson{}", realTimeJson);
    	try {
    		RealTimeData realTimeData = JSON.parseObject(realTimeJson, RealTimeData.class);
            if (realTimeData.getServiceId() == null) {
                logger.warn("线上预测传入参数错误, serviceId不能为空");
                return ResultUtils.generateResultStr(ResultCode.DATA_ERROR, ResultMessage.OPT_FAILURE.getMsg(), null);
            }
            String ret = realTimeBussiness.getForecastResult(realTimeData);
            if ("EXPIRED".equals(ret)) {
            	logger.error("RealTimeController getForecastResult.服务已过期.");
            	return ResultUtils.generateResultStr(ResultCode.REAL_TIME_EXPIRED, ResultMessage.OPT_FAILURE.getMsg("服务已过期"), null);
            }
            if ("NO_RELEASE".equals(ret)) {
            	logger.error("RealTimeController getForecastResult.服务没有进行线上部署.");
            	return ResultUtils.generateResultStr(ResultCode.REAL_TIME_NO_RELEASE, ResultMessage.OPT_FAILURE.getMsg("服务没有进行线上部署"), null);
            }
            if ("TIME_OUT".equals(ret)) {
            	logger.error("RealTimeController getForecastResult.线上预测失败,服务请求python计算超时，请稍后重试.");
            	return ResultUtils.generateResultStr(ResultCode.REAL_TIME_TIME_OUT, ResultMessage.OPT_FAILURE.getMsg("服务请求python计算超时，请稍后重试"), null);
            }
            logger.info("RealTimeController getForecastResult.线上预测成功");
            return ResultUtils.generateResultStr(ResultCode.SUCCESS, ResultMessage.OPT_SUCCESS.getMsg(), ret);
        } catch (Exception e) {
        	logger.error("RealTimeController getForecastResult.线上预测失败");
            return ResultUtils.generateResultStr(ResultCode.REAL_TIME_ERROR, ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }

}
