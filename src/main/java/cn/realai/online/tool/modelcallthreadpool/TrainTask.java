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
import cn.realai.online.core.entity.BatchRecord;
import cn.realai.online.core.entity.ModelPerformance;
import cn.realai.online.core.entity.PersonalComboResultSet;
import cn.realai.online.core.entity.PersonalHetroResultSet;
import cn.realai.online.core.entity.PersonalHomoResultSet;
import cn.realai.online.core.entity.PersonalInformation;
import cn.realai.online.core.entity.SampleGrouping;
import cn.realai.online.core.entity.SampleSummary;
import cn.realai.online.core.entity.SampleWeight;
import cn.realai.online.core.entity.TopSort;
import cn.realai.online.core.entity.VariableData;
import cn.realai.online.core.service.BatchRecordService;
import cn.realai.online.core.service.ExperimentService;
import cn.realai.online.core.service.ModelPerformanceService;
import cn.realai.online.core.service.PersonalComboResultSetService;
import cn.realai.online.core.service.PersonalHetroResultSetService;
import cn.realai.online.core.service.PersonalHomoResultSetService;
import cn.realai.online.core.service.PersonalInformationService;
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
		
		int length = sampleGroupingList.size();
		Map<String, Long> sgMap = new HashMap<String, Long>();
		for (SampleGrouping sg : sampleGroupingList) {
			sgMap.put(sg.getGroupName(), sg.getId());
		}
		
		VariableDataService variableDataService = SpringContextUtils.getBean(VariableDataService.class);
		List<VariableData> vdList = variableDataService.findListByExperimentId(experimentId);
		Map<String, Long> vdMap = new HashMap<String, Long>();
		for (VariableData vd : vdList) {
			vdMap.put(vd.getName() + vd.getDataType(), vd.getId());
		}
		
		//样本权重
		analysisSampleWeight(redisClientTemplate.get(redisKey.getSampleWeight()), sgMap, vdMap);
		
		//千人千面人员信息
		List<PersonalInformation> personalInformationList = analysisPersonalInformation(redisClientTemplate.get(redisKey.getPersonalInformation()), sgMap);
		
		length = personalInformationList.size();
		Map<String, Long> piMap = new HashMap<String, Long>();
		for (PersonalInformation pi : personalInformationList) {
			piMap.put(pi.getPersonalId(), pi.getId());
		}
		
		//生成批次
		BatchRecord batchRecord = new BatchRecord();
		batchRecord.setBatchType(BatchRecord.BATCH_TYPE_TRAIN);
		batchRecord.setServiceId(experiment.getServiceId());
		batchRecord.setCreateTime(System.currentTimeMillis());
		batchRecord.setXtableHeterogeneousDataSource(experiment.getXtableHeterogeneousDataSource());
		batchRecord.setXtableHomogeneousDataSource(experiment.getXtableHomogeneousDataSource());
		batchRecord.setYtableDataSource(experiment.getYtableDataSource());
		batchRecord.setExperimentId(experiment.getId());
		batchRecord.setBatchName(experiment.getName() + "训练结果批次数据");
		BatchRecordService batchRecordService = SpringContextUtils.getBean(BatchRecordService.class);
		Long batchRecordId = batchRecordService.insert(batchRecord);
		
		//解析千人千面同质和异质结果
		analysisPersonalResultSet(redisClientTemplate.get(redisKey.getPersonalHomoResultSet()), 
				redisClientTemplate.get(redisKey.getPersonalHetroResultSet()), piMap, batchRecordId);
		
		analysisPersonalComboResultSet(redisClientTemplate.get(redisKey.getPersonalComboResultSet()), piMap, batchRecordId);
		
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
	private void analysisSampleWeight(String redisValue, Map<String, Long> sgMap, Map<String, Long> vdMap) {
		if (redisValue == null || "".equals(redisValue)) {
			logger.error("TrainTask analysisSampleWeight. 训练结果没有样本权重数据. experimentId{}", experimentId);
			throw new RuntimeException("训练结果没有样本权重数据experimentId = " + experimentId);
		}
		List<SampleWeight> swList = JSON.parseArray(redisValue, SampleWeight.class);
		for (SampleWeight sw : swList) {
			if (sw.getExperimentId() != experimentId.longValue()) {
				logger.error("TrainTask analysisSampleWeight. 训练结果数据错误,训练实验id的结果实验id不相等. experimentId{}, resultId{}", experimentId, sw.getExperimentId());
				throw new RuntimeException("训练结果数据错误,训练实验id的结果实验id不相等.");
			}
			Long sgId = sgMap.get(sw.getGroupName());
			if (sgId == null) {
				logger.error("TrainTask analysisSampleWeight. 训练结果数据错误,分组名称不存在. experimentId{}, groupName{}", experimentId, sw.getGroupName());
				throw new RuntimeException("训练结果数据错误,分组名称不存在.");
			}
			sw.setGroupId(sgId);
			Long vdId = vdMap.get(sw.getVariableName() + sw.getVariableType());
			if (vdId == null) {
				logger.error("TrainTask analysisSampleWeight. 训练结果数据错误,变量名称不存在. experimentId{}, groupName{}", experimentId, sw.getVariableName());
				throw new RuntimeException("训练结果数据错误,变量名称不存在.");
			}
			sw.setGroupId(vdId);
		}
		SampleWeightService sampleWeightService = SpringContextUtils.getBean(SampleWeightService.class);
		sampleWeightService.insertList(swList);
	}
	
	/*
	 * 千人千面人员信息
	 */
	private List<PersonalInformation> analysisPersonalInformation(String redisValue, Map<String, Long> sgMap) {
		if (redisValue == null || "".equals(redisValue)) {
			logger.error("TrainTask analysisModelPerformance. 训练结果没有千人千面人员信息数据. experimentId{}", experimentId);
			throw new RuntimeException("训练结果没有千人千面人员信息数据experimentId = " + experimentId);
		}
		List<PersonalInformation> piList = JSON.parseArray(redisValue, PersonalInformation.class);
		for (PersonalInformation pi : piList) {
			if (pi.getExperimentId() != experimentId.longValue()) {
				logger.error("TrainTask analysisModelPerformance. 训练结果数据错误,训练实验id的结果实验id不相等. experimentId{}, resultId{}", experimentId, pi.getExperimentId());
				throw new RuntimeException("训练结果数据错误,训练实验id的结果实验id不相等.");
			}
			Long sgId = sgMap.get(pi.getGroupName());
			if (sgId == null) {
				logger.error("TrainTask analysisPersonalInformation. 训练结果数据错误,分组名称不存在. experimentId{}, groupName{}", experimentId, pi.getGroupName());
				throw new RuntimeException("训练结果数据错误,分组名称不存在.");
			}
			pi.setGroupId(sgId);
		}
		PersonalInformationService personalInformationService = SpringContextUtils.getBean(PersonalInformationService.class);
		personalInformationService.insertList(piList);
		return personalInformationService.findListByExperimentId(experimentId);
	}
	
	/*
	 * 千人千面同质和异质计算结果解析
	 * @param homoValue
	 * @param HetroValue
	 * @param piMap
	 * @param batchRecordId
	 */
	private void analysisPersonalResultSet(String homoValue, String HetroValue, Map<String, Long> piMap, Long batchRecordId) {
		if ((homoValue == null || "".equals(homoValue)) && (HetroValue == null || "".equals(HetroValue))) {
			logger.error("TrainTask analysisPersonalResultSet. 训练结果没有千人千面同质和异质信息数据. experimentId{}", experimentId);
			throw new RuntimeException("训练结果没有千人千面同质和异质信息数据experimentId = " + experimentId);
		}
		if (homoValue != null && !"".equals(homoValue)) {
			List<PersonalHomoResultSet> homoList = JSON.parseArray(homoValue, PersonalHomoResultSet.class);
			for (PersonalHomoResultSet phrs : homoList) {
				Long piId = piMap.get(phrs.getPersonalId());
				if (piId == null) {
					logger.error("TrainTask analysisPersonalResultSet. 训练结果数据错误,千人千面信息不存在. experimentId{}, groupName{}", experimentId, phrs.getPersonalId());
					throw new RuntimeException("训练结果数据错误,千人千面信息不存在.");
				}
				phrs.setPid(piId);
				phrs.setBatchId(batchRecordId);
			}
			PersonalHomoResultSetService personalHomoResultSetService = SpringContextUtils.getBean(PersonalHomoResultSetService.class);
			personalHomoResultSetService.insertList(homoList);
		}
		if (HetroValue != null && !"".equals(HetroValue)) {
			List<PersonalHetroResultSet> hetroList = JSON.parseArray(homoValue, PersonalHetroResultSet.class);
			for (PersonalHetroResultSet phrs : hetroList) {
				Long piId = piMap.get(phrs.getPersonalId());
				if (piId == null) {
					logger.error("TrainTask analysisPersonalResultSet. 训练结果数据错误,千人千面信息不存在. experimentId{}, groupName{}", experimentId, phrs.getPersonalId());
					throw new RuntimeException("训练结果数据错误,千人千面信息不存在.");
				}
				phrs.setPid(piId);
				phrs.setBatchId(batchRecordId);
			}
			PersonalHetroResultSetService personalHetroResultSetService = SpringContextUtils.getBean(PersonalHetroResultSetService.class);
			personalHetroResultSetService.insertList(hetroList);
		}
		
	}

	/*
	 * 千人千面个人组合信息
	 */
	private void analysisPersonalComboResultSet(String redisValue, Map<String, Long> piMap, Long batchRecordId) {
		if (redisValue == null || "".equals(redisValue)) {
			logger.error("TrainTask analysisPersonalComboResultSet. 训练结果没有千人千面人员聚合信息数据. experimentId{}", experimentId);
			throw new RuntimeException("训练结果没有千人千面人员聚合信息数据experimentId = " + experimentId);
		}
		List<PersonalComboResultSet> pcrsList = JSON.parseArray(redisValue, PersonalComboResultSet.class);
		for (PersonalComboResultSet pcrs : pcrsList) {
			Long piId = piMap.get(pcrs.getPersonalId());
			if (piId == null) {
				logger.error("TrainTask analysisPersonalComboResultSet. 训练结果数据错误,千人千面信息不存在. experimentId{}, groupName{}", experimentId, pcrs.getPersonalId());
				throw new RuntimeException("训练结果数据错误,千人千面信息不存在.");
			}
			pcrs.setPid(piId);
			pcrs.setBatchId(batchRecordId);
		}
		PersonalComboResultSetService personalComboResultSetService = SpringContextUtils.getBean(PersonalComboResultSetService.class);
		personalComboResultSetService.insertList(pcrsList);
	}
	
}
