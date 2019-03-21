package cn.realai.online.core.controller;

import java.util.Map;

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

    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(value = "/forecast", method = RequestMethod.GET)
    public String getForecastResult(String param, Long serviceId) {
        try {
            if (serviceId == null) {
                logger.warn("线上预测传入参数错误, param{}, serviceId{}", param, serviceId);
                return ResultUtils.generateResultStr(ResultCode.DATA_ERROR, ResultMessage.OPT_FAILURE.getMsg(), null);
            }
            Map<String, Object> map = JSON.parseObject(param, Map.class);
            String ret = realTimeBussiness.getForecastResult(map, serviceId);
            return ResultUtils.generateResultStr(ResultCode.SUCCESS, ResultMessage.OPT_SUCCESS.getMsg(), ret);
        } catch (Exception e) {
            logger.error("线上预测发生异常", e);
            return ResultUtils.generateResultStr(ResultCode.DATA_ERROR, ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }

}
