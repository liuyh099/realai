package cn.realai.online.core.service;


import cn.realai.online.core.bo.PsiResultBO;

import java.util.List;

/**
 * 功能描述：TODO
 *
 * @author admin
 * @create 2019-03-19 16:51
 */
public interface PsiChekcResultService {

    //查询模型列表
    List<PsiResultBO> selectList(Long modelId);

    //获取最大Psi
    Double selectMaxPsi(long modelId);

    /**
     * 查询有没有psi大于0.1
     * @return
     */
    Integer findMaxPsiTotal();
}
