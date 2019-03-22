package cn.realai.online.core.bussiness.impl;

import cn.realai.online.common.page.PageBO;
import cn.realai.online.core.bo.SampleWeightBO;
import cn.realai.online.core.bussiness.SampleWeightBussiness;
import cn.realai.online.core.entity.SampleWeight;
import cn.realai.online.core.query.ExperimentalResultWhileBoxQuery;
import cn.realai.online.core.query.GlobalVariableQuery;
import cn.realai.online.core.service.SampleWeightService;
import cn.realai.online.core.vo.WhileBoxScoreCardVO;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SampleWeightBussinessImpl implements SampleWeightBussiness {

    @Autowired
    private SampleWeightService sampleWeightService;

    @Override
    public PageBO<WhileBoxScoreCardVO> pageBO(ExperimentalResultWhileBoxQuery experimentalResultWhileBoxQuery) {
        //开启分页
        Page page = PageHelper.startPage(experimentalResultWhileBoxQuery.getPageNum(), experimentalResultWhileBoxQuery.getPageSize());

        //执行条件查询
        SampleWeight sampleWeight = new SampleWeight();
        BeanUtils.copyProperties(experimentalResultWhileBoxQuery, sampleWeight);
        List<SampleWeightBO> boList = sampleWeightService.findList(sampleWeight);
        //处理查询结果
        List<WhileBoxScoreCardVO> result = JSON.parseArray(JSON.toJSONString(boList), WhileBoxScoreCardVO.class);
        PageBO<WhileBoxScoreCardVO> pageBO = new PageBO<WhileBoxScoreCardVO>(result, experimentalResultWhileBoxQuery.getPageSize(), experimentalResultWhileBoxQuery.getPageNum(), page.getTotal(), page.getPages());
        return pageBO;
    }

    @Override
    public PageBO<WhileBoxScoreCardVO> pageBO(GlobalVariableQuery globalVariableQuery) {
        //开启分页
        Page page = PageHelper.startPage(globalVariableQuery.getPageNum(), globalVariableQuery.getPageSize());

        //执行条件查询
        SampleWeight sampleWeight = new SampleWeight();
        BeanUtils.copyProperties(globalVariableQuery, sampleWeight);
        List<SampleWeightBO> boList = sampleWeightService.findList(sampleWeight);
        //处理查询结果
        List<WhileBoxScoreCardVO> result = JSON.parseArray(JSON.toJSONString(boList), WhileBoxScoreCardVO.class);
        PageBO<WhileBoxScoreCardVO> pageBO = new PageBO<WhileBoxScoreCardVO>(result, globalVariableQuery.getPageSize(), globalVariableQuery.getPageNum(), page.getTotal(), page.getPages());
        return pageBO;
    }
}
