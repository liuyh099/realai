package cn.realai.online.core.bussiness.impl;

import cn.realai.online.calculation.TrainService;
import cn.realai.online.common.Constant;
import cn.realai.online.common.page.PageBO;
import cn.realai.online.core.bo.ModelBO;
import cn.realai.online.core.bo.ModelDetailBO;
import cn.realai.online.core.bo.ModelListBO;
import cn.realai.online.core.bo.ServiceDeployRecordBO;
import cn.realai.online.core.bussiness.ModelBussiness;
import cn.realai.online.core.bussiness.ServiceDeployRecordBussiness;
import cn.realai.online.core.bussiness.TuningRecordBussiness;
import cn.realai.online.core.entity.Experiment;
import cn.realai.online.core.entity.Model;
import cn.realai.online.core.entity.ModelPerformance;
import cn.realai.online.core.entity.TuningRecord;
import cn.realai.online.core.query.ModelListQuery;
import cn.realai.online.core.service.*;
import cn.realai.online.core.vo.*;
import cn.realai.online.lic.LicenseException;
import cn.realai.online.lic.ServiceLicenseCheck;
import cn.realai.online.userandperm.entity.User;
import cn.realai.online.util.UserUtils;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * 功能描述：TODO
 *
 * @author admin
 * @create 2019-03-19 18:10
 */
@Service
public class ModelBussinessImpl implements ModelBussiness {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ModelService modelService;
    @Autowired
    private ModelPerformanceService modelPerformanceService;
    @Autowired
    private ExperimentService experimentService;
    @Autowired
    private PsiCheckResultService psiChekcResultService;
    @Autowired
    private TuningRecordService tuningRecordService;

    @Autowired
    private TuningRecordBussiness tuningRecordBussiness;
    @Autowired
    private ServiceLicenseCheck serviceLicenseCheck;
    @Autowired
    private ServiceService serviceService;
    
    @Autowired
    private TrainService trainService;

    @Autowired
    private ServiceDeployRecordBussiness serviceDeployRecordBussiness;

