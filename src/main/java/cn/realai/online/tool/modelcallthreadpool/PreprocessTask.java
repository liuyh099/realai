package cn.realai.online.tool.modelcallthreadpool;

import java.util.List;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;

import cn.realai.online.core.entity.VariableData;
import cn.realai.online.core.service.VariableDataService;

/**
 * 预处理回调任务
 * @author lyh
 */
public class PreprocessTask implements Runnable{

	@Override
	public void run() {
		//读取预处理结果配置文件
		String vdStr = "";
		
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		VariableDataService variableDataService = wac.getBean("variableDataService", VariableDataService.class);
		
		
		//解析
		List<VariableData> vdList = JSON.parseArray(vdStr, VariableData.class);
		variableDataService.insertVariableDataList(vdList);
		
		//修改
	}

}
