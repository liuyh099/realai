package cn.realai.online.core.bussiness.impl;

import cn.realai.online.common.page.PageBO;
import cn.realai.online.core.bo.ExperimentBO;
import cn.realai.online.core.bussiness.ExperimentalTrainBusiness;
import cn.realai.online.core.entity.Experiment;
import cn.realai.online.core.query.ExperimentalTrainQuery;
import cn.realai.online.core.service.ExperimentService;
import cn.realai.online.core.vo.ExperimentalTrainVO;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ExperimentalTrainBusinessImpl implements ExperimentalTrainBusiness {

    @Autowired
    private ExperimentService experimentService;

	@Override
	public PageBO<ExperimentalTrainVO> pageList(ExperimentalTrainQuery experimentalTrainQuery) {
		PageBO<ExperimentalTrainVO> pageBO = new PageBO<>();
		Page page = PageHelper.startPage(experimentalTrainQuery.getPageNum(), experimentalTrainQuery.getPageSize());
		Experiment experiment = new Experiment();
		BeanUtils.copyProperties(experimentalTrainQuery, experiment);
		List<ExperimentBO> list = experimentService.findList(experiment);
		List<ExperimentalTrainVO> result = JSON.parseArray(JSON.toJSONString(list), ExperimentalTrainVO.class);
		pageBO.setPageContent(result);
		pageBO.setCount(page.getTotal());
		pageBO.setPageNum(experimentalTrainQuery.getPageNum());
		pageBO.setPageSize(experimentalTrainQuery.getPageSize());
		pageBO.setTotalPage(page.getPages());
		return pageBO;
	}
}
