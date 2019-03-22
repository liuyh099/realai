package cn.realai.online.core.bussiness.impl;

import cn.realai.online.calculation.TrainService;
import cn.realai.online.common.page.PageBO;
import cn.realai.online.core.bo.*;
import cn.realai.online.core.bussiness.ExperimentalTrainBussiness;
import cn.realai.online.core.entity.*;
import cn.realai.online.core.query.ExperimentalTrainCreateModelDataQuery;
import cn.realai.online.core.query.ExperimentalTrainQuery;
import cn.realai.online.core.query.FaceListDataQuery;
import cn.realai.online.core.query.IdQuery;
import cn.realai.online.core.service.*;
import cn.realai.online.util.UserUtils;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 实验训练的业务实现
 */
@Service
@Transactional(readOnly = true)
public class ExperimentalTrainBussinessImpl implements ExperimentalTrainBussiness {

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
        List<Experiment> list = experimentService.findList(experiment);
        List<ExperimentBO> result = JSON.parseArray(JSON.toJSONString(list), ExperimentBO.class);
        //BeanUtilsBean.copyProperties(list,result);
        //处理查询结果
        //List<ExperimentalTrainVO> result = JSON.parseArray(JSON.toJSONString(list), ExperimentalTrainVO.class);
        PageBO<ExperimentBO> pageBO = new PageBO<ExperimentBO>(result, experimentalTrainQuery.getPageSize(), experimentalTrainQuery.getPageNum(), page.getTotal(), page.getPages());
        return pageBO;
    }

    /**
     * 根据实验训练id删除实验训练
     *
     * @param ids 实验训练id集合
     */
    @Override
    @Transactional(readOnly = false)
    public Integer deleteExperimentByIds(List<Long> ids) {
        int count = experimentService.deleteExperimentByIds(ids);
        return count;
    }

    /*
     * 训练
     * @param trainId 实验id
     */
    @Override
    @Transactional(readOnly = false)
    public int train(long experimentId) {
        //获取训练锁
        MLock mlock = experimentService.getExperimentTrainMLockInstance(experimentId);
        if (!mlock.tryLock()) {
            return -1;
        }

        //查询需要删除的列
        HomoAndHetroBO deleteVariableData = variableDataService.selectDeleteByExperimentId(experimentId);

        //修改试验状态
        int ret = experimentService.updateExperimentStatus(experimentId, Experiment.STATUS_TRAINING);
        ExperimentBO experimentBO = experimentService.selectExperimentById(experimentId);
        trainService.training(experimentBO, 0L, deleteVariableData.getHomoList(), deleteVariableData.getHetroList());
        return ret;
    }

    @Override
    public void testPreprocess(long experimentId) {
        ExperimentBO experimentBO = experimentService.selectExperimentById(experimentId);
        trainService.preprocess(experimentBO);
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
        return experimentService.insert(experiment);
    }

    @Override
    public boolean checkTrainName(String name, Long id) {
        return experimentService.checkTrainName(name, id);

    }

    @Override
    public ExperimentBO selectById(Long trainId) {
        Experiment experiment = experimentService.selectExperimentById(trainId);
        ExperimentBO experimentBO = new ExperimentBO();
        BeanUtils.copyProperties(experiment, experimentBO);
        return experimentBO;
    }

    @Override
    @Transactional(readOnly = false)
    public Integer selectFileUpdate(ExperimentBO experimentBO) {
        Experiment experiment = new Experiment();
        BeanUtils.copyProperties(experimentBO, experiment);
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
                return null;
            }
            List<VariableDataBO> result = JSON.parseArray(JSON.toJSONString(list), VariableDataBO.class);
            PageBO<VariableDataBO> pageBO = new PageBO<VariableDataBO>(result, query.getPageSize(), query.getPageNum(), page.getTotal(), page.getPages());
            return pageBO;
        }
        return null;
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteVariableData(Long experimentId, List<Long> ids) {
        variableDataService.deleteVariableData(experimentId, ids);
    }

    @Override
    public ExperimentalResultQuatoBO quota(Long experimentId) {

        //获得评估的结果集
        Experiment experiment = experimentService.selectExperimentById(experimentId);

        //TODO 去获取服务
        List<ExperimentResultSetBO> trainResultSetListBO = quotaCommon(Experiment.DATA_SET_TRAIN, experimentId);
        List<ExperimentResultSetBO> testResultSetListBO = quotaCommon(Experiment.DATA_SET_TEST, experimentId);
        List<ExperimentResultSetBO> validResultSetListBO = quotaCommon(Experiment.DATA_SET_VALID, experimentId);
        ExperimentalResultQuatoBO experimentalResultQuatoBO = new ExperimentalResultQuatoBO();
        experimentalResultQuatoBO.setModel(1);
        experimentalResultQuatoBO.setTestResultList(testResultSetListBO);
        experimentalResultQuatoBO.setTrainResultList(trainResultSetListBO);
        experimentalResultQuatoBO.setValidateResultList(validResultSetListBO);
        return experimentalResultQuatoBO;
    }

    private List<ExperimentResultSetBO> quotaCommon(Integer dataSetType, Long experimentId) {
        ExperimentResultSet experimentResultSet = new ExperimentResultSet();
        experimentResultSet.setExperimentId(experimentId);
        experimentResultSet.setDataSetType(dataSetType);
        List<ExperimentResultSet> resultSetList = resultSetService.findList(experimentResultSet);
        List<ExperimentResultSetBO> resultSetListBO = JSON.parseArray(JSON.toJSONString(resultSetList), ExperimentResultSetBO.class);
        return resultSetListBO;
    }


    @Override
    public List<ExperimentResultSetBO> quotaGroup(Long groupId) {
        ExperimentResultSet experimentResultSet = new ExperimentResultSet();
        experimentResultSet.setParentId(groupId);
        List<ExperimentResultSet> validResultSetList = resultSetService.findList(experimentResultSet);
        List<ExperimentResultSetBO> resultList = JSON.parseArray(JSON.toJSONString(validResultSetList), ExperimentResultSetBO.class);
        return resultList;
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
        return result;
    }

    @Override
    public List<PersonalHetroResultSetBO> listDataDetailTopTen(Long id) {
        PersonalHetroResultSet query = new PersonalHetroResultSet();
        query.setPid(id);
        PageHelper.startPage(1, 10);
        List<PersonalHetroResultSet> list = personalHetroResultSetService.findList(query);
        List<PersonalHetroResultSetBO> bo = JSON.parseArray(JSON.toJSONString(list), PersonalHetroResultSetBO.class);
        //TODO 去关联变量表数据
        return bo;
    }

    @Override
    public PageBO<PersonalHetroResultSetBO> listPersonalHetroResultSet(IdQuery query) {
        PersonalHetroResultSet queryCondition = new PersonalHetroResultSet();
        queryCondition.setPid(query.getId());
        Page page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<PersonalHetroResultSet> list = personalHetroResultSetService.findList(queryCondition);
        List<PersonalHetroResultSetBO> result = JSON.parseArray(JSON.toJSONString(list), PersonalHetroResultSetBO.class);
        //TODO 去关联变量表数据

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
        //TODO 去关联变量表数据

        PageBO<PersonalHomoResultSetBO> pageBO = new PageBO<PersonalHomoResultSetBO>(result, query.getPageSize(), query.getPageNum(), page.getTotal(), page.getPages());
        return pageBO;

    }

    @Override
    public List<PersonalHomoResultSetBO> listDataDetailSameCharts(Long id) {
        List<PersonalHomoResultSet> list = personalHomoResultSetService.listCharts(id);
        List<PersonalHomoResultSetBO> result = JSON.parseArray(JSON.toJSONString(list), PersonalHomoResultSetBO.class);
        return result;
    }

    @Override
    public List<ExperimentBO> getCanPublishTrain() {
        Experiment experiment = new Experiment();
        experiment.setStatus(Experiment.STATUS_TRAINING_OVER);
        experiment.setReleasStatus(Experiment.RELEAS_YES);
        List<Experiment> list = experimentService.findList(experiment);
        List<ExperimentBO> result = JSON.parseArray(JSON.toJSONString(list), ExperimentBO.class);
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
    public List<SampleGroupingBO> getGroupOptionName(Long experimentId) {
        List<SampleGrouping> sampleGroupings = sampleGroupingService.findListByExperimentId(experimentId);
        List<SampleGroupingBO> sampleGroupingBOList = JSON.parseArray(JSON.toJSONString(sampleGroupings), SampleGroupingBO.class);
        return sampleGroupingBOList;
    }

    @Override
    public List<BatchRecordBO> findBatchRecordBOList(BatchRecordBO batchRecordBO, boolean isTranFlag) {

        List<BatchRecord> batchRecords = batchRecordService.findBatchRecordList(batchRecordBO, isTranFlag);

        return null;
    }

    @Override
    public Boolean doubleCreate(ExperimentalTrainDoubleCreateBO bo) {
        Experiment experiment = experimentService.selectExperimentById(bo.getTrainId());
        if(experiment==null){
            return false;
        }
        experiment.setId(null);
        experiment.setName(experiment.getName()+"-01");
        experiment.setStatus(Experiment.STATUS_PARAM);
        experiment.setReleasStatus(Experiment.RELEAS_NO);
        experiment.setCreateTime(System.currentTimeMillis());
        experiment.setTrainingTime(null);
        experiment.setReleaseTime(null);
        experiment.setTuningCount(0);
        experiment.setCreateUserId(UserUtils.getUser().getId());
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
        experiment.setPreFinish(1);

        //构建参数
//        Valida
//        variableDataService.findList()；

        return null;
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
        return personal;
    }

    private ExperimentalResultTopGroupBO quotaTopGroupCommon(Long experimentId, Integer dataType) {
        List<TopSortBO> response = quotaTopGroupDataCommon(experimentId, dataType, 1);
        List<TopSortBO> noResponse = quotaTopGroupDataCommon(experimentId, dataType, 1);
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
}
