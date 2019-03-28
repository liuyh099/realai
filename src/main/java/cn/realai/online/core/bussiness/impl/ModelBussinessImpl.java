package cn.realai.online.core.bussiness.impl;

import cn.realai.online.common.Constant;
import cn.realai.online.common.page.PageBO;
import cn.realai.online.core.bo.ModelBO;
import cn.realai.online.core.bo.ModelDetailBO;
import cn.realai.online.core.bo.ModelListBO;
import cn.realai.online.core.bussiness.ModelBussiness;
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
                voItem.setTuningReason("新建");

                //处理PSI是否足够预警
                if (modelPsiList != null && !modelPsiList.isEmpty()) {
                    for (Map psi : modelPsiList) {
                        if (psi.get("modelId") == item.getModelId()) {
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
                        if (record.getModelId() == item.getModelId()) {
                            String reason = TuningRecord.TYPE.PSI.value.equals(record.getType()) ? "PSI调优" : "强制调优";
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
        List<ModelListBO> modelListBOList = modelService.selectList(null, serviceId);
        if (modelListBOList == null || modelListBOList.isEmpty()) {
            return list;
        }
        modelListBOList.forEach(item -> {
            ModelNameSelectVO itemVO = new ModelNameSelectVO();
            itemVO.setId(item.getModelId());
            itemVO.setName(item.getModelName());
            list.add(itemVO);
        });
        return list;
    }

    @Override
    public ModelSelectVO selectRecentModelNameList(Long modelId) {
        ModelSelectVO selectVO = new ModelSelectVO();
        if (modelId == null) {
            //未传模型ID则查询最近发布的服务模型
            Model latestModel = modelService.selectLatest();
            if (latestModel == null) {
                return selectVO;
            }
            selectVO.setServiceId(latestModel.getServiceId());
            selectVO.setModelId(latestModel.getId());
            selectVO.setModelName(latestModel.getName());
        } else {
            //直接根据模型ID查询
            Model model = modelService.get(modelId);
            if (model == null) {
                return selectVO;
            }
            selectVO.setServiceId(model.getServiceId());
            selectVO.setModelId(model.getId());
            selectVO.setModelName(model.getName());
        }
        //根据服务ID查询模型
        List<ModelListBO> modelList = modelService.selectList(null, selectVO.getServiceId());
        if (modelList != null && !modelList.isEmpty()) {
            selectVO.setServiceName(modelList.get(0).getServiceName());
            List<ModelNameSelectVO> list = new ArrayList<>();
            modelList.forEach(itemBO -> {
                ModelNameSelectVO itemVO = new ModelNameSelectVO();
                itemVO.setId(itemBO.getModelId());
                itemVO.setName(itemBO.getModelName());
                list.add(itemVO);
            });
            selectVO.setModelList(list);
        }
        return null;
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
                return publishModel(modelBO);
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
                if(StringUtils.isNotBlank(tuningRecord.getSecuriyKey())){
                    //强制调优
                    try {
                        serviceLicenseCheck.applyService(modelBO.getServiceId(),tuningRecord.getSecuriyKey());
                        return  publishAndTuringReord(modelBO, tuningRecord);
                    } catch (LicenseException e) {
                        logger.error("使用调优秘钥失败",e);
                        hashMap.put("status",false);
                        hashMap.put("msg","调优秘钥不正确");
                        return hashMap;
                    }
                }else {
                    //PSI调优
                    return publishAndTuringReord(modelBO, tuningRecord);
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
        experimentService.updateExperimentOffline(modelBO.getExperimentId(), experiment.getServiceId(), Experiment.RELEAS_NO);
        modelBO.setServiceId(experiment.getServiceId());
        modelBO.setCreateTime(System.currentTimeMillis());
        int count = modelService.insert(modelBO);

        HashMap<String,Object> hashMap =new HashMap<>();
        if(count>0){
            hashMap.put("status",true);
        }else {
            hashMap.put("status",false);
            hashMap.put("msg","发布失败");
        }
        return hashMap;
    }

    @Override
    public Model getTrainByModelId(Long id) {
        return modelService.get(id);
    }

}
