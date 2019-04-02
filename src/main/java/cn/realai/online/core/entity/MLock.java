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
	
	public static final int MLOCK_TYPE_TRAIN = 1;
	
	public static final int MLOCK_TYPE_BATCH = 2;
	
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