    @Override
    public PageBO<ModelListVO> pageList(ModelListQuery query) {
        //开启分页
        Page page = PageHelper.startPage(query.getPageNum(), query.getPageSize());

        //执行条件查询
        List<ModelListBO> list = modelService.selectList(query.getName(), query.getServiceId());

        //处理查询结果
        List<ModelListVO> voList = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            List<Long> modelIds = new ArrayList<>();
            list.forEach(item -> {
                modelIds.add(item.getModelId());
            });
            List<Map> modelPsiList = psiChekcResultService.selectMaxPsiList(modelIds);
            List<TuningRecord> tuningRecords = tuningRecordService.findLatestListByModelIds(modelIds);
            for (ModelListBO item : list) {
                ModelListVO voItem = new ModelListVO();
                BeanUtils.copyProperties(item, voItem);
                voItem.setAlgorithm(item.getAlgorithmType());
                voItem.setTuningReason("新建");

                //处理PSI是否足够预警
                if (modelPsiList != null && !modelPsiList.isEmpty()) {
                    for (Map psi : modelPsiList) {
                        if (psi.get("modelId").equals(item.getModelId())) {
                            Double maxPsi = (Double) psi.get("maxPsi");
                            boolean flag = maxPsi > Constant.PSI_ALER_VALUE ? true : false;
                            voItem.setPsi(maxPsi);
                            voItem.setAler(flag);
                            break;
                        }
                    }
                }

                //设置调优原因
                if (tuningRecords != null && !tuningRecords.isEmpty()) {
                    for (TuningRecord record : tuningRecords) {
                        if (record.getModelId().equals(item.getModelId())) {
                            String reason = TuningRecord.TYPE.PSI.value.equalsIgnoreCase(record.getType()) ? "PSI调优":"强制调优";
                            voItem.setTuningReason(reason);
                            break;
                        }
                    }

                }
                voList.add(voItem);
            }
        }
        PageBO<ModelListVO> pageBO = new PageBO<>(voList, query.getPageSize(), query.getPageNum(), page.getTotal(), page.getPages());
        return pageBO;
    }

    @Override
    public ModelDetailVO selectModelDetail(Long modelId) {
        ModelDetailVO detailVO = new ModelDetailVO();
        //读取模型详情
        ModelDetailBO detailBO = modelService.selectDetail(modelId);
        if (detailBO != null) {
            BeanUtils.copyProperties(detailBO, detailVO);
            //处理服务类型
            String serviceTypeName = cn.realai.online.core.entity.Service.getServiceTypeName(detailBO.getServiceType(), detailBO.getServiceBusinessType());
            detailVO.setServiceTypeName(serviceTypeName);
            //处理调优原因
            detailVO.setTuningReason("新建");
            List<TuningRecord> tuningRecords = tuningRecordService.findLatestListByModelIds(Arrays.asList(modelId));
            if (tuningRecords != null && !tuningRecords.isEmpty()) {
                String reason = TuningRecord.TYPE.PSI.value.equals(tuningRecords.get(0).getType()) ? "PSI调优" : "强制调优";
                detailVO.setTuningReason(reason);
            }
        }
        //读取模型表现
        List<ModelPerformance> performanceList = modelPerformanceService.selectList(modelId);
        if (performanceList != null && !performanceList.isEmpty()) {
            List<ModelPerformanceVO> performanceVOList = new ArrayList<>();
            for (ModelPerformance item : performanceList) {
                ModelPerformanceVO itemVO = new ModelPerformanceVO();
                BeanUtils.copyProperties(item, itemVO);
                performanceVOList.add(itemVO);
            }
            detailVO.setPerformanceList(performanceVOList);
        }
        return detailVO;
    }

    @Override
    public List<ModelNameSelectVO> selectModelNameList(Long serviceId) {
        List<ModelNameSelectVO> list = new ArrayList<>();
        Model releasedModel = modelService.selectByServiceId(serviceId);
        if (releasedModel == null) {
            return list;
        }
        ModelNameSelectVO itemVO = new ModelNameSelectVO();
        itemVO.setId(releasedModel.getId());
        itemVO.setName(releasedModel.getName());
        list.add(itemVO);
        return list;
    }

    @Override
    public List<ModelNameSelectVO> selectAllModelNameList(Long serviceId) {
        List<Model> releasedModelList = modelService.selectAllModelNameList(serviceId);
        List<ModelNameSelectVO> list = JSON.parseArray(JSON.toJSONString(releasedModelList),ModelNameSelectVO.class);
        return list;
    }


    @Override
    public ModelSelectVO selectRecentModelNameList() {
        ModelSelectVO selectVO = new ModelSelectVO();
        Model latestModel = modelService.selectLatest();
        if (latestModel == null) {
            return selectVO;
        }
        cn.realai.online.core.entity.Service service = serviceService.get(latestModel.getServiceId());
        selectVO.setServiceId(latestModel.getServiceId());
        if (service != null) {
            selectVO.setServiceName(service.getName());
        }
        selectVO.setModelId(latestModel.getId());
        selectVO.setModelName(latestModel.getName());

        return selectVO;
    }

    @Override
    public Boolean checkName(String name) {

        Model model = new Model();
        model.setName(name);
        List<Model> models = modelService.findList(model);
        if (CollectionUtils.isEmpty(models)) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = false)
    public Map<String,Object> publish(ModelBO modelBO) {
        HashMap<String,Object> hashMap =new HashMap<>();
        Experiment experiment = experimentService.getById(modelBO.getExperimentId());
        if(experiment.getStatus()!=Experiment.STATUS_TRAINING_OVER){
            hashMap.put("status",false);
            hashMap.put("msg","训练未完成不可以发布!");
            return hashMap;
        }
        modelBO.setServiceId(experiment.getServiceId());
        if (modelBO.getTuningType() == null) {
            //如果不是调优，检查是否可以发布，如果已存在发布实验，那么不可以再发布实验
            List<Experiment> experiments = experimentService.findPublishByServiceId(modelBO.getServiceId());
            if (CollectionUtils.isEmpty(experiments)) {
                try {
                    serviceLicenseCheck.applyService(modelBO.getServiceId());
                    return publishModel(modelBO);
                } catch (LicenseException e) {
                    logger.error("服务发布失败");
                    hashMap.put("status",false);
                    hashMap.put("msg",e.getMessage());
                    return hashMap;
                }

            } else {
                logger.info("已经发布服务，不可以重新发布,服务ID=" + modelBO.getServiceId() + "实验ID" + modelBO.getExperimentId());
                hashMap.put("status",false);
                hashMap.put("msg","该服务已经存在已发布的实验，不可重复发布");
                return hashMap;
            }
        } else {
            //如果是调优，检查有没有调优记录，有的话调优
            TuningRecord tuningRecord = tuningRecordBussiness.selectValidTuningRecord(modelBO.getServiceId());
            if(ObjectUtils.isEmpty(tuningRecord) || !TuningRecord.STATUS.VALID.value.equals(tuningRecord.getStatus())){
                hashMap.put("status",false);
                hashMap.put("msg","当前不可以调优");
                return hashMap;
            }else {
                if(StringUtils.isNotBlank(tuningRecord.getSecurityKey())){
                    //强制调优
                    try {
                        serviceLicenseCheck.applyService(modelBO.getServiceId(),tuningRecord.getSecurityKey());
                        return  publishAndTuringReord(modelBO, tuningRecord);
                    } catch (LicenseException e) {
                        logger.error("使用调优秘钥失败,秘钥="+tuningRecord.getSecurityKey());
                        hashMap.put("status",false);
                        hashMap.put("msg",e.getMessage());
                        return hashMap;
                    }
                }else {
                    //PSI调优
                    try {
                        serviceLicenseCheck.applyService(modelBO.getServiceId());
                        return publishAndTuringReord(modelBO, tuningRecord);
                    } catch (LicenseException e) {
                        logger.error("PSI调优失败");
                        hashMap.put("status",false);
                        hashMap.put("msg",e.getMessage());
                        return hashMap;
                    }

                }
            }
        }
    }

    /**
     * 发布模型和更新调优记录
     * @param modelBO
     * @param tuningRecord
     * @return
     */
    private HashMap<String,Object> publishAndTuringReord(ModelBO modelBO, TuningRecord tuningRecord) {
        HashMap a = publishModel(modelBO);
        if ((boolean)a.get("status")) {
            tuningRecord.setStatus(TuningRecord.STATUS.USED.value);
            tuningRecordService.update(tuningRecord);
        }
        return a;
    }

    /**
     * 发布模型
     * @param modelBO
     * @return
     */
    private HashMap<String,Object> publishModel(ModelBO modelBO) {
        Experiment experiment = experimentService.selectExperimentById(modelBO.getExperimentId());
        experimentService.updateExperimentTrainStatus(modelBO.getExperimentId(), modelBO.getStatus(),System.currentTimeMillis());
        experimentService.updateExperimentOffline(modelBO.getExperimentId(), experiment.getServiceId(), Experiment.RELEAS_OFFINE);
        //serviceService.online(modelBO.getServiceId());
        modelBO.setServiceId(experiment.getServiceId());
        modelBO.setCreateTime(System.currentTimeMillis());
        modelBO.setCreateUserId(UserUtils.getUser().getId());
        //获取服务
        Integer tuningNo = modelService.selectCountByServiceId(experiment.getServiceId());
        modelBO.setTuningNo(tuningNo);
        int count = modelService.insert(modelBO);
        ServiceDeployRecordBO serviceDeployRecordBO = getServiceDeployRecordBO(modelBO);
        serviceDeployRecordBussiness.insert(serviceDeployRecordBO);

        count = trainService.experimentDeploy(modelBO.getExperimentId(), null);
        HashMap<String,Object> hashMap =new HashMap<>();
        if(count>0){
            modelService.offline(modelBO.getServiceId(),modelBO.getId(),Model.RELEASE_STATUS.NONE.value);
            hashMap.put("status",true);
        }else {
            hashMap.put("status",false);
            hashMap.put("msg","发布失败");
        }
        return hashMap;
    }

    private ServiceDeployRecordBO getServiceDeployRecordBO(ModelBO modelBO) {
        ServiceDeployRecordBO serviceDeployRecordBO =new ServiceDeployRecordBO();
        serviceDeployRecordBO.setServiceId(modelBO.getServiceId());
        serviceDeployRecordBO.setModelId(modelBO.getId());
        serviceDeployRecordBO.setCreateTime(System.currentTimeMillis());
        serviceDeployRecordBO.setExperimentId(modelBO.getExperimentId());
        serviceDeployRecordBO.setOpertionType(modelBO.getStatus());
        serviceDeployRecordBO.setRemark(modelBO.getRemark());
        serviceDeployRecordBO.setUserId(UserUtils.getUser().getId());
        return serviceDeployRecordBO;
    }

    @Override
    public Model getTrainByModelId(Long id) {
        return modelService.get(id);
    }

    @Override
    public List<ModelBO> findModelOptionHistory(Long serviceId) {
        List<Model> result = modelService.findModelOptionHistory(serviceId);
        return null;
    }


}
