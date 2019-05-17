package cn.realai.online.tool.traincallbackthreadpool;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.alibaba.fastjson.JSON;

import cn.realai.online.core.bo.ExperimentBO;
import cn.realai.online.core.bo.TrainResultRedisKey;
import cn.realai.online.core.entity.Experiment;
import cn.realai.online.core.entity.ExperimentResultSet;
import cn.realai.online.core.entity.ModelPerformance;
import cn.realai.online.core.entity.SampleSummary;
import cn.realai.online.core.entity.TopSort;
import cn.realai.online.core.service.ExperimentResultSetService;
import cn.realai.online.core.service.ExperimentService;
import cn.realai.online.core.service.ModelPerformanceService;
import cn.realai.online.core.service.SampleSummaryService;
import cn.realai.online.core.service.TopSortService;
import cn.realai.online.tool.redis.RedisClientTemplate;
import cn.realai.online.util.SpringContextUtils;

/**
 * 模型训练回调任务
 *
 * @author lyh
 */
public class TrainTaskStageOne implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(TrainTaskStageOne.class);

    private Long experimentId;

    private TrainResultRedisKey redisKey;

    public TrainTaskStageOne(Long experimentId, TrainResultRedisKey redisKey) {
        this.experimentId = experimentId;
        this.redisKey = redisKey;
    }

    @Override
    @Transactional()
    public void run() {
    	
    	DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
		//设置隔离级别
		definition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
		DataSourceTransactionManager txManager = SpringContextUtils.getBean(DataSourceTransactionManager.class);
		TransactionStatus ts = txManager.getTransaction(definition);
		ExperimentService experimentService = SpringContextUtils.getBean(ExperimentService.class);
    	
		try {
	    	logger.info("TrainTaskStageOne run, 实验评估回调处理开始， experimentId{}, redisKey{}", experimentId, JSON.toJSONString(redisKey));
	        //查询实验信息
	        ExperimentBO experiment = experimentService.selectExperimentById(experimentId);
	        if (experiment == null) {
	            logger.error("TrainTaskStageOne run. experiment信息不存在. experimentId{}", experimentId);
	            throw new RuntimeException("实验id不存在  experimentId = " + experimentId);
	        }
	
	        //获取Redis操作对象
	        RedisClientTemplate redisClientTemplate = SpringContextUtils.getBean(RedisClientTemplate.class);
	        
	        //解析模型表现
	        analysisModelPerformance(redisClientTemplate.get(redisKey.getModelperformance()));
	        redisClientTemplate.delete(redisKey.getModelperformance());
	
	        //解析top排序
	        analysisTopSort(redisClientTemplate.get(redisKey.getTopsort()));
	        redisClientTemplate.delete(redisKey.getTopsort());
	
	        //样本摘要
	        analysisSampleSummary(redisClientTemplate.get(redisKey.getSampleSummary()));
	        redisClientTemplate.delete(redisKey.getSampleSummary());
	
	        //解析实验结果集 风控或营销
	        analysisExperimentResultSet(redisClientTemplate.get(redisKey.getExperimentResultSet()));
	        redisClientTemplate.delete(redisKey.getExperimentResultSet());
	        
	        //样本综述
	        String sampleReview = redisKey.getSampleReview();
	
	        //模型路径
	        String modelUrl = redisKey.getModelUrl();
	
	
	        //分段统计图片地址（千人千面也是他）
	        String segmentationStatisticsImageUrl = redisKey.getSegmentationStatisticsImageUrl();
	
	        //badTop总数图片地址
	        String badTopCountImageUrl = redisKey.getBadTopCountImageUrl();
	
	        //roc训练图片地址
	        String rocTestImageUrl = redisKey.getRocTestImageUrl();
	
	        //roc测试图片地址
	        String rocTrainImageUrl = redisKey.getRocTrainImageUrl();
	
	        //roc验证图片地址
	        String rocValidateImageUrl = redisKey.getRocValidateImageUrl();
	        
	        
	        //ks图片地址
	        String ksTestImageUrl = redisKey.getKsTestImageUrl();
	
	        String ksTrainImageUrl = redisKey.getKsTrainImageUrl();
	
	        String ksValidateImageUrl = redisKey.getKsValidateImageUrl();
	
	        //维护实验训练结果到实验数据
	        experimentService.trainResultMaintain(experimentId, Experiment.STATUS_TRAINING_STAGE_ONE, sampleReview, modelUrl, segmentationStatisticsImageUrl, badTopCountImageUrl,
	                rocTestImageUrl, rocTrainImageUrl, rocValidateImageUrl, ksTestImageUrl, ksTrainImageUrl, ksValidateImageUrl);
	        
	        txManager.commit(ts);
		} catch (Exception e) {
			logger.info("TrainTaskStageOne run, 实验评估回调处理异常， experimentId{}, redisKey{}", experimentId, JSON.toJSONString(redisKey));
			txManager.rollback(ts);
			experimentService.maintainErrorMsg(experimentId, Experiment.STATUS_TRAINING_ERROR, "评估失败");
			e.printStackTrace();
		}
        
        logger.info("TrainTaskStageOne run, 实验评估回调处理结束， experimentId{}, redisKey{}", experimentId, JSON.toJSONString(redisKey));
        
    }

	/*
     * 模型表现
     */
    private void analysisModelPerformance(String redisValue) {
        if (redisValue == null || "".equals(redisValue)) {
            logger.error("TrainTaskStageOne analysisModelPerformance. 训练结果没有模型表现数据. experimentId{}", experimentId);
            throw new RuntimeException("训练结果没有模型表现数据experimentId = " + experimentId);
        }
        List<ModelPerformance> mpList = JSON.parseArray(redisValue, ModelPerformance.class);
        for (ModelPerformance mp : mpList) {
        	mp.setExperimentId(experimentId);
        }
        ModelPerformanceService modelPerformanceService = SpringContextUtils.getBean(ModelPerformanceService.class);
        modelPerformanceService.deleteByExperimentId(experimentId);
        modelPerformanceService.insertList(mpList);
    }

    /*
     * top排序
     */
    private void analysisTopSort(String redisValue) {
        if (redisValue == null || "".equals(redisValue)) {
            logger.error("TrainTaskStageOne analysisModelPerformance. 训练结果没有top排序数据. experimentId{}", experimentId);
            throw new RuntimeException("训练结果没有top排序数据experimentId = " + experimentId);
        }
        List<TopSort> tsList = JSON.parseArray(redisValue, TopSort.class);
        for (TopSort ts : tsList) {
        	ts.setExperimentId(experimentId);
        }
        TopSortService topSortService = SpringContextUtils.getBean(TopSortService.class);
        topSortService.deleteByExperimentId(experimentId);
        topSortService.insertList(tsList);
    }

    /*
     * 样本摘要
     */
    private void analysisSampleSummary(String redisValue) {
        if (redisValue == null || "".equals(redisValue)) {
            logger.error("TrainTaskStageOne analysisModelPerformance. 训练结果没有样本摘要数据. experimentId{}", experimentId);
            throw new RuntimeException("训练结果没有样本摘要数据experimentId = " + experimentId);
        }
        List<SampleSummary> ssList = JSON.parseArray(redisValue, SampleSummary.class);
        for (SampleSummary ss : ssList) {
        	ss.setExperimentId(experimentId);
        }
        SampleSummaryService sampleSummaryService = SpringContextUtils.getBean(SampleSummaryService.class);
        sampleSummaryService.deleteByExperimentId(experimentId);
        sampleSummaryService.insertList(ssList);
    }

    private void analysisExperimentResultSet(String redisValue) {
        if (redisValue == null || "".equals(redisValue)) {
            logger.error("TrainTaskStageOne analysisExperimentResultSet. 训练结果没有风控或营销信息数据. experimentId{}", experimentId);
            throw new RuntimeException("训练结果没有风控或营销信息数据experimentId = " + experimentId);
        }
        List<ExperimentResultSet> ersList = JSON.parseArray(redisValue, ExperimentResultSet.class);
        for (ExperimentResultSet ers : ersList) {
        	ers.setExperimentId(experimentId);
        }
        ExperimentResultSetService experimentResultSetService = SpringContextUtils.getBean(ExperimentResultSetService.class);
        experimentResultSetService.deleteByExperimentId(experimentId);
        experimentResultSetService.insertList(ersList);
    }

}
