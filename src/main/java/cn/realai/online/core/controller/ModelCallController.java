package cn.realai.online.core.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import cn.realai.online.core.bo.TrainResultRedisKey;
import cn.realai.online.core.bo.model.OfflineBatchRequest;
import cn.realai.online.core.bo.model.DailyBatchRequest;
import cn.realai.online.core.bo.model.ModelRequest;
import cn.realai.online.core.bussiness.ModelCallBussiness;
import cn.realai.online.core.entity.DeployInfo;
import cn.realai.online.util.FileUtil;
import cn.realai.online.common.Constant;
import cn.realai.online.common.base.BaseController;
import cn.realai.online.common.vo.Result;
import cn.realai.online.common.vo.ResultCode;
import cn.realai.online.common.vo.ResultMessage;
import cn.realai.online.common.vo.ResultUtils;

/**
 * python模型部分调用Java的controller
 *
 * @author lyh
 */
@RestController
@RequestMapping("/modelCall")
public class ModelCallController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(ModelCallController.class);

    @Autowired
    private ModelCallBussiness modelCallBussiness;

    /**
     * 线上部署
     * @return
     */
    @RequestMapping(value = "/deploy", method = RequestMethod.POST)
    public String deployServiceModel(String path) {
    	List<String> strList = FileUtil.readFile(path);
		if (strList == null || strList.size() == 0) {//服务密钥不存在或不合法
			return ResultUtils.generateResultStr(ResultCode.PARAM_ERROR, ResultMessage.OPT_FAILURE.getMsg("密钥文件不存在"), null);
		}

		String deployInfoStr = strList.get(0);
		DeployInfo deployInfo = null;
		try {
			deployInfo = JSON.parseObject(deployInfoStr, DeployInfo.class);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtils.generateResultStr(ResultCode.PARAM_ERROR, ResultMessage.OPT_FAILURE.getMsg("密钥信息格式有误"), null);
		}
		deployInfo.setHistoryInfo(deployInfoStr);
    	int ret = modelCallBussiness.deployServiceModel(deployInfo);
    	if (ret == -1) {
    		return ResultUtils.generateResultStr(ResultCode.PARAM_ERROR, ResultMessage.OPT_FAILURE.getMsg("密钥验证失败，密钥信息有误"), null);
    	}
    	if (ret == 1) {
    		return ResultUtils.generateResultStr(ResultCode.PYTHON_SUCCESS, ResultMessage.OPT_SUCCESS.getMsg(), null);
    	}
    	return ResultUtils.generateResultStr(ResultCode.DATA_ERROR, ResultMessage.OPT_FAILURE.getMsg(), null);
    }
    
    /**
     * 跑批任务
     * @return
     */
    @RequestMapping(value = "/batchTask", method = RequestMethod.POST)
    public String runBatchTask(@RequestBody String param) {
    	logger.info("ModelCallController runBatchOffline. 离线跑批. param{}", JSON.toJSONString(param));
    	OfflineBatchRequest obrs = JSON.parseObject(param, OfflineBatchRequest.class);
    	if (obrs.getCode() != 200) {
    		modelCallBussiness.batchErrorDealWith(obrs.getBatchId(), obrs.getMsg());
    		return ResultUtils.generateResultStr(ResultCode.PYTHON_SUCCESS, ResultMessage.OPT_SUCCESS.getMsg(), null);
    	}
    	
    	Long experimentId = obrs.getModelId();
        String type = obrs.getType();
        String redisKey = obrs.getRedisKey();
        String downUrl = obrs.getDownUrl();
        Long batchId = obrs.getBatchId();
        modelCallBussiness.runBatchOffline(experimentId, redisKey, type, downUrl, batchId, obrs.getDone());
        return ResultUtils.generateResultStr(ResultCode.PYTHON_SUCCESS, ResultMessage.OPT_SUCCESS.getMsg(), null);
    }
    
    /**
     * 每天晚上的跑批任务
     *
     * @return
     */
    @RequestMapping(value = "/dailyBatch", method = RequestMethod.POST)
    public String runBatchDaily(@RequestBody String dbrListJson) {
    	logger.info("ModelCallController runBatchDaily. 每日跑批批次任务. dbrListJson{}", dbrListJson);
    	List<DailyBatchRequest> dbrList = JSON.parseArray(dbrListJson, DailyBatchRequest.class);
    	for (DailyBatchRequest dbr : dbrList) {
    		if (dbr.getBatchDate() == null) {
    			logger.info("ModelCallController runBatchDaily. 每日跑批批次任务,批次日期不能为空. dbr{}", JSON.toJSONString(dbr));
    			return ResultUtils.generateResultStr(ResultCode.PARAM_ERROR, ResultMessage.PARAM_ERORR.getMsg("批次日期不能为空"), null);
    		}
    		if (dbr.getModelId() == null) {
    			logger.info("ModelCallController runBatchDaily. 每日跑批批次任务,模型id不能为空. dbr{}", JSON.toJSONString(dbr));
    			return ResultUtils.generateResultStr(ResultCode.PARAM_ERROR, ResultMessage.PARAM_ERORR.getMsg("模型id不能为空"), null);
    		}
    		if (dbr.getYtable() == null) {
    			logger.info("ModelCallController runBatchDaily. 每日跑批批次任务,Y轴数据源不能为空. dbr{}", JSON.toJSONString(dbr));
    			return ResultUtils.generateResultStr(ResultCode.PARAM_ERROR, ResultMessage.PARAM_ERORR.getMsg("Y轴数据源不能为空"), null);
    		}
    		if (dbr.getXtableHetro() == null && dbr.getXtableHomo() == null) {
    			logger.info("ModelCallController runBatchDaily. 每日跑批批次任务,X轴数据源不能为空. dbr{}", JSON.toJSONString(dbr));
    			return ResultUtils.generateResultStr(ResultCode.PARAM_ERROR, ResultMessage.PARAM_ERORR.getMsg("X轴数据源不能为空"), null);
    		}
    	}
    	modelCallBussiness.runBatchDaily(dbrList);
        return ResultUtils.generateResultStr(ResultCode.PYTHON_SUCCESS, ResultMessage.OPT_SUCCESS.getMsg(), null);
    }

    /**
     * 模型训练预处理
     *
     * @return
     */
    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    public Result callback(@RequestBody String param) {
    	logger.info("ModelCallController callback. param{}, time{}", JSON.toJSONString(param), System.currentTimeMillis() + "");
    	System.out.println(getRequestIp());
    	try {
    		ModelRequest request = JSON.parseObject(param, ModelRequest.class);
            Integer code = request.getCode();
            Long experimentId = request.getExperimentId();
            String task = request.getTask();
            String msg = request.getMsg();
            String data = request.getData();
            Map<String, String> map = JSON.parseObject(data, Map.class);
            //参数验证
            if (code == null) {
                return new Result(ResultCode.PARAM_ERROR.getCode(), ResultMessage.PARAM_ERORR.getMsg("code不能为null"), null);
            }
            if (experimentId == null || experimentId == 0) {
                logger.error("ModelCallController callback. python回调experimentId为null");
                return new Result(ResultCode.PARAM_ERROR.getCode(), ResultMessage.PARAM_ERORR.getMsg("experimentId不能为null或0"), null);
            }
            if (task == null || "".equals(task)) {
                logger.error("ModelCallController callback. python回调task为null");
                return new Result(ResultCode.PARAM_ERROR.getCode(), ResultMessage.PARAM_ERORR.getMsg("task不能为null或空"), null);
            }

            //解析处理结果
            if (code != 200) { //如果处理不成功
                modelCallBussiness.trainErrorDealWith(experimentId, msg, task);
            } else { //处理成功，根据task判断处理方式
                if (Constant.COMMAND_PREPROCESS.equals(task)) { //预处理
                    String redisKey = map.get("variableData");
                    modelCallBussiness.preprocessCallback(experimentId, redisKey);
                } else if (Constant.COMMAND_TRAIN.equals(task) || Constant.COMMAND_SECOND_TRAIN.equals(task)) { //训练
                    int stage = request.getStage();
                	TrainResultRedisKey redisKey = JSON.parseObject(data, TrainResultRedisKey.class);
                    modelCallBussiness.trainCallback(experimentId, redisKey, stage);
                } else {
                    logger.warn("ModelCallController callback. python回调task为null");
                    modelCallBussiness.trainErrorDealWith(experimentId, msg, task);
                    return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
                }
            }
            return new Result(ResultCode.PYTHON_SUCCESS.getCode(), ResultMessage.OPT_SUCCESS.getMsg(), null);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return new Result(ResultCode.DATA_ERROR.getCode(), ResultMessage.OPT_FAILURE.getMsg(), null);
    }

}
