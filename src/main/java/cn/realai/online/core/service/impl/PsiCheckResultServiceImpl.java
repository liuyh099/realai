package cn.realai.online.core.service.impl;

import cn.realai.online.core.bo.PsiResultBO;
import cn.realai.online.core.dao.PSICheckResultDao;
import cn.realai.online.core.service.PsiChekcResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能描述：TODO
 *
 * @author admin
 * @create 2019-03-20 11:35
 */
@Service
public class PsiCheckResultServiceImpl implements PsiChekcResultService{

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
}
