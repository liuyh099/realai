package cn.realai.online.core.bussiness;

import java.util.List;

import cn.realai.online.core.bo.TrainResultRedisKey;
import cn.realai.online.core.entity.BatchExecutionTask;
import cn.realai.online.core.entity.DailyBatchRequest;
import cn.realai.online.core.entity.DeployInfo;

public interface ModelCallBussiness {

    /*
     * 每日跑批
     * @param brs 
     */
    void runBatchDaily(List<DailyBatchRequest> sbrList);
    
    /*
     * 离线跑批
     * @param brs 
     */
    void runBatchOffline(BatchExecutionTask bet);

    /*
     * 预处理回调处理
     */
    int preprocessCallback(Long experimentId, String redisKey); 

    /*
     * 训练回调处理
     */
    void trainCallback(Long experimentId, TrainResultRedisKey redisKey, Integer stage);

    /*
     * 训练错误处理
     */
    void trainErrorDealWith(Long experimentId, String errMsg, String task);

    /*
     * 批次错误处理
     */
	void batchErrorDealWith(Long experimentId, String msg);

	/*
	 * 部署模型
	 */
	int deployServiceModel(DeployInfo deployInfo);

}
