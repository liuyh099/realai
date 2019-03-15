package cn.realai.online.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.realai.online.core.bussiness.ExperimentBussiness;
import cn.realai.online.common.base.BaseController;
import cn.realai.online.common.vo.ResultCode;
import cn.realai.online.common.vo.ResultMessage;
import cn.realai.online.common.vo.ResultUtils;

@RestController
@RequestMapping("/experiment")
public class ExperimentController extends BaseController{
	
	private static Logger logger = LoggerFactory.getLogger(ExperimentController.class);

	@Autowired
	private ExperimentBussiness experimentBussiness;
	
	@RequestMapping(value = "/modeling/{id}", method = RequestMethod.POST)
	public String modeling(@PathVariable long id) {
		try {
			int ret = experimentBussiness.modeling(id);
			return ResultUtils.generateResultStr(ResultCode.SUCCESS, ResultMessage.OPT_SUCCESS.getMsg(), ret);
		} catch (Exception e) {
			logger.warn("线上预测发生异常", e);
			return ResultUtils.generateResultStr(ResultCode.DATA_ERROR, ResultMessage.OPT_FAILURE.getMsg(), null);
		}
	}
	
}
