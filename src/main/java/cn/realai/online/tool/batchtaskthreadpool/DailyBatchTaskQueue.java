package cn.realai.online.tool.batchtaskthreadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DailyBatchTaskQueue {

	 public final static ThreadPoolExecutor queue = new ThreadPoolExecutor(1, 1, 10,
	            TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
	            new ThreadPoolExecutor.DiscardOldestPolicy());
	
}
