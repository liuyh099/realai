package cn.realai.online.core.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import cn.realai.online.core.bo.TrainResultRedisKey;
import cn.realai.online.core.bussiness.ModelCallBussiness;
import cn.realai.online.common.Constant;
import cn.realai.online.common.vo.ResultCode;
import cn.realai.online.common.vo.ResultMessage;
import cn.realai.online.common.vo.ResultUtils;
import cn.realai.online.util.DateUtil;

/**
 * python模型部分调用Java的controller
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
	 * @return
	 */
	@RequestMapping(value = "/dailyBatch", method = RequestMethod.POST)
	public String runBatchDaily(Long experimentId, String fileAddress) {
		//获取当天日期
		String date = DateUtil.dateToString(new Date(), DateUtil.ymd); 
		
		if (fileAddress != null || "".equals(fileAddress) || experimentId == null) {
			logger.error("ModelCallController runBatchDaily:date定时任务发生错误，参数格式错误. date{}, "
					+ "experimentId{}, fileAddress{}", date, experimentId, fileAddress);
			return ResultUtils.generateResultStr(ResultCode.PARAM_ERROR, ResultMessage.PARAM_ERORR.getMsg("文件地址不能为空或null"), null);
		}
		modelCallBussiness.runBatchDaily(experimentId, fileAddress);
		return ResultUtils.generateResultStr(ResultCode.SUCCESS, ResultMessage.OPT_SUCCESS.getMsg(), null);
	}
	
	/**
	 * 模型训练预处理
	 * @return
	 */
	public String callback(Integer code, Long experimentId, String task, String msg, String data) {
		//参数验证
		if (code == null) {
			
		} 
		if (experimentId == null || experimentId == 0) {
			
		}
		if (task == null || "".equals(task)) {
			
		}
		
		//解析处理结果
		if (code != 200) { //如果处理不成功
			
		}
		
		if (Constant.TASK_PREPROCESS.equals(task)) { //预处理
			modelCallBussiness.preprocessCallback(experimentId, data);
		} else if (Constant.TASK_TRAIN.equals(task)) { //训练
			TrainResultRedisKey redisKey = JSON.parseObject(data, TrainResultRedisKey.class);
			modelCallBussiness.trainCallback(experimentId, redisKey);
		} else {
			
		}
		
		return "";
	}
	
}
