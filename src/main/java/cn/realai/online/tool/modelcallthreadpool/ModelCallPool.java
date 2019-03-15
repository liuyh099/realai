package cn.realai.online.tool.modelcallthreadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 模型回调任务执行线程池
 * 处理  训练预处理  训练  每日定时跑批 的任务
 * @author lyh
 */
public class ModelCallPool {

	public final static ThreadPoolExecutor modelCallPool = new ThreadPoolExecutor(10, 10, 10,
            TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), 
            new ThreadPoolExecutor.DiscardOldestPolicy());
	
}
