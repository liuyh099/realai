package cn.realai.online.common;

public class Constant {

	/*
	 * 实时计算任务超时时间，如果一个任务被放入计算队列，超过一定时间就返回失败
	 */
	public static final int TIMEOUT_CALCULATION = 10;
	
	/*
	 * 为了给Python计算削峰，每次单个服务最多只能同时有两个计算任务发送给python
	 */
	public static final int COUNT_SYNCHRONIZE_CALCULATION = 2;
	
	/**** Python回调task标记开始 ****/
	
	//预处理任务
	public static final String TASK_PREPROCESS = "preprocess";
	
	//实验训练
	public static final String TASK_TRAIN = "train";
	
	/**** Python回调task标记结束 ****/
	
	//redis默认过期时间   一个星期
	public static final int REDIS_EXPIRED_TIME = 604800;
	
}
