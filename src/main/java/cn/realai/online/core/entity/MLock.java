package cn.realai.online.core.entity;

import cn.realai.online.core.service.MLockService;
import cn.realai.online.util.SpringContextUtils;

/**
 * 数据库锁
 *
 * @author lyh
 */
public class MLock {

	//锁
	private String lockKey;
	
	//锁的过期时间
	private long expiredTime;
	
	//锁的值
	private String lockValue;
	
	//当前时间
	private long currentTime;
	
	//结束时间
	private long endTime;
	
	private int type;
	
	//训练环境的lock
    //任意时间系统只能跑一个训练,在进行训练之前先获取锁
    //实验训练mlock锁
    public static final String TRAIN_MLOCK_LOCK = "TRAINING_LOCK";
    
    //线上环境的lock
    public static final String ONLINE_MLOCK_LOCK = "ONLINE_LOCK";

    //实验训练锁的前缀
    public static final String TRAIN_MLOCK_PREFIX = "EXPERIMENT_TRAIN_";
    
    //批次处理锁前缀
    public static final String BATCH_MLOCK_PREFIX = "BATCH_MLOCK_";

    //实验训练mlock锁,过期时间
    public static final int MLOCK_LEASE_TIME = 60 * 60 * 3 * 1000;
	
	MLockService mlockService = SpringContextUtils.getBean(MLockService.class);
	
	public MLock(String lockKey, String lockValue, long expiredTime) {
		this.lockKey = lockKey;
		this.lockValue = lockValue;
		this.expiredTime = expiredTime;
		this.expiredTime = expiredTime;
		this.currentTime = System.currentTimeMillis();
		this.endTime = currentTime + expiredTime;
	}

	public String getLockKey() {
		return lockKey;
	}

	public long getExpiredTime() {
		return expiredTime;
	}

	public String getLockValue() {
		return lockValue;
	}

	public long getCurrentTime() {
		return currentTime;
	}

	public long getEndTime() {
		return endTime;
	}
	
	public boolean tryLock() {
		return mlockService.tryLock(this);
	}
	
	public boolean unLock() {
		return mlockService.unLock(this);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}
