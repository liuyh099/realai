package cn.realai.online.core.bussiness;

import java.util.List;

import cn.realai.online.core.bo.TrainResultRedisKey;

public interface ModelCallBussiness {

	/*
	 * 每日跑批
	 * @param experimentId
	 * @param fileAddress
	 */
	void runBatchDaily(Long experimentId, String fileAddress);

	/*
	 * 预处理回调处理
	 */
	int preprocessCallback(Long experimentId, String redisKey);

	/*
	 * 训练回调处理
	 */
	void trainCallback(Long experimentId, TrainResultRedisKey redisKey);

}
