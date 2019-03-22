package cn.realai.online.core.bussiness;

import cn.realai.online.core.vo.PsiCheckVO;
import cn.realai.online.core.vo.PsiResultVO;

import java.util.List;

public interface PsiCheckResultBussiness {

    List<PsiResultVO> selectList(Long modelId);

    PsiCheckVO checkPsi(Long modelId);

    /**
     * psi检查
     * @return
     */
    boolean psiCheckNotice();
}
