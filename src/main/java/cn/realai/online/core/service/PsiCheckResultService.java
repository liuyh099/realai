package cn.realai.online.core.service;


import cn.realai.online.core.bo.PsiResultBO;
import cn.realai.online.core.entity.PSICheckResult;

import java.util.List;

/**
 * 功能描述：TODO
 *
 * @author admin
 * @create 2019-03-19 16:51
 */
public interface PsiCheckResultService {

    //查询模型列表
    List<PsiResultBO> selectList(Long modelId);

    //获取最大Psi
    Double selectMaxPsi(long modelId);

    //删除psi记录
	int deletePSIByExperimentId(Long experimentId);

	//批量插入psi记录
	int insertList(List<PSICheckResult> list);
	
}
