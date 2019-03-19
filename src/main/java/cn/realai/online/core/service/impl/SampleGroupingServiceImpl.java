package cn.realai.online.core.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.realai.online.core.entity.SampleGrouping;
import cn.realai.online.core.service.SampleGroupingService;

@Service
public class SampleGroupingServiceImpl implements SampleGroupingService{

	@Override
	public void insertList(List<SampleGrouping> sgList) {
		
	}

	@Override
	public List<SampleGrouping> findListByExperimentId(Long experimentId) {
		
		return null;
	}

}
