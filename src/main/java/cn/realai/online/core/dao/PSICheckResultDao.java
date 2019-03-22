package cn.realai.online.core.dao;

import cn.realai.online.core.bo.PsiResultBO;
import cn.realai.online.core.entity.PSICheckResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PSICheckResultDao {
    //插入PSI
    Integer insert(PSICheckResult psi);

    //更新PSI
    Integer update(PSICheckResult psi);

    //获取PSI
    PSICheckResult get(Long id);

    //获取最大Psi
    Double selectMaxPsi(Long modelId);

    //查询PSI列表
    List<PsiResultBO> selectList(@Param("modelId") Long modelId);

    /**
     * 查询有没有PSi 大于0.1
     * @return
     */
    Integer findMaxPsiTotal();
}
