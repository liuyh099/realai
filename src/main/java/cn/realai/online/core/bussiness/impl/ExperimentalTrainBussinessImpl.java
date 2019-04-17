package cn.realai.online.core.bussiness.impl;

import cn.realai.online.calculation.TrainService;
import cn.realai.online.common.page.PageBO;
import cn.realai.online.core.bo.*;
import cn.realai.online.core.bussiness.ExperimentalTrainBussiness;
import cn.realai.online.core.bussiness.TuningRecordBussiness;
import cn.realai.online.core.entity.*;
import cn.realai.online.core.query.ExperimentalTrainCreateModelDataQuery;
import cn.realai.online.core.query.ExperimentalTrainQuery;
import cn.realai.online.core.query.FaceListDataQuery;
import cn.realai.online.core.query.IdQuery;
import cn.realai.online.core.service.*;
import cn.realai.online.tool.redis.RedisClientTemplate;
import cn.realai.online.util.UserUtils;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 实验训练的业务实现
 */
@Service
@Transactional(readOnly = true)
public class ExperimentalTrainBussinessImpl implements ExperimentalTrainBussiness {

	private static Logger logger = LoggerFactory.getLogger(ExperimentalTrainBussinessImpl.class);
	
    @Autowired
    private ExperimentService experimentService;

    @Autowired
    private TrainService trainService;

    @Autowired
    private VariableDataService variableDataService;

    @Autowired
    private ExperimentResultSetService resultSetService;

    @Autowired
    private TopSortService topSortService;

    @Autowired
    private SampleSummaryService sampleSummaryService;

    @Autowired
    private PersonalInformationService personalInformationService;

    @Autowired
    private BatchRecordService batchRecordService;

    @Autowired
    private PersonalComboResultSetService personalComboResultSetService;

    @Autowired
    private PersonalHetroResultSetService personalHetroResultSetService;

    @Autowired
    private PersonalHomoResultSetService personalHomoResultSetService;

    @Autowired
    private ModelPerformanceService modelPerformanceService;

    @Autowired
    private SampleGroupingService sampleGroupingService;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private TuningRecordBussiness tuningRecordBussiness;

    /**
     * 根据实验名称和状态等分页查询实验列表
     *
     * @param experimentalTrainQuery 实验列表查询条件
     * @return
     */
    @Override
    public PageBO<ExperimentBO> pageList(ExperimentalTrainQuery experimentalTrainQuery) {

        //开启分页
        Page page = PageHelper.startPage(experimentalTrainQuery.getPageNum(), experimentalTrainQuery.getPageSize());

        //执行条件查询
        Experiment experiment = new Experiment();
        BeanUtils.copyProperties(experimentalTrainQuery, experiment);
        experiment.setServiceId(experimentalTrainQuery.getServiceId());
        List<Experiment> list = experimentService.findList(experiment);

        Map<Long, Boolean> tuningMap = new HashedMap();
        for (Experiment experiment1 : list) {
            if(experiment1.getStatus()==Experiment.STATUS_TRAINING_OVER ){
                Boolean tuningFlag = tuningMap.get(experiment1.getServiceId());
                if (tuningFlag == null) {
                    tuningFlag = checkTrainTuningLock(experiment1.getServiceId(), experimentalTrainQuery.getTuningType());
                    tuningMap.put(experiment1.getServiceId(), tuningFlag);
                }
                if (tuningFlag != null && tuningFlag == true) {
                    experiment1.setPublishCount(0);
                }
            }else {
                experiment1.setPublishCount(1);
            }
        }
        List<ExperimentBO> result = JSON.parseArray(JSON.toJSONString(list), ExperimentBO.class);
        PageBO<ExperimentBO> pageBO = new PageBO<ExperimentBO>(result, experimentalTrainQuery.getPageSize(), experimentalTrainQuery.getPageNum(), page.getTotal(), page.getPages());
        return pageBO;
    }

