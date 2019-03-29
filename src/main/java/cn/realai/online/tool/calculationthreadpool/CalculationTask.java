package cn.realai.online.tool.calculationthreadpool;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.realai.online.calculation.TrainService;
import cn.realai.online.util.SpringContextUtils;

public class CalculationTask implements Callable<String> {
	
	private static Logger logger = LoggerFactory.getLogger(CalculationTask.class);

    //实时数据
    private String jsonData;

    //任务
    private FutureTask<String> futureTask;

    //模型code
    private Long modelId;

    public CalculationTask(String jsonData, long modelId) {
        this.jsonData = jsonData;
        this.modelId = modelId;
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
    	String uuId = UUID.randomUUID().toString();
    	logger.info("CalculationTask call. 线上任务开始. jsonData{}, modelId{}, uuId{}", jsonData, modelId, uuId);
    	TrainService trainService = SpringContextUtils.getBean(TrainService.class);
    	String ret = trainService.realTimeForecast(modelId, jsonData);
    	logger.info("CalculationTask call. 线上任务获取返回值. uuId{}", uuId);
        return ret;
    }

}
