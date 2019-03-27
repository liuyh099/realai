package cn.realai.online.core.service.impl;

import cn.realai.online.core.bo.PsiResultBO;
import cn.realai.online.core.dao.PSICheckResultDao;
import cn.realai.online.core.entity.PSICheckResult;
import cn.realai.online.core.service.PsiCheckResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 功能描述：TODO
 *
 * @author admin
 * @create 2019-03-20 11:35
 */
@Service
public class PsiCheckResultServiceImpl implements PsiCheckResultService {

    @Autowired
    private PSICheckResultDao psiCheckResultDao;

    @Override
    public List<PsiResultBO> selectList(Long modelId) {
        return psiCheckResultDao.selectList(modelId);
    }

    @Override
    public Double selectMaxPsi(long modelId) {
        return psiCheckResultDao.selectMaxPsi(modelId);
    }

    @Override
    public List<Map> selectMaxPsiList(List<Long> modelIds) {

        List<Map> map = psiCheckResultDao.selectMaxPsiList(modelIds);
        return map;
    }

    @Override
    public Integer findMaxPsiTotal() {
        return psiCheckResultDao.findMaxPsiTotal();
    }

	@Override
	public int deletePSIByExperimentId(Long experimentId) {
		return psiCheckResultDao.deletePSIByExperimentId(experimentId);
	}

	@Override
	public int insertList(List<PSICheckResult> list) {
		return psiCheckResultDao.insertList(list);
	}

}