    /**
     * 检查训练是否可以调优
     *
     * @param serviceId
     * @param tuningType
     * @return
     */
    private boolean checkTrainTuningLock(Long serviceId, Integer tuningType) {
        TuningRecord tuningRecord = tuningRecordBussiness.selectValidTuningRecord(serviceId);
        if (tuningRecord != null) {
            return true;
        }
        return false;
    }

    /**
     * 根据实验训练id删除实验训练
     *
     * @param ids 实验训练id集合
     */
    @Override
    @Transactional(readOnly = false)
    public Integer deleteExperimentByIds(List<Long> ids) {
        int count = experimentService.logicDeleteExperiment(ids);
        if (count > 0) {
            for (Long id : ids) {
                trainService.deleteExperiment(id);
            }
        }
        return count;
    }

    /*
     * 训练
     * @param trainId 实验id
     */
	@Override
    @Transactional(readOnly = false)
    public int train(long experimentId) {

    	ExperimentBO experimentBO = experimentService.selectExperimentById(experimentId);
    	logger.info("ExperimentalTrainBussinessImpl train. experimentBO{}", JSON.toJSONString(experimentBO));
    	
    	//验证服务密钥是否过期
    	if (!serviceService.checkService(experimentBO.getServiceId())) {
    		return -2;
    	}
    	
        int deleteStatus = VariableData.DELETE_YES;
        Long parentId = null;
        
        if (experimentBO.getParentId() != null && experimentBO.getParentId() != 0) {
        	logger.info("ExperimentalTrainBussinessImpl train. experimentBO{}, VariableData{}", JSON.toJSONString(experimentBO), VariableData.DELETE_NO);
        	deleteStatus = VariableData.DELETE_NO;
        	parentId = experimentBO.getParentId();
        }

        //查询需要删除的列
        HomoAndHetroBO deleteVariableData = variableDataService.selectDeleteByExperimentId(experimentId, deleteStatus);

        //修改试验状态

        //训练    
        int ret = trainService.training(experimentBO, parentId, deleteVariableData.getHomoList(), deleteVariableData.getHetroList(), deleteStatus);
        if (ret != 1) {
            return ret;
        }
        ret = experimentService.train(experimentId, Experiment.STATUS_TRAINING, System.currentTimeMillis());
        return ret;
    }

    /**
     * 根据实验ID获取实验详情
     *
     * @param experimentId 实验id
     * @return 实验详情
     */
    @Override
    public ExperimentalTrainDetailBO detail(long experimentId) {
        ExperimentalTrainDetailBO experimentalTrainDetailBO = experimentService.selectExperimentDetailById(experimentId);
        return experimentalTrainDetailBO;
    }

    @Override
    @Transactional(readOnly = false)
    public Long selectFileAdd(ExperimentBO experimentBO) {
        Experiment experiment = new Experiment();
        BeanUtils.copyProperties(experimentBO, experiment);
        experiment.setCreateTime(System.currentTimeMillis());
        experiment.setStatus(Experiment.STATUS_FILE);
        experiment.setReleasStatus(Experiment.RELEAS_NO);
        experiment.setAlgorithmType("全监督算法");
        experiment.setCreateUserId(UserUtils.getUser().getId());
        Long ret = experimentService.insert(experiment);
        variableDataService.deleteVariableDataByExperimentId(experimentBO.getId());
        trainService.preprocess(experiment);
        return ret;
    }

    @Override
    public boolean checkTrainName(String name, Long id) {
        return experimentService.checkTrainName(name, id);
    }

    @Override
    public ExperimentBO selectById(Long trainId) {
        Experiment experiment = experimentService.selectExperimentById(trainId);
        ExperimentBO experimentBO = new ExperimentBO();
        if(experiment==null){
            return null;
        }
        BeanUtils.copyProperties(experiment, experimentBO);
        return experimentBO;
    }

