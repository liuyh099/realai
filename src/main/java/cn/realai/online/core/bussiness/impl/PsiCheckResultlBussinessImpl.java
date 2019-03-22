package cn.realai.online.core.bussiness.impl;

import cn.realai.online.core.bo.PsiResultBO;
import cn.realai.online.core.bussiness.PsiCheckResultBussiness;
import cn.realai.online.core.entity.PSICheckResult;
import cn.realai.online.core.service.PsiCheckResultService;
import cn.realai.online.core.vo.PsiCheckVO;
import cn.realai.online.core.vo.PsiResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：TODO
 *
 * @author admin
 * @create 2019-03-19 18:10
 */
@Service
public class PsiCheckResultlBussinessImpl implements PsiCheckResultBussiness {

    @Autowired
    private PsiCheckResultService psiChekcResultService;

    @Override
    public List<PsiResultVO> selectList(Long modelId) {
        List<PsiResultVO> psiResultVOList = new ArrayList<>();
        List<PsiResultBO> psiResultBOList = psiChekcResultService.selectList(modelId);
        if (psiResultBOList != null && !psiResultBOList.isEmpty()) {
            psiResultBOList.forEach(psi -> {
                PsiResultVO itemVO = new PsiResultVO();
                BeanUtils.copyProperties(psi, itemVO);
                itemVO.setAlerName(PSICheckResult.STATUS.getDesc(itemVO.getAler()));
                psiResultVOList.add(itemVO);
            });
        }
        return psiResultVOList;
    }

    @Override
    public PsiCheckVO checkPsi(Long modelId) {
        PsiCheckVO checkVO = new PsiCheckVO();
        checkVO.setModelId(modelId);
        Double maxPsi = psiChekcResultService.selectMaxPsi(modelId);
        checkVO.setMaxPsi(maxPsi);
        checkVO.setFlag(maxPsi > 0.1);
        return checkVO;
    }
}
