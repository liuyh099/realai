package cn.realai.online.core.bussiness.impl;

import cn.realai.online.core.bo.SampleWeightBO;
import cn.realai.online.core.bussiness.SampleWeightBussiness;
import cn.realai.online.core.entity.SampleWeight;
import cn.realai.online.core.entity.VariableData;
import cn.realai.online.core.query.ExperimentalResultWhileBoxQuery;
import cn.realai.online.core.query.GlobalVariableQuery;
import cn.realai.online.core.service.SampleWeightService;
import cn.realai.online.core.service.VariableDataService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SampleWeightBussinessImpl implements SampleWeightBussiness {

    @Autowired
    private SampleWeightService sampleWeightService;

    @Autowired
    private VariableDataService variableDataService;

    @Override
    public List<SampleWeightBO> getSampleWeightList(ExperimentalResultWhileBoxQuery experimentalResultWhileBoxQuery) {

        //执行条件查询
        SampleWeight sampleWeight = new SampleWeight();
        BeanUtils.copyProperties(experimentalResultWhileBoxQuery, sampleWeight);
        if (!"image".equals(experimentalResultWhileBoxQuery.getType())) {
            List<SampleWeightBO> boList = sampleWeightService.findList(sampleWeight);
            for (SampleWeightBO sampleWeightBO : boList) {

                    sampleWeightBO.setImgUrl(null);
                    VariableData variableData = variableDataService.getById(sampleWeightBO.getVariableId());
                    sampleWeightBO.setVariableName(variableData.getName());
                    sampleWeightBO.setVariableType(variableData.getVariableType());
                    sampleWeightBO.setMeaning(variableData.getMeaning());
            }
            return boList;
        }else {
            List<SampleWeightBO> boList = sampleWeightService.findImage(sampleWeight);
            for (SampleWeightBO sampleWeightBO : boList) {
                VariableData variableData = variableDataService.getById(sampleWeightBO.getVariableId());
                sampleWeightBO.setVariableName(variableData.getName());
                sampleWeightBO.setVariableType(variableData.getVariableType());
                sampleWeightBO.setMeaning(variableData.getMeaning());
            }
            return boList;
        }


    }

    @Override
    public List<SampleWeightBO> getSampleWeightList(GlobalVariableQuery globalVariableQuery) {

        //执行条件查询
        SampleWeight sampleWeight = new SampleWeight();
        BeanUtils.copyProperties(globalVariableQuery, sampleWeight);
        sampleWeight.setGroupId(globalVariableQuery.getGroupId());
        sampleWeight.setExperimentId(globalVariableQuery.getTrainId());
        sampleWeight.setVariableType(globalVariableQuery.getSampleType());
        if (!"image".equals(globalVariableQuery.getType())) {
            List<SampleWeightBO> boList = sampleWeightService.findList(sampleWeight);
            for (SampleWeightBO sampleWeightBO : boList) {
                sampleWeightBO.setImgUrl(null);
                VariableData variableData = variableDataService.getById(sampleWeightBO.getVariableId());
                sampleWeightBO.setVariableName(variableData.getName());
                sampleWeightBO.setVariableType(variableData.getVariableType());
                sampleWeightBO.setMeaning(variableData.getMeaning());
            }
            return boList;
        }else {
            List<SampleWeightBO> boList = sampleWeightService.findImage(sampleWeight);
            for (SampleWeightBO sampleWeightBO : boList) {
                VariableData variableData = variableDataService.getById(sampleWeightBO.getVariableId());
                sampleWeightBO.setVariableName(variableData.getName());
                sampleWeightBO.setVariableType(variableData.getVariableType());
                sampleWeightBO.setMeaning(variableData.getMeaning());
            }
            return boList;
        }
    }
}