    @Override
    @Transactional(readOnly = false)
    public Integer selectFileUpdate(ExperimentBO experimentBO) {
        Experiment experiment = new Experiment();
        BeanUtils.copyProperties(experimentBO, experiment);
        variableDataService.deleteVariableDataByExperimentId(experimentBO.getId());
        trainService.preprocess(experiment);
        return experimentService.selectFileUpdate(experiment);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updateParam(ExperimentBO experimentBO) {
        Experiment experiment = new Experiment();
        BeanUtils.copyProperties(experimentBO, experiment);
        return experimentService.updateParam(experiment);
    }

    @Override
    public PageBO<VariableDataBO> pageHomOrHemeList(ExperimentalTrainCreateModelDataQuery query) {
        ExperimentBO experimentBO = selectById(query.getId());
        if (experimentBO != null && new Integer(2).equals(experimentBO.getPreFinish())) {
            //可以查询训练结果
            VariableData variableData = new VariableData();
            variableData.setExperimentId(query.getId());
            variableData.setVariableType(query.getVariableType());
            Page page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
            List<VariableData> list = variableDataService.findVariableDataList(variableData);
            if (CollectionUtils.isEmpty(list)) {
                return new PageBO<VariableDataBO>(query);
            }
            List<VariableDataBO> result = JSON.parseArray(JSON.toJSONString(list), VariableDataBO.class);
            PageBO<VariableDataBO> pageBO = new PageBO<VariableDataBO>(result, query.getPageSize(), query.getPageNum(), page.getTotal(), page.getPages());
            return pageBO;
        }
        return new PageBO<VariableDataBO>(query);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteVariableData(Long experimentId, List<Long> ids) {
        variableDataService.deleteVariableData(experimentId, ids);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteVariableDataWithRecommend(Long experimentId, List<Long> excludeIds) {
        experimentService.updateExperimentStatus(experimentId,Experiment.STATUS_FILTER);
        variableDataService.deleteByRecommendAndExcludeIds(experimentId, excludeIds);
    }

    @Override
    public ExperimentalResultQuatoBO quota(Long experimentId) {



        List<ExperimentResultSetBO> trainResultSetListBO = quotaCommon(Experiment.DATA_SET_TRAIN, experimentId, "parent");
        List<ExperimentResultSetBO> testResultSetListBO = quotaCommon(Experiment.DATA_SET_TEST, experimentId, "parent");
        List<ExperimentResultSetBO> validResultSetListBO = quotaCommon(Experiment.DATA_SET_VALID, experimentId, "parent");
        ExperimentalResultQuatoBO experimentalResultQuatoBO = new ExperimentalResultQuatoBO();
        experimentalResultQuatoBO.setModel(1);
        experimentalResultQuatoBO.setTestResultList(testResultSetListBO);
        experimentalResultQuatoBO.setTrainResultList(trainResultSetListBO);
        if (validResultSetListBO == null || validResultSetListBO.size() <= 0) {
            experimentalResultQuatoBO.setValidateResultList(null);
        } else {
            experimentalResultQuatoBO.setValidateResultList(validResultSetListBO);
        }


        //获得评估的结果集
        Experiment experiment = experimentService.selectExperimentById(experimentId);
        cn.realai.online.core.entity.Service service = serviceService.selectServiceById(experiment.getServiceId());
        Integer type = service.getType();
        experimentalResultQuatoBO.setModel(type);
        return experimentalResultQuatoBO;
    }

    private List<ExperimentResultSetBO> quotaCommon(Integer dataSetType, Long experimentId, String type) {
        ExperimentResultSet experimentResultSet = new ExperimentResultSet();
        experimentResultSet.setExperimentId(experimentId);
        experimentResultSet.setDataSetType(dataSetType);
        experimentResultSet.setSearchType(type);
        List<ExperimentResultSet> resultSetList = resultSetService.findList(experimentResultSet);
        List<ExperimentResultSetBO> resultSetListBO = JSON.parseArray(JSON.toJSONString(resultSetList), ExperimentResultSetBO.class);
        return resultSetListBO;
    }


    @Override
    public List<ExperimentResultSetBO> quotaGroup(Long parentId) {

        ExperimentResultSet experimentResultSet = new ExperimentResultSet();
        experimentResultSet.setId(parentId);
        List<ExperimentResultSet> list = resultSetService.findList(experimentResultSet);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        ExperimentResultSet experimentResultSetTemp = list.get(0);
        String name = experimentResultSetTemp.getGroupName();
        name = name.substring(0, name.length() - 1);
        List<ExperimentResultSetBO> validResultSetList = quotaCommon(experimentResultSetTemp.getDataSetType(), experimentResultSetTemp.getExperimentId(), name);
        return validResultSetList;
    }

    @Override
    public ExperimentalResultTopBO quotaTopGroup(Long experimentId) {
        ExperimentalResultTopGroupBO train = quotaTopGroupCommon(experimentId, Experiment.DATA_SET_TRAIN);
        ExperimentalResultTopGroupBO test = quotaTopGroupCommon(experimentId, Experiment.DATA_SET_TEST);
        ExperimentalResultTopGroupBO valid = quotaTopGroupCommon(experimentId, Experiment.DATA_SET_VALID);
        ExperimentalResultTopBO resultTopBO = new ExperimentalResultTopBO();
        resultTopBO.setTestTop(test);
        resultTopBO.setTrainTop(train);
        resultTopBO.setValidateTop(valid);
        return resultTopBO;
    }

    @Override
    public List<SampleSummaryBO> summary(Long experimentId) {
        SampleSummary sampleSummary = new SampleSummary();
        sampleSummary.setExperimentId(experimentId);
        List<SampleSummary> ret = sampleSummaryService.findList(sampleSummary);
        List<SampleSummaryBO> result = JSON.parseArray(JSON.toJSONString(ret), SampleSummaryBO.class);
        return result;
    }

    @Override
    public PageBO<PersonalInformationBO> personalInformationPage(FaceListDataQuery query, Integer batchType) {

        BatchRecord batchRecord = getBatchRecord(query, batchType);
        if (ObjectUtils.isEmpty(batchRecord)) {
            return new PageBO<PersonalInformationBO>(query);
        }

        Page page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        PersonalInformation personal = buildQueryCondition(batchRecord, query);
        List<PersonalInformation> list = personalInformationService.findList(personal);

        List<PersonalInformationBO> result = JSON.parseArray(JSON.toJSONString(list), PersonalInformationBO.class);
        PageBO<PersonalInformationBO> pageBO = new PageBO<PersonalInformationBO>(result, query.getPageSize(), query.getPageNum(), page.getTotal(), page.getPages());
        return pageBO;
    }

    private BatchRecord getBatchRecord(FaceListDataQuery query, Integer batchType) {
        BatchRecord batchRecord = new BatchRecord();
        batchRecord.setExperimentId(query.getId());
        batchRecord.setBatchType(batchType);
        batchRecord.setId(query.getBatchId());
        batchRecord = batchRecordService.getByEntity(batchRecord);
        return batchRecord;
    }

    @Override
    public PersonalInformationBO listDataDetail(Long id) {
        PersonalInformation personal = personalInformationService.get(id);
        PersonalInformationBO result = new PersonalInformationBO();
        BeanUtils.copyProperties(personal, result);
        return result;
    }

    @Override
    public List<PersonalComboResultSetBO> listDataDetailTopGroup(Long id) {
        PersonalComboResultSet query = new PersonalComboResultSet();
        query.setPid(id);
        List<PersonalComboResultSet> list = personalComboResultSetService.findList(query);
        List<PersonalComboResultSetBO> result = JSON.parseArray(JSON.toJSONString(list), PersonalComboResultSetBO.class);
        for (PersonalComboResultSetBO boTep : result) {
            VariableData variableData = variableDataService.getById(boTep.getVariableId1());
            boTep.setVariableName1(variableData.getName());
            boTep.setVariableMeaning1(variableData.getMeaning());
            VariableData variableData2 = variableDataService.getById(boTep.getVariableId2());
            boTep.setVariableName2(variableData2.getName());
            boTep.setVariableMeaning2(variableData2.getMeaning());
            VariableData variableData3 = variableDataService.getById(boTep.getVariableId3());
            boTep.setVariableName3(variableData3.getName());
            boTep.setVariableMeaning3(variableData3.getMeaning());
        }
        return result;
    }

    @Override
    public List<PersonalHetroResultSetBO> listDataDetailTopTen(Long id) {
        PersonalHetroResultSet query = new PersonalHetroResultSet();
        query.setPid(id);
        PageHelper.startPage(1, 10);
        List<PersonalHetroResultSet> list = personalHetroResultSetService.findList(query);
        List<PersonalHetroResultSetBO> bo = JSON.parseArray(JSON.toJSONString(list), PersonalHetroResultSetBO.class);
        for (PersonalHetroResultSetBO boTep : bo) {
            VariableData variableData = variableDataService.getById(boTep.getVariableId());
            boTep.setVariableName(variableData.getName());
            boTep.setVariableMeaning(variableData.getMeaning());
        }
        return bo;
    }

    @Override
    public PageBO<PersonalHetroResultSetBO> listPersonalHetroResultSet(IdQuery query) {
        PersonalHetroResultSet queryCondition = new PersonalHetroResultSet();
        queryCondition.setPid(query.getId());
        Page page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<PersonalHetroResultSet> list = personalHetroResultSetService.findList(queryCondition);
        List<PersonalHetroResultSetBO> result = JSON.parseArray(JSON.toJSONString(list), PersonalHetroResultSetBO.class);
        for (PersonalHetroResultSetBO boTep : result) {
            VariableData variableData = variableDataService.getById(boTep.getVariableId());
            boTep.setVariableName(variableData.getName());
            boTep.setVariableMeaning(variableData.getMeaning());
        }
        PageBO<PersonalHetroResultSetBO> pageBO = new PageBO<PersonalHetroResultSetBO>(result, query.getPageSize(), query.getPageNum(), page.getTotal(), page.getPages());
        return pageBO;
    }

    @Override
    public PageBO<PersonalHomoResultSetBO> listPersonalHomoResultSet(IdQuery query) {
        PersonalHomoResultSet queryCondition = new PersonalHomoResultSet();
        queryCondition.setPid(query.getId());
        Page page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<PersonalHomoResultSet> list = personalHomoResultSetService.findList(queryCondition);
        List<PersonalHomoResultSetBO> result = JSON.parseArray(JSON.toJSONString(list), PersonalHomoResultSetBO.class);
        for (PersonalHomoResultSetBO boTep : result) {
            VariableData variableData = variableDataService.getById(boTep.getVariableId());
            boTep.setVariableName(variableData.getName());
            boTep.setVariableMeaning(variableData.getMeaning());
        }

        PageBO<PersonalHomoResultSetBO> pageBO = new PageBO<PersonalHomoResultSetBO>(result, query.getPageSize(), query.getPageNum(), page.getTotal(), page.getPages());
        return pageBO;

    }

    @Override
    public List<PersonalHomoResultSetBO> listDataDetailSameCharts(Long id) {
        List<PersonalHomoResultSet> list = personalHomoResultSetService.listCharts(id);
        List<PersonalHomoResultSetBO> result = JSON.parseArray(JSON.toJSONString(list), PersonalHomoResultSetBO.class);
        for (PersonalHomoResultSetBO boTep : result) {
            VariableData variableData = variableDataService.getById(boTep.getVariableId());
            boTep.setVariableName(variableData.getName());
            boTep.setVariableMeaning(variableData.getMeaning());
        }
        return result;
    }

    @Override
    public List<ExperimentBO> getCanPublishTrain() {
        Experiment experiment = new Experiment();
        experiment.setStatus(Experiment.STATUS_TRAINING_OVER);
        // experiment.setReleasStatus(Experiment.RELEAS_NO);
        List<Experiment> list = experimentService.findList(experiment);
        //训练完成（可以调优和可以发布的实验）
        List<Experiment> filterList = null;
        if (!CollectionUtils.isEmpty(list)) {
            filterList = new ArrayList<>();
            for (Experiment experimentTemp : list) {
                if (experimentTemp.getPublishCount() <= 0) {
                    //可以正常发布的实验
                    filterList.add(experimentTemp);
                } else {
                    //需要判断是否是调优需求
                    if(checkTrainTuningLock(experimentTemp.getServiceId(),null)){
                        filterList.add(experimentTemp);
                    }
                }
            }
        }
        List<ExperimentBO> result = JSON.parseArray(JSON.toJSONString(filterList), ExperimentBO.class);
        return result;
    }

    @Override
    public List<ModelPerformanceBO> findModelPerformance(Long id) {
        ModelPerformance modelPerformance = new ModelPerformance();
        modelPerformance.setExperimentId(id);
        List<ModelPerformance> list = modelPerformanceService.findList(modelPerformance);
        List<ModelPerformanceBO> result = JSON.parseArray(JSON.toJSONString(list), ModelPerformanceBO.class);
        return result;
    }

    @Override
    public List<SampleGroupingBO> getGroupOptionName(Long experimentId, boolean isExceptionGroup, boolean isAllGroup) {
        List<SampleGrouping> sampleGroupings = sampleGroupingService.findListByExperimentId(experimentId, isExceptionGroup, isAllGroup);
        List<SampleGroupingBO> sampleGroupingBOList = JSON.parseArray(JSON.toJSONString(sampleGroupings), SampleGroupingBO.class);
        return sampleGroupingBOList;
    }

    @Override
    public List<BatchRecordBO> findBatchRecordBOList(BatchRecordBO batchRecordBO, boolean isTranFlag) {
        batchRecordBO.setTranFlag(isTranFlag);
        List<BatchRecord> batchRecords = batchRecordService.findBatchRecordList(batchRecordBO);
        List<BatchRecordBO> result = JSON.parseArray(JSON.toJSONString(batchRecords), BatchRecordBO.class);
        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public Long doubleCreate(ExperimentalTrainDoubleCreateBO bo) {
        Experiment experiment = experimentService.selectExperimentById(bo.getTrainId());
        if (experiment == null) {
            return null;
        }
        String experimentName = experiment.getName() + "二次创建实验" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        if (experimentName.length() > 100) {
        	return -1L;
        }
        experiment.setName(experimentName);
        experiment.setId(null);
        experiment.setParentId(bo.getTrainId());
        experiment.setStatus(Experiment.STATUS_FILTER);
        experiment.setReleasStatus(Experiment.RELEAS_NO);
        experiment.setCreateTime(System.currentTimeMillis());
        experiment.setTrainingTime(null);
        experiment.setReleaseTime(null);
        experiment.setTuningCount(0);
        experiment.setCreateUserId(UserUtils.getUser().getId());
        //experiment.setCreateUserId(1L);
        experiment.setRemark(null);
        experiment.setSampleReview(null);
        experiment.setModelUrl(null);
        experiment.setSegmentationStatisticsImageUrl(null);
        experiment.setBadTopCountImageUrl(null);
        experiment.setRocTestImageUrl(null);
        experiment.setRocTrainImageUrl(null);
        experiment.setRocValidateImageUrl(null);
        experiment.setKsTestImageUrl(null);
        experiment.setKsTrainImageUrl(null);
        experiment.setKsValidateImageUrl(null);
        experiment.setPreFinish(Experiment.PREFINISH_YES);
        experimentService.doubleCreate(experiment);

        if (experiment.getId() == null) {
            return null;
        }

        //构建参数
        if (!CollectionUtils.isEmpty(bo.getVariableIdList())) {
            List<VariableData> list = variableDataService.findDoubleCreateVariableDataList(bo.getVariableIdList());
            if (!CollectionUtils.isEmpty(list)) {
                for (VariableData v :
                        list) {
                    v.setId(null);
                    v.setExperimentId(experiment.getId());
                }

                variableDataService.insertVariableDataList(list);
            }
        }
        return experiment.getId();
    }

    @Override
    public List<ExperimentBO> findExperimentByServerId(Long serverId) {
        Experiment experiment = new Experiment();
        experiment.setServiceId(serverId);
        List<Experiment> list = experimentService.findList(experiment);
        List<ExperimentBO> result = JSON.parseArray(JSON.toJSONString(list), ExperimentBO.class);
        return result;
    }

    @Override
    public ExperimentBO getPublishExperimentByServerId(Long id) {
        Experiment experiment = new Experiment();
        experiment.setServiceId(id);
        experiment.setReleasStatus(Experiment.RELEAS_YES);
        List<Experiment> list = experimentService.findList(experiment);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        Experiment experiment1 = list.get(0);
        ExperimentBO bo = new ExperimentBO();
        BeanUtils.copyProperties(experiment1, bo);
        return bo;
    }

    @Override
    public List<ExperimentBO> list(ExperimentalTrainQuery query) {
        Experiment experiment = new Experiment();
        experiment.setStatus(query.getStatus());
        List<Experiment> list = experimentService.findList(experiment);
        List<ExperimentBO> result = JSON.parseArray(JSON.toJSONString(list), ExperimentBO.class);
        return result;
    }

    @Override
    public List<ExperimentBO> listByStatus(List<Integer> status) {
        Experiment experiment = new Experiment();
        experiment.setSearchStatusList(status);
        List<Experiment> list = experimentService.findList(experiment);
        List<ExperimentBO> result = JSON.parseArray(JSON.toJSONString(list), ExperimentBO.class);
        return result;
    }


    @Override
    public Long getLastServerId() {
        return experimentService.getLastServerId();
    }

    @Override
    public Long getGroupAllId(long trainId) {
        return sampleGroupingService.getAllGroupIdByTrainId(trainId);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updateName(ExperimentBO experimentBO) {
        return experimentService.updateName(experimentBO.getId(), experimentBO.getName());
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updateNameAndRemark(ExperimentBO experimentBO) {
        Experiment experiment = new Experiment();
        BeanUtils.copyProperties(experimentBO, experiment);
        return experimentService.updateNameAndRemark(experiment);
    }

    @Override
    public List<Long> findNotPublishExperimentIds(List<Long> ids) {
        return experimentService.findNotPublishExperimentIds(ids);
    }




    private PersonalInformation buildQueryCondition(BatchRecord batchRecord, FaceListDataQuery query) {
        PersonalInformation personal = new PersonalInformation();
        personal.setExperimentId(batchRecord.getExperimentId());
        personal.setBatchId(batchRecord.getId());
        personal.setGroupId(query.getGroupId());
        personal.setPersonalName(query.getName());
        personal.setPersonalCardId(query.getIdCard());
        personal.setPhoneNum(query.getPhone());
        personal.setPersonalId(query.getPersonalId());
        personal.setInputStartDate(query.getInputStartDate());
        personal.setInputEndDate(query.getInputStartEnd());
        personal.setSearchType(query.getSearchType());
        return personal;
    }

    private ExperimentalResultTopGroupBO quotaTopGroupCommon(Long experimentId, Integer dataType) {
        List<TopSortBO> response = quotaTopGroupDataCommon(experimentId, dataType, 1);
        List<TopSortBO> noResponse = quotaTopGroupDataCommon(experimentId, dataType, 2);
        ExperimentalResultTopGroupBO result = new ExperimentalResultTopGroupBO();
        result.setResponseDataList(response);
        result.setNoResponseDataList(noResponse);
        return result;
    }

    private List<TopSortBO> quotaTopGroupDataCommon(Long experimentId, Integer dataType, Integer responseType) {
        TopSort topSort = new TopSort();
        topSort.setExperimentId(experimentId);
        topSort.setResponseType(responseType);
        topSort.setDataSetType(dataType);
        List<TopSort> topSorts = topSortService.findList(topSort);
        List<TopSortBO> result = JSON.parseArray(JSON.toJSONString(topSorts), TopSortBO.class);
        return result;
    }

    @Override
    public void testPreprocess(long experimentId) {
        ExperimentBO experimentBO = experimentService.selectExperimentById(experimentId);
        trainService.preprocess(experimentBO);
    }

}
