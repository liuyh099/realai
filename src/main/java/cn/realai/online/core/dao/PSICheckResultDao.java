package cn.realai.online.core.dao;

import cn.realai.online.core.bo.PsiResultBO;
import cn.realai.online.core.entity.PSICheckResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PSICheckResultDao {
    //插入PSI
    Integer insert(PSICheckResult psi);

    //更新PSI
    Integer update(PSICheckResult psi);

    //获取PSI
    PSICheckResult get(Long id);

    //获取最大Psi
    Double selectMaxPsi(Long modelId);

    List<Map> selectMaxPsiList(List<Long> modelIds);

    //查询PSI列表
    List<PsiResultBO> selectList(@Param("modelId") Long modelId, @Param("variableType") Integer variableType, @Param("maxNum") Integer maxNum);

	int deletePSIByExperimentId(@Param("experimentId")Long experimentId);

	int insertList(@Param("list")List<PSICheckResult> list);

    /**
     * 查询有没有PSi 大于0.1
     * @return
     */
    Integer findMaxPsiTotal();

    Double selectPsiByServiceId(@Param("serviceId")Long experimentId);


}
