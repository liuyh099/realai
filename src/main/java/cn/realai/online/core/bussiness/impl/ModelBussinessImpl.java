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
import cn.realai.online.core.entity.Service;
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
import org.springframework.stereotype.Component;
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
@Component
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
            //List<TuningRecord> tuningRecords = tuningRecordService.findLatestListByModelIds(modelIds);
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

                //判断此模型是否可操作
                voItem.setHandle(1);
                if (Model.RELEASE_STATUS.ONLINE.value.equals(item.getReleaseStatus())) {
                    if (!serviceService.checkDiscard(item.getServiceId())) {
                        voItem.setHandle(0);
                    }
                }


                //设置模型状态名称
                String releaseStatusName = Optional.of(item)
                                            .map(ModelListBO::getReleaseStatus)
                                            .map(v -> Model.RELEASE_STATUS.valueOf(v.toUpperCase()).desc)
                                            .orElse(null);
                voItem.setReleaseStatusName(releaseStatusName);

                //设置调优原因
                String reason = Optional.of(item)
                                    .map(ModelListBO::getTuningType)
                                    .map(v -> TuningRecord.TYPE.PSI.value.equalsIgnoreCase(v) ? "PSI调优" : "强制调优")
                                    .orElse("新建");
                voItem.setTuningReason(reason);
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
            String reason = Optional.of(detailBO)
                                .map(ModelDetailBO::getTuningType)
                                .map(v -> TuningRecord.TYPE.PSI.value.equals(v) ? "PSI调优" : "强制调优")
                                .orElse("新建");
            detailVO.setTuningReason(reason);
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
    public List<ModelBO> findLastModelSelect() {
        List<Model> modelList = modelService.findLastModelSelect();
        List<ModelBO> result = JSON.parseArray(JSON.toJSONString(modelList),ModelBO.class);
        return result;
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
        TuningRecord tuningRecord = tuningRecordBussiness.selectValidTuningRecord(modelBO.getServiceId());
        if (tuningRecord == null) {
            //如果不是调优，检查是否可以发布，如果已存在发布实验，那么不可以再发布实验
            List<Experiment> experiments = experimentService.findPublishByServiceId(modelBO.getServiceId());
            if (CollectionUtils.isEmpty(experiments)) {
                try {
                    serviceLicenseCheck.applyService(modelBO.getServiceId());
                    return publishModel(modelBO);
                } catch (LicenseException e) {
                    logger.error(modelBO.getServiceId()+"服务发布失败:", e);
                    hashMap.put("status",false);
                    hashMap.put("msg",e.getMessage());
                    return hashMap;
                }

            } else {
                logger.info(modelBO.getServiceId()+"已经发布服务，不可以重新发布,服务ID=" + modelBO.getServiceId() + "实验ID" + modelBO.getExperimentId());
                hashMap.put("status",false);
                hashMap.put("msg","该服务已经存在已发布的实验，不可重复发布");
                return hashMap;
            }
        } else {
            //如果是调优，检查有没有调优记录，有的话调优
            if(ObjectUtils.isEmpty(tuningRecord) || !TuningRecord.STATUS.VALID.value.equals(tuningRecord.getStatus())){
                hashMap.put("status",false);
                hashMap.put("msg","当前不可以调优");
                return hashMap;
            }else {
                if(TuningRecord.TYPE.KEY.value.equals(tuningRecord.getType())){
                    //强制调优
                    try {
                        serviceLicenseCheck.applyService(modelBO.getServiceId(),tuningRecord.getSecurityKey());
                        return  publishAndTuringReord(modelBO, tuningRecord);
                    } catch (LicenseException e) {
                        logger.error(modelBO.getServiceId()+"使用调优秘钥失败,秘钥="+tuningRecord.getSecurityKey());
                        hashMap.put("status",false);
                        hashMap.put("msg",e.getMessage());
                        return hashMap;
                    }
                }else {
                    //PSI调优

                    //检查PSI调优是否可行
                    Double d=psiChekcResultService.selectPsiByServiceId(modelBO.getServiceId());
                    if(d<=0.1){
                        tuningRecord.setStatus(TuningRecord.STATUS.INVALID.value);
                        tuningRecordService.update(tuningRecord);
                        logger.error(modelBO.getServiceId()+"PSI调优失败,PSI实际值为:"+d);
                        hashMap.put("status",false);
                        hashMap.put("msg","psi调优失败");
                        return hashMap;
                    }
                    try {
                        serviceLicenseCheck.applyService(modelBO.getServiceId());
                        return publishAndTuringReord(modelBO, tuningRecord);
                    } catch (LicenseException e) {
                        logger.error(modelBO.getServiceId()+"PSI调优失败");
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
        modelBO.setTuningRecordId(tuningRecord.getId()); //模型关联调优ID
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

        modelBO.setServiceId(experiment.getServiceId());
        modelBO.setCreateTime(System.currentTimeMillis());
        modelBO.setCreateUserId(UserUtils.getUser().getId());
        //获取服务
        Integer tuningNo = modelService.selectCountByServiceId(experiment.getServiceId());
        if(tuningNo!=null && tuningNo==0){
            tuningNo=null;
        }
        experimentService.updateExperimentTrainStatus(modelBO.getExperimentId(), modelBO.getStatus(),System.currentTimeMillis(),tuningNo);
        experimentService.updateExperimentOffline(modelBO.getExperimentId(), experiment.getServiceId(), Experiment.RELEAS_OFFINE);
        modelBO.setTuningNo(Optional.ofNullable(tuningNo).orElse(0));
        
        ServiceDeployRecordBO serviceDeployRecordBO = getServiceDeployRecordBO(modelBO);
        serviceDeployRecordBussiness.insert(serviceDeployRecordBO);
        
        String modelDownUrl = trainService.experimentDeploy(modelBO.getExperimentId(), null, modelBO.getReleaseStatus(), 
        		serviceService.get(modelBO.getServiceId()).getSecretKey(), modelBO.getServiceId());
        HashMap<String,Object> hashMap =new HashMap<>();
        //设置模型下载地址，并创建模型
    	modelBO.setModelDownUrl(modelDownUrl);
    	modelService.insert(modelBO);
    	//就模型下线
        modelService.offline(modelBO.getServiceId(),modelBO.getId(),Model.RELEASE_STATUS.NONE.value);
        hashMap.put("status",true);
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
