package cn.realai.online.core.service.impl;

import java.util.List;

import cn.realai.online.core.bo.SampleWeightBO;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.realai.online.core.dao.SampleWeightDao;
import cn.realai.online.core.entity.SampleWeight;
import cn.realai.online.core.service.SampleWeightService;

@Service
public class SampleWeightServiceImpl implements SampleWeightService {

	@Autowired
	private SampleWeightDao sampleWeightDao;
	
	@Override
	public void insertList(List<SampleWeight> swList) {
		if (swList == null || swList.size() == 0) {
			return ;
		}
		sampleWeightDao.insertList(swList);
	}

	@Override
	public List<SampleWeightBO> findList(SampleWeight sampleWeight) {
		List<SampleWeight> list = sampleWeightDao.findList(sampleWeight);
		List<SampleWeightBO> result = JSON.parseArray(JSON.toJSONString(list), SampleWeightBO.class);
		return result;
	}

}
