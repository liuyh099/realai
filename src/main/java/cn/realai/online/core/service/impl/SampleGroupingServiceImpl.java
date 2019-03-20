package cn.realai.online.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.realai.online.core.entity.SampleGrouping;
import cn.realai.online.core.service.SampleGroupingService;
import cn.realai.online.core.dao.SampleGroupingDao;

@Service
public class SampleGroupingServiceImpl implements SampleGroupingService{

	@Autowired
	private SampleGroupingDao SampleGroupingDao;
	
	@Override
	public void insertList(List<SampleGrouping> sgList) {
		if (sgList == null || sgList.size() == 0) {
			return;
		}
		SampleGroupingDao.insertList(sgList);
	}

	@Override
	public List<SampleGrouping> findListByExperimentId(Long experimentId) {
		
		return null;
	}

}
