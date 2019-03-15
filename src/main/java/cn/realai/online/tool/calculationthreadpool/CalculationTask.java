package cn.realai.online.tool.calculationthreadpool;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class CalculationTask implements Callable<String>{

	//实时数据
	private Map<String, Object> map;
	
	//任务
	private FutureTask<String> futureTask;
	
	//模型code
	private long serviceId;
	
	public CalculationTask(Map<String, Object> map, long serviceId) {
		this.map = map;
		this.serviceId = serviceId;
	}
	
	public FutureTask<String> getFutureTask() {
		return futureTask;
	}

	public void setFutureTask(FutureTask<String> futureTask) {
		this.futureTask = futureTask;
	}

	/*
	 * 调用Python做实时计算
	 */
	@Override
	public String call() throws Exception {
		
		return null;
	}

}
