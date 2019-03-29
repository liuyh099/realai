package cn.realai.online.tool.calculationthreadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import cn.realai.online.calculation.TrainService;
import cn.realai.online.util.SpringContextUtils;

public class CalculationTask implements Callable<String> {

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
    	TrainService trainService = SpringContextUtils.getBean(TrainService.class);
    	String ret = trainService.realTimeForecast(modelId, jsonData);
        return ret;
    }

}
