package cn.realai.online.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.realai.online.core.dao.ExperimentResultSetDao;
import cn.realai.online.core.entity.ExperimentResultSet;
import cn.realai.online.core.service.ExperimentResultSetService;

@Service
public class ExperimentResultSetServiceImpl implements ExperimentResultSetService {

    @Autowired
    private ExperimentResultSetDao experimentResultSetDao;

    @Override
    public void insertList(List<ExperimentResultSet> ersList) {
        if (ersList == null || ersList.size() == 0) {
            return;
        }
        experimentResultSetDao.insertList(ersList);
    }

    @Override
    public List<ExperimentResultSet> findList(ExperimentResultSet experimentResultSet) {
        return experimentResultSetDao.findList(experimentResultSet);
    }

	@Override
	public int deleteByExperimentId(Long experimentId) {
		return experimentResultSetDao.deleteByExperimentId(experimentId);
	}

}
