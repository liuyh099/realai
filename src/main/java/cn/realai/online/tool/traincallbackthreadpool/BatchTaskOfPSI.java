package cn.realai.online.tool.traincallbackthreadpool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.realai.online.common.Constant;
import cn.realai.online.core.entity.Model;
import cn.realai.online.core.entity.PSICheckResult;
import cn.realai.online.core.entity.VariableData;
import cn.realai.online.core.service.ModelService;
import cn.realai.online.core.service.PsiCheckResultService;
import cn.realai.online.core.service.VariableDataService;
import cn.realai.online.tool.redis.RedisClientTemplate;
import cn.realai.online.util.SpringContextUtils;

public class BatchTaskOfPSI extends BaseBatchTask {

	private static Logger logger = LoggerFactory.getLogger(BatchTaskOfPSI.class);
	
	public BatchTaskOfPSI(Long experimentId, Long batchId, String redisKey) {
		this.experimentId = experimentId;
		this.batchId = batchId;
		this.redisKey = redisKey;
	}
	
	@Override
	public void run() {
		logger.info("BatchTaskOfPSI run. 当前线程信息, 线程数{}, 任务数{}", 
				ModelCallPool.modelCallPool.getPoolSize(), ModelCallPool.modelCallPool.getQueue().size());
		logger.info("BatchTaskOfPSI run. experimentId{}, batchId{}, redisKey{}", experimentId, batchId, redisKey);
		RedisClientTemplate redisClientTemplate = SpringContextUtils.getBean(RedisClientTemplate.class);
        String redisValue = redisClientTemplate.get(redisKey);
        List<PSICheckResult> list = JSON.parseArray(redisValue, PSICheckResult.class);
    	if (list == null || list.size() == 0) {
    		logger.info("BatchTaskOfPSI run psiValue is null, rediskey{}", redisKey);
    		return ;
    	}
    	
		PsiCheckResultService psiCheckResultService = SpringContextUtils.getBean(PsiCheckResultService.class);
		psiCheckResultService.deletePSIByExperimentId(experimentId);
		
		ModelService modelService = SpringContextUtils.getBean(ModelService.class);
		Model model = modelService.selectOnlineModel(null, experimentId);
		
		VariableDataService variableDataService = SpringContextUtils.getBean(VariableDataService.class);
        List<VariableData> vdList = variableDataService.findListByExperimentId(experimentId);
        Map<String, Long> vdMap = new HashMap<String, Long>();
        for (VariableData vd : vdList) {
            vdMap.put(vd.getName() + vd.getVariableType(), vd.getId());
        }
		for (PSICheckResult psi : list) {
			psi.setAler(psi.getPsi() > Constant.PSI_ALER_VALUE ? 2 : 1);//1.不预警 2.预警
			psi.setVariableId(vdMap.get(psi.getVariableName() + psi.getVariableType()));
			psi.setExperimentId(experimentId);
			psi.setModelId(model.getId());
		}
		psiCheckResultService.insertList(list);
		logger.info("BatchTaskOfPSI run. experimentId{}, batchId{}, size{}", experimentId, batchId, list.size());
		redisClientTemplate.delete(redisKey);
	}

}
