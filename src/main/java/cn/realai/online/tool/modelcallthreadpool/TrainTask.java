package cn.realai.online.tool.modelcallthreadpool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import cn.realai.online.core.entity.BatchRecord;
import cn.realai.online.core.entity.Experiment;
import cn.realai.online.core.entity.ExperimentResultSet;
import cn.realai.online.core.entity.MLock;
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
import cn.realai.online.core.service.ExperimentResultSetService;
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
 *
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
        
        
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
		//设置隔离级别
		definition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
		
		DataSourceTransactionManager txManager = SpringContextUtils.getBean(DataSourceTransactionManager.class);
		
		TransactionStatus ts = txManager.getTransaction(definition);
		try {
	
	        //解析模型表现
	        analysisModelPerformance(redisClientTemplate.get(redisKey.getModelperformance()));
	        redisClientTemplate.delete(redisKey.getModelperformance());
	
	        //解析top排序
	        analysisTopSort(redisClientTemplate.get(redisKey.getTopsort()));
	        redisClientTemplate.delete(redisKey.getTopsort());
	
	        //样本摘要
	        analysisSampleSummary(redisClientTemplate.get(redisKey.getSampleSummary()));
	        redisClientTemplate.delete(redisKey.getSampleSummary());
	
	        //样本分组
	        List<SampleGrouping> sampleGroupingList = analysisSampleGrouping(redisClientTemplate.get(redisKey.getSampleGrouping()));
	        redisClientTemplate.delete(redisKey.getSampleGrouping());
	
	        Map<String, Long> sgMap = new HashMap<String, Long>();
	        for (SampleGrouping sg : sampleGroupingList) {
	            sgMap.put(sg.getGroupName(), sg.getId());
	        }
	
	        VariableDataService variableDataService = SpringContextUtils.getBean(VariableDataService.class);
	        List<VariableData> vdList = variableDataService.findListByExperimentId(experimentId);
	        Map<String, Long> vdMap = new HashMap<String, Long>();
	        for (VariableData vd : vdList) {
	            vdMap.put(vd.getName() + vd.getVariableType(), vd.getId());
	        }
	
	        //样本权重
	        analysisSampleWeight(redisClientTemplate.get(redisKey.getSampleWeight()), sgMap, vdMap);
	        redisClientTemplate.delete(redisKey.getSampleWeight());
	
	        //千人千面人员信息
	        
	
	        //生成批次
	        BatchRecord batchRecord = new BatchRecord();
	        batchRecord.setBatchType(BatchRecord.BATCH_TYPE_TRAIN);
	        batchRecord.setServiceId(experiment.getServiceId());
	        batchRecord.setXtableHeterogeneousDataSource(experiment.getXtableHeterogeneousDataSource());
	        batchRecord.setXtableHomogeneousDataSource(experiment.getXtableHomogeneousDataSource());
	        batchRecord.setYtableDataSource(experiment.getYtableDataSource());
	        batchRecord.setExperimentId(experiment.getId());
	        batchRecord.setBatchName(experiment.getName() + "训练结果批次数据");
	        BatchRecordService batchRecordService = SpringContextUtils.getBean(BatchRecordService.class);
	        batchRecordService.insert(batchRecord);
	        Long batchRecordId = batchRecord.getId();
	        
	        List<PersonalInformation> personalInformationList = analysisPersonalInformation(redisClientTemplate.get(redisKey.getPersonalInformation()), sgMap, batchRecordId);
	
	        Map<String, Long> piMap = new HashMap<String, Long>();
	        for (PersonalInformation pi : personalInformationList) {
	            piMap.put(pi.getPersonalId(), pi.getId());
	        }
	
	        //解析千人千面同质和异质结果
	        analysisPersonalResultSet(redisClientTemplate.get(redisKey.getPersonalHomoResultSet()),
	                redisClientTemplate.get(redisKey.getPersonalHetroResultSet()), piMap, vdMap, batchRecordId);
	        redisClientTemplate.delete(redisKey.getPersonalHetroResultSet());
	        
	        //解析千人千面聚合信息
	        analysisPersonalComboResultSet(redisClientTemplate.get(redisKey.getPersonalComboResultSet()), piMap, vdMap, batchRecordId);
	        redisClientTemplate.delete(redisKey.getPersonalComboResultSet());
	        
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
	        experimentService.trainResultMaintain(experimentId, Experiment.STATUS_TRAINING_OVER, sampleReview, modelUrl, segmentationStatisticsImageUrl, badTopCountImageUrl,
	                rocTestImageUrl, rocTrainImageUrl, rocValidateImageUrl, ksTestImageUrl, ksTrainImageUrl, ksValidateImageUrl);
	        
	        txManager.commit(ts);
		} catch (Exception e) {
			e.printStackTrace();
			txManager.rollback(ts);
		}
        
        //释放MLock锁
        MLock mlock = experimentService.getExperimentTrainMLockInstance(experimentId);
        mlock.unLock();
        
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
        	mp.setExperimentId(experimentId);
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
        	ts.setExperimentId(experimentId);
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
        	ss.setExperimentId(experimentId);
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
        	sg.setExperimentId(experimentId);
        	sg.setCreateTime(System.currentTimeMillis());
        }
        SampleGroupingService sampleGroupingService = SpringContextUtils.getBean(SampleGroupingService.class);
        sampleGroupingService.insertList(sgList);
        sgList = sampleGroupingService.findListByExperimentId(experimentId, true, true);
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
            sw.setExperimentId(experimentId);
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
            sw.setVariableId(vdId);
        }
        SampleWeightService sampleWeightService = SpringContextUtils.getBean(SampleWeightService.class);
        sampleWeightService.insertList(swList);
    }

    /*
     * 千人千面人员信息
     */
    private List<PersonalInformation> analysisPersonalInformation(String redisValue, Map<String, Long> sgMap, Long batchRecordId) {
        if (redisValue == null || "".equals(redisValue)) {
            logger.error("TrainTask analysisModelPerformance. 训练结果没有千人千面人员信息数据. experimentId{}", experimentId);
            throw new RuntimeException("训练结果没有千人千面人员信息数据experimentId = " + experimentId);
        }
        List<PersonalInformation> piList = JSON.parseArray(redisValue, PersonalInformation.class);
        for (PersonalInformation pi : piList) {
            pi.setExperimentId(experimentId);
            Long sgId = sgMap.get(pi.getGroupName());
            if (sgId == null) {
                logger.error("TrainTask analysisPersonalInformation. 训练结果数据错误,分组名称不存在. experimentId{}, groupName{}", experimentId, pi.getGroupName());
                throw new RuntimeException("训练结果数据错误,分组名称不存在.");
            }
            pi.setGroupId(sgId);
            pi.setBatchId(batchRecordId);
        }
        PersonalInformationService personalInformationService = SpringContextUtils.getBean(PersonalInformationService.class);
        personalInformationService.insertList(piList);
        return personalInformationService.findListByExperimentIdAndBatchId(experimentId, batchRecordId);
    }

    /*
     * 千人千面同质和异质计算结果解析
     * @param homoValue
     * @param HetroValue
     * @param piMap
     * @param batchRecordId
     */
    private void analysisPersonalResultSet(String homoValue, String HetroValue, Map<String, Long> piMap, Map<String, Long> vdMap, Long batchRecordId) {
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
                
                Long vId = vdMap.get(phrs.getVariableName() + VariableData.SCHEMA_TYPE_HOMO);
                phrs.setVariableId(vId);
                phrs.setPid(piId);
                phrs.setBatchId(batchRecordId);
                phrs.setExperimentId(experimentId);
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
                Long vId = vdMap.get(phrs.getVariableName() + VariableData.SCHEMA_TYPE_HETERO);
                phrs.setVariableId(vId);
                phrs.setPid(piId);
                phrs.setBatchId(batchRecordId);
                phrs.setExperimentId(experimentId);
            }
            PersonalHetroResultSetService personalHetroResultSetService = SpringContextUtils.getBean(PersonalHetroResultSetService.class);
            personalHetroResultSetService.insertList(hetroList);
        }

    }

    /*
     * 千人千面个人组合信息
     */
    private void analysisPersonalComboResultSet(String redisValue, Map<String, Long> piMap, Map<String, Long> vdMap, Long batchRecordId) {
        if (redisValue == null || "".equals(redisValue)) {
        	return;
        }
    	List<PersonalComboResultSet> comboList = JSON.parseArray(redisValue, PersonalComboResultSet.class);
        for (PersonalComboResultSet pcrs : comboList) {
            Long piId = piMap.get(pcrs.getPersonalId());
            if (piId == null) {
                logger.error("TrainTask analysisPersonalComboResultSet. 训练结果数据错误,千人千面信息不存在. experimentId{}, groupName{}", experimentId, pcrs.getPersonalId());
                throw new RuntimeException("训练结果数据错误,千人千面信息不存在.");
            }
            pcrs.setPid(piId);
            pcrs.setBatchId(batchRecordId);
            Long variableId1 = vdMap.get(pcrs.getVariableName1() + VariableData.SCHEMA_TYPE_HETERO);
            pcrs.setVariableId1(variableId1);
            Long variableId2 = vdMap.get(pcrs.getVariableName2() + VariableData.SCHEMA_TYPE_HETERO);
            pcrs.setVariableId2(variableId2);
            Long variableId3 = vdMap.get(pcrs.getVariableName3() + VariableData.SCHEMA_TYPE_HETERO);
            pcrs.setVariableId3(variableId3);
        }
        PersonalComboResultSetService personalComboResultSetService = SpringContextUtils.getBean(PersonalComboResultSetService.class);
        personalComboResultSetService.insertList(comboList);
    }

    private void analysisExperimentResultSet(String redisValue) {
        if (redisValue == null || "".equals(redisValue)) {
            logger.error("TrainTask analysisExperimentResultSet. 训练结果没有风控或营销信息数据. experimentId{}", experimentId);
            throw new RuntimeException("训练结果没有风控或营销信息数据experimentId = " + experimentId);
        }
        List<ExperimentResultSet> ersList = JSON.parseArray(redisValue, ExperimentResultSet.class);
        for (ExperimentResultSet ers : ersList) {
        	ers.setExperimentId(experimentId);
        }
        ExperimentResultSetService experimentResultSetService = SpringContextUtils.getBean(ExperimentResultSetService.class);
        experimentResultSetService.insertList(ersList);
    }

}
