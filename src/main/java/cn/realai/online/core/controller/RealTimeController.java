package cn.realai.online.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.realai.online.common.vo.ResultCode;
import cn.realai.online.common.vo.ResultMessage;
import cn.realai.online.common.vo.ResultUtils;
import cn.realai.online.core.bussiness.RealTimeBussiness;

/**
 * 实时返回行方请求的类
 *
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
    public String getForecastResult(String param, Long serviceId) {
    	logger.info("RealTimeController getForecastResult.线上预测请求开始, param{}, serviceId{}", param, serviceId);
    	try {
            if (serviceId == null) {
                logger.warn("线上预测传入参数错误, param{}, serviceId{}", param, serviceId);
                return ResultUtils.generateResultStr(ResultCode.DATA_ERROR, ResultMessage.OPT_FAILURE.getMsg(), null);
            }
            String ret = realTimeBussiness.getForecastResult(param, serviceId);
            if (ret == null) {
            	logger.error("RealTimeController getForecastResult.线上预测失败,预测值为null, param{}, serviceId{}", param, serviceId);
            	return ResultUtils.generateResultStr(ResultCode.DATA_ERROR, ResultMessage.PROGRAM_EXCEPTION.getMsg(), null);
            }
            logger.info("RealTimeController getForecastResult.线上预测成功, param{}, serviceId{}, ret{}", param, serviceId, ret);
            return ResultUtils.generateResultStr(ResultCode.SUCCESS, ResultMessage.OPT_SUCCESS.getMsg(), ret);
        } catch (Exception e) {
        	logger.error("RealTimeController getForecastResult.线上预测失败, param{}, serviceId{}", param, serviceId);
            return ResultUtils.generateResultStr(ResultCode.DATA_ERROR, ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }

}
