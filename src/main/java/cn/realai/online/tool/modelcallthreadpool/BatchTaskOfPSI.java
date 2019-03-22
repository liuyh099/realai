package cn.realai.online.tool.modelcallthreadpool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import cn.realai.online.common.Constant;
import cn.realai.online.core.entity.PSICheckResult;
import cn.realai.online.core.entity.VariableData;
import cn.realai.online.core.service.PsiCheckResultService;
import cn.realai.online.core.service.VariableDataService;
import cn.realai.online.tool.redis.RedisClientTemplate;
import cn.realai.online.util.SpringContextUtils;

public class BatchTaskOfPSI extends BaseBatchTask {

	public BatchTaskOfPSI(Long experimentId, Long batchId, String redisKey) {
		this.experimentId = experimentId;
		this.batchId = batchId;
		this.redisKey = redisKey;
	}
	
	@Override
	public void run() {
		RedisClientTemplate redisClientTemplate = SpringContextUtils.getBean(RedisClientTemplate.class);
        String redisValue = redisClientTemplate.get(redisKey);
        List<PSICheckResult> list = JSON.parseArray(redisValue, PSICheckResult.class);
    	if (list == null || list.size() == 0) {
    		return ;
    	}
    	
		PsiCheckResultService psiCheckResultService = SpringContextUtils.getBean(PsiCheckResultService.class);
		psiCheckResultService.deletePSIByExperimentId(experimentId);
		
		VariableDataService variableDataService = SpringContextUtils.getBean(VariableDataService.class);
        List<VariableData> vdList = variableDataService.findListByExperimentId(experimentId);
        Map<String, Long> vdMap = new HashMap<String, Long>();
        for (VariableData vd : vdList) {
            vdMap.put(vd.getName() + vd.getVariableType(), vd.getId());
        }
		for (PSICheckResult psi : list) {
			psi.setAler(psi.getPsi() > Constant.PSI_ALER_VALUE ? 2 : 1);//1.不预警 2.预警
			psi.setVariableId(vdMap.get(psi.getVariableName() + psi.getVariableType()));
		}
		psiCheckResultService.insertList(list);
	}

}
