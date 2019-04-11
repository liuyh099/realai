package cn.realai.online.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.realai.online.core.dao.TopSortDao;
import cn.realai.online.core.entity.TopSort;
import cn.realai.online.core.service.TopSortService;

@Service
public class TopSortServiceImpl implements TopSortService {

    @Autowired
    private TopSortDao topSortDao;

    @Override
    public void insertList(List<TopSort> tsList) {
        if (tsList == null || tsList.size() == 0) {
            return;
        }
        for (TopSort ts : tsList) {
            ts.setCreateTime(System.currentTimeMillis());
        }
        topSortDao.insertList(tsList);
    }

    @Override
    public List<TopSort> findList(TopSort topSort) {
        return topSortDao.findList(topSort);
    }

	@Override
	public int deleteByExperimentId(Long experimentId) {
		return topSortDao.deleteByExperimentId(experimentId);
	}

}
