package cn.realai.online.tool.modelcallthreadpool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import cn.realai.online.core.bo.ExperimentBO;
import cn.realai.online.core.bo.TrainResultRedisKey;
import cn.realai.online.core.entity.ModelPerformance;
import cn.realai.online.core.entity.SampleGrouping;
import cn.realai.online.core.entity.SampleSummary;
import cn.realai.online.core.entity.SampleWeight;
import cn.realai.online.core.entity.TopSort;
import cn.realai.online.core.entity.VariableData;
import cn.realai.online.core.service.ExperimentService;
import cn.realai.online.core.service.ModelPerformanceService;
import cn.realai.online.core.service.SampleGroupingService;
import cn.realai.online.core.service.SampleSummaryService;
import cn.realai.online.core.service.SampleWeightService;
import cn.realai.online.core.service.TopSortService;
import cn.realai.online.core.service.VariableDataService;
import cn.realai.online.tool.redis.RedisClientTemplate;
import cn.realai.online.util.SpringContextUtils;

/**
 * 模型训练回调任务
 * @author lyh
 */
public class TrainTask implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(TrainTask.class);
	
	private Long experimentId;
	
	private TrainResultRedisKey redisKey;
	
	public TrainTask(Long experimentId, TrainResultRedisKey redisKey) {
		this.experimentId = experimentId;
		this.redisKey = redisKey;
	}
	
	@Override
	@Transactional()
	public void run() {
		//查询实验信息
		ExperimentService experimentService = SpringContextUtils.getBean(ExperimentService.class);
		ExperimentBO experiment = experimentService.selectExperimentById(experimentId);
		if (experiment == null) {
			logger.error("TrainTask run. experiment信息不存在. experimentId{}", experimentId);
		}
		
		//获取Redis操作对象
		RedisClientTemplate redisClientTemplate = SpringContextUtils.getBean(RedisClientTemplate.class);
		
		//解析模型表现
		analysisModelPerformance(redisClientTemplate.get(redisKey.getModelperformance()));
		
		//解析top排序
		analysisTopSort(redisClientTemplate.get(redisKey.getTopsort()));
		
		//样本摘要
		analysisSampleSummary(redisClientTemplate.get(redisKey.getSampleSummary()));
		
		//样本分组
		List<SampleGrouping> sampleGroupingList = analysisSampleGrouping(redisClientTemplate.get(redisKey.getSampleGrouping()));
		
		//样本权重
		analysisSampleWeight(redisClientTemplate.get(redisKey.getSampleWeight()), sampleGroupingList);
		
		VariableDataService variableDataService = SpringContextUtils.getBean(VariableDataService.class);
		List<VariableData> vdList = variableDataService.findListByExperimentId(experimentId);
		//Map<String, VariableData> homo = new HashMap<>;
		
	}
	
	/*
	 * 模型表现
	 */
	private void analysisModelPerformance(String redisValue) {
		if (redisValue == null || "".equals(redisValue)) {
			logger.error("TrainTask analysisModelPerformance. 训练结果没有模型表现数据. experimentId{}", experimentId);
			throw new RuntimeException("训练结果没有模型表现数据experimentId = " + experimentId);
		}
		List<ModelPerformance> mpList = JSON.parseArray(redisValue, ModelPerformance.class);
		for (ModelPerformance mp : mpList) {
			if (mp.getExperimentId() != experimentId.longValue()) {
				logger.error("TrainTask analysisModelPerformance. 训练结果数据错误,训练实验id的结果实验id不相等. experimentId{}, resultId{}", experimentId, mp.getExperimentId());
				throw new RuntimeException("训练结果数据错误,训练实验id的结果实验id不相等.");
			}
		}
		ModelPerformanceService modelPerformanceService = SpringContextUtils.getBean(ModelPerformanceService.class);
		modelPerformanceService.insertList(mpList);
	}
	
	/*
	 * top排序
	 */
	private void analysisTopSort(String redisValue) {
		if (redisValue == null || "".equals(redisValue)) {
			logger.error("TrainTask analysisModelPerformance. 训练结果没有top排序数据. experimentId{}", experimentId);
			throw new RuntimeException("训练结果没有top排序数据experimentId = " + experimentId);
		}
		List<TopSort> tsList = JSON.parseArray(redisValue, TopSort.class);
		for (TopSort ts : tsList) {
			if (ts.getExperimentId() != experimentId.longValue()) {
				logger.error("TrainTask analysisModelPerformance. 训练结果数据错误,训练实验id的结果实验id不相等. experimentId{}, resultId{}", experimentId, ts.getExperimentId());
				throw new RuntimeException("训练结果数据错误,训练实验id的结果实验id不相等.");
			}
		}
		TopSortService topSortService = SpringContextUtils.getBean(TopSortService.class);
		topSortService.insertList(tsList);
	}
	
	/*
	 * 样本摘要
	 */
	private void analysisSampleSummary(String redisValue) {
		if (redisValue == null || "".equals(redisValue)) {
			logger.error("TrainTask analysisModelPerformance. 训练结果没有样本摘要数据. experimentId{}", experimentId);
			throw new RuntimeException("训练结果没有样本摘要数据experimentId = " + experimentId);
		}
		List<SampleSummary> ssList = JSON.parseArray(redisValue, SampleSummary.class);
		for (SampleSummary ss : ssList) {
			if (ss.getExperimentId() != experimentId.longValue()) {
				logger.error("TrainTask analysisModelPerformance. 训练结果数据错误,训练实验id的结果实验id不相等. experimentId{}, resultId{}", experimentId, ss.getExperimentId());
				throw new RuntimeException("训练结果数据错误,训练实验id的结果实验id不相等.");
			}
		}
		SampleSummaryService sampleSummaryService = SpringContextUtils.getBean(SampleSummaryService.class);
		sampleSummaryService.insertList(ssList);
	}

	/*
	 * 样本分组
	 */
	private List<SampleGrouping> analysisSampleGrouping(String redisValue) {
		if (redisValue == null || "".equals(redisValue)) {
			logger.error("TrainTask analysisModelPerformance. 训练结果没有样本分组数据. experimentId{}", experimentId);
			throw new RuntimeException("训练结果没有样本分组数据experimentId = " + experimentId);
		}
		List<SampleGrouping> sgList = JSON.parseArray(redisValue, SampleGrouping.class);
		for (SampleGrouping sg : sgList) {
			if (sg.getExperimentId() != experimentId.longValue()) {
				logger.error("TrainTask analysisModelPerformance. 训练结果数据错误,训练实验id的结果实验id不相等. experimentId{}, resultId{}", experimentId, sg.getExperimentId());
				throw new RuntimeException("训练结果数据错误,训练实验id的结果实验id不相等.");
			}
		}
		SampleGroupingService sampleGroupingService = SpringContextUtils.getBean(SampleGroupingService.class);
		sampleGroupingService.insertList(sgList);
		sgList = sampleGroupingService.findListByExperimentId(experimentId);
		return sgList;
	}
	
	/*
	 * 样本权重
	 */
	private void analysisSampleWeight(String redisValue, List<SampleGrouping> sgList) {
		if (redisValue == null || "".equals(redisValue)) {
			logger.error("TrainTask analysisModelPerformance. 训练结果没有样本权重数据. experimentId{}", experimentId);
			throw new RuntimeException("训练结果没有样本权重数据experimentId = " + experimentId);
		}
		List<SampleWeight> swList = JSON.parseArray(redisValue, SampleWeight.class);
		for (SampleWeight sw : swList) {
			if (sw.getExperimentId() != experimentId.longValue()) {
				logger.error("TrainTask analysisModelPerformance. 训练结果数据错误,训练实验id的结果实验id不相等. experimentId{}, resultId{}", experimentId, sw.getExperimentId());
				throw new RuntimeException("训练结果数据错误,训练实验id的结果实验id不相等.");
			}
		}
		SampleWeightService sampleWeightService = SpringContextUtils.getBean(SampleWeightService.class);
		sampleWeightService.insertList(sgList);
	}
	
}
