package cn.realai.online.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.realai.online.core.dao.SampleSummaryDao;
import cn.realai.online.core.entity.SampleSummary;
import cn.realai.online.core.service.SampleSummaryService;

@Service
public class SampleSummaryServiceImpl implements SampleSummaryService {

    @Autowired
    private SampleSummaryDao sampleSummaryDao;

    @Override
    public void insertList(List<SampleSummary> ssList) {
        if (ssList == null || ssList.size() == 0) {
            return;
        }
        sampleSummaryDao.insertList(ssList);
    }

    @Override
    public List<SampleSummary> findList(SampleSummary sampleSummary) {
        return sampleSummaryDao.findList(sampleSummary);
    }

	@Override
	public int deleteByExperimentId(Long experimentId) {
		return sampleSummaryDao.deleteByExperimentId(experimentId);
	}

}
