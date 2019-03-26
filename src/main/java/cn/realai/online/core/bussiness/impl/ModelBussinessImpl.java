package cn.realai.online.core.bussiness.impl;

import cn.realai.online.common.page.PageBO;
import cn.realai.online.core.bo.ModelBO;
import cn.realai.online.core.bo.ModelDetailBO;
import cn.realai.online.core.bo.ModelListBO;
import cn.realai.online.core.bussiness.ModelBussiness;
import cn.realai.online.core.entity.Experiment;
import cn.realai.online.core.entity.Model;
import cn.realai.online.core.entity.ModelPerformance;
import cn.realai.online.core.query.ModelListQuery;
import cn.realai.online.core.service.ExperimentService;
import cn.realai.online.core.service.ModelPerformanceService;
import cn.realai.online.core.service.ModelService;
import cn.realai.online.core.vo.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：TODO
 *
 * @author admin
 * @create 2019-03-19 18:10
 */
@Service
public class ModelBussinessImpl implements ModelBussiness {

    @Autowired
    private ModelService modelService;
    @Autowired
    private ModelPerformanceService modelPerformanceService;
    @Autowired
    private ExperimentService experimentService;

    @Override
    public PageBO<ModelListVO> pageList(ModelListQuery query) {
        //开启分页
        Page page = PageHelper.startPage(query.getPageNum(), query.getPageSize());

        //执行条件查询
        List<ModelListBO> list = modelService.selectList(query.getName(), query.getServiceId());

        //处理查询结果
        List<ModelListVO> voList = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            for (ModelListBO item : list) {
                ModelListVO voItem = new ModelListVO();
                BeanUtils.copyProperties(item, voItem);
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
    public int publish(ModelBO modelBO) {
        Experiment experiment = experimentService.selectExperimentById(modelBO.getExperimentId());
        experimentService.updateExperimentTrainStatus(modelBO.getExperimentId(), modelBO.getStatus());
        experimentService.updateExperimentOffline(modelBO.getExperimentId(), experiment.getServiceId(), Experiment.RELEAS_NO);
        //TODO 查询服务的ID 和处理服务上下线细节
        modelBO.setServiceId(experiment.getServiceId());
        modelBO.setCreateTime(System.currentTimeMillis());
        int count = modelService.insert(modelBO);
        return count;
    }

    @Override
    public Model getTrainByModelId(Long id) {
        return modelService.get(id);
    }

}
