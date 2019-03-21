package cn.realai.online.core.controller;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import cn.realai.online.core.bo.DailyBatchRequestStructure;
import cn.realai.online.core.bo.ModelRequestStructure;
import cn.realai.online.core.bo.TrainResultRedisKey;
import cn.realai.online.core.bussiness.ModelCallBussiness;
import cn.realai.online.common.Constant;
import cn.realai.online.common.vo.Result;
import cn.realai.online.common.vo.ResultCode;
import cn.realai.online.common.vo.ResultMessage;
import cn.realai.online.common.vo.ResultUtils;
import cn.realai.online.util.DateUtil;

/**
 * python模型部分调用Java的controller
 *
 * @author lyh
 */
@RestController
@RequestMapping("/modelCall")
public class ModelCallController {

    private static Logger logger = LoggerFactory.getLogger(ModelCallController.class);

    @Autowired
    private ModelCallBussiness modelCallBussiness;

    /**
     * 每天晚上的跑批任务
     *
     * @return
     */
    @RequestMapping(value = "/dailyBatch", method = RequestMethod.POST)
    public String runBatchDaily(@RequestBody String param) {
        DailyBatchRequestStructure dbrs = JSON.parseObject(param, DailyBatchRequestStructure.class);
        Long experimentId = dbrs.getExperimentId();
        String redisKey = dbrs.getRedisKey();
        //获取当天日期
        String date = DateUtil.dateToString(new Date(), DateUtil.ymd);

        if (redisKey != null || "".equals(redisKey) || experimentId == null) {
            logger.error("ModelCallController runBatchDaily:date定时任务发生错误，参数格式错误. date{}, "
                    + "experimentId{}, fileAddress{}", date, experimentId, redisKey);
            return ResultUtils.generateResultStr(ResultCode.PARAM_ERROR, ResultMessage.PARAM_ERORR.getMsg("文件地址不能为空或null"), null);
        }
        modelCallBussiness.runBatchDaily(experimentId, redisKey);
        return ResultUtils.generateResultStr(ResultCode.SUCCESS, ResultMessage.OPT_SUCCESS.getMsg(), null);
    }

    /**
     * 模型训练预处理
     *
     * @return
     */
    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    public Result callback(@RequestBody String param) {
        ModelRequestStructure request = JSON.parseObject(param, ModelRequestStructure.class);
        Integer code = request.getCode();
        Long experimentId = request.getExperimentId();
        String task = request.getTask();
        String msg = request.getMsg();
        String data = request.getData();
        Map<String, String> map = JSON.parseObject(data, Map.class);
        //参数验证
        if (code == null) {
            modelCallBussiness.errorDealWith(experimentId, "");
            return new Result(ResultCode.PARAM_ERROR.getCode(), ResultMessage.PARAM_ERORR.getMsg("code不能为null"), null);
        }
        if (experimentId == null || experimentId == 0) {
            logger.error("ModelCallController callback. python回调experimentId为null");
            return new Result(ResultCode.PARAM_ERROR.getCode(), ResultMessage.PARAM_ERORR.getMsg("experimentId不能为null或0"), null);
        }
        if (task == null || "".equals(task)) {
            logger.error("ModelCallController callback. python回调task为null");
            modelCallBussiness.errorDealWith(experimentId, "");
            return new Result(ResultCode.PARAM_ERROR.getCode(), ResultMessage.PARAM_ERORR.getMsg("task不能为null或空"), null);
        }

        //解析处理结果
        if (code != 200) { //如果处理不成功
            modelCallBussiness.errorDealWith(experimentId, msg);
        } else { //处理成功，根据task判断处理方式
            if (Constant.TASK_PREPROCESS.equals(task)) { //预处理
                String redisKey = map.get("variableData");
                modelCallBussiness.preprocessCallback(experimentId, redisKey);
            } else if (Constant.TASK_TRAIN.equals(task)) { //训练
                TrainResultRedisKey redisKey = JSON.parseObject(data, TrainResultRedisKey.class);
                modelCallBussiness.trainCallback(experimentId, redisKey);
            } else {
                logger.warn("ModelCallController callback. python回调task为null");
            }
        }

        return new Result(ResultCode.SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
    }

}
