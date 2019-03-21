package cn.realai.online.tool.modelcallthreadpool;

/**
 * 每日跑批任务
 *
 * @author lyh
 */
public class BatchDailyTask implements Runnable {

    private Long experimentId;

    private String redisKey;

    public BatchDailyTask(Long experimentId, String redisKey) {
        this.experimentId = experimentId;
        this.redisKey = redisKey;
    }

    @Override
    public void run() {

    }

}
