package cn.realai.online.tool.calculationthreadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CalculationQueue {

	public final static ThreadPoolExecutor queue = new ThreadPoolExecutor(2, 2, 10,
            TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), 
            new ThreadPoolExecutor.DiscardOldestPolicy());
	
}
