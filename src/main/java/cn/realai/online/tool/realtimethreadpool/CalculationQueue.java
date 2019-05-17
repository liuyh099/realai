package cn.realai.online.tool.realtimethreadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.realai.online.common.Constant;

public class CalculationQueue {

    public final static ThreadPoolExecutor queue = new ThreadPoolExecutor(Constant.COUNT_SYNCHRONIZE_CALCULATION, 
    		Constant.COUNT_SYNCHRONIZE_CALCULATION, 
    		Constant.TIMEOUT_CALCULATION,
            TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
            new ThreadPoolExecutor.DiscardOldestPolicy());

}
