package cn.realai.online.core.bussiness;

import cn.realai.online.core.bo.TrainResultRedisKey;

public interface ModelCallBussiness {

    /*
     * 每日跑批
     * @param brs 
     */
    void runBatchDaily(Long experimentId, String redisKey, String type, String date);
    
    /*
     * 离线跑批
     * @param brs 
     */
    void runBatchOffline(Long experimentId, String redisKey, String type, String downUrl, Long batchId);

    /*
     * 预处理回调处理
     */
    int preprocessCallback(Long experimentId, String redisKey);

    /*
     * 训练回调处理
     */
    void trainCallback(Long experimentId, TrainResultRedisKey redisKey);

    /*
     * 错误处理
     */
    void errorDealWith(Long experimentId, String errMsg);

}
