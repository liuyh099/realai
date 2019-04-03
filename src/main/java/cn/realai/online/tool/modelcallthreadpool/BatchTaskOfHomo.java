package cn.realai.online.tool.modelcallthreadpool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.realai.online.core.entity.PersonalHomoResultSet;
import cn.realai.online.core.entity.PersonalInformation;
import cn.realai.online.core.entity.SampleGrouping;
import cn.realai.online.core.entity.VariableData;
import cn.realai.online.core.service.PersonalHomoResultSetService;
import cn.realai.online.core.service.PersonalInformationService;
import cn.realai.online.core.service.SampleGroupingService;
import cn.realai.online.core.service.VariableDataService;
import cn.realai.online.tool.redis.RedisClientTemplate;
import cn.realai.online.util.SpringContextUtils;
import cn.realai.online.util.StringUtil;

public class BatchTaskOfHomo extends BaseBatchTask {

	private static Logger logger = LoggerFactory.getLogger(BatchTaskOfHomo.class);
	
	public BatchTaskOfHomo(Long experimentId, Long batchId, String redisKey) {
		this.experimentId = experimentId;
		this.batchId = batchId;
		this.redisKey = redisKey;
	}
	
	@Override
	public void run() {
		logger.info("BatchTaskOfHomo run. experimentId{}, batchId{}, redisKey{}", experimentId, batchId, redisKey);
		RedisClientTemplate redisClientTemplate = SpringContextUtils.getBean(RedisClientTemplate.class);
		try {
			String redisValue = redisClientTemplate.get(redisKey);
			List<PersonalHomoResultSet> list = JSON.parseArray(redisValue, PersonalHomoResultSet.class);
	    	if (list == null || list.size() == 0) {
	    		return ;
	    	}
			
	    	//变量信息
	    	VariableDataService variableDataService = SpringContextUtils.getBean(VariableDataService.class);
	        List<VariableData> vdList = variableDataService.findListByExperimentId(experimentId);
	        Map<String, Long> vdMap = new HashMap<String, Long>();
	        for (VariableData vd : vdList) {
	            vdMap.put(vd.getName() + vd.getVariableType(), vd.getId());
	        }
	    	
	        //人员信息
	        PersonalInformationService personalInformationService = SpringContextUtils.getBean(PersonalInformationService.class);
	        List<PersonalInformation> personalInformationList = personalInformationService.findListByExperimentIdAndBatchId(experimentId, batchId);
	        
	        Map<String, Long> piMap = new HashMap<String, Long>();
	        for (PersonalInformation pi : personalInformationList) {
	            piMap.put(pi.getPersonalId(), pi.getId());
	        }
	    	
			for (PersonalHomoResultSet phrs : list) {
				Long pid = piMap.get(phrs.getPersonalId());
	    		if (pid == null && errorCount <3) {
	    			logger.warn("BatchTaskOfHomo run getPid error. 获取人的id没有获取到. experimentId{}, batchId{}, redisKey{}, personalId{}, errorCount{}", 
	    					experimentId, batchId, redisKey, phrs.getPersonalId(), errorCount);
	    			Thread.sleep(2000L);
	    			errorCount= errorCount + 1;
	    			ModelCallPool.modelCallPool.execute(this);
	    			return ;
	    		} else if (errorCount == 3) {
	    			logger.error("BatchTaskOfHetro run getPid error. 获取人的id没有获取到. experimentId{}, batchId{}, redisKey{}, personalId{}, errorCount{}", 
	    					experimentId, batchId, redisKey, phrs.getPersonalId(), errorCount);
	    		}
	    		phrs.setPid(pid);
				phrs.setBatchId(batchId);
				phrs.setExperimentId(experimentId);
	    		phrs.setVariableId(vdMap.get(phrs.getVariableName() + VariableData.VARIABLE_TYPE_HOMO));
			}
			PersonalHomoResultSetService personalHomoResultSetService = SpringContextUtils.getBean(PersonalHomoResultSetService.class);
			personalHomoResultSetService.insertList(list);
			redisClientTemplate.delete(redisKey);
		} catch (Exception e) {
			
		}
	}

}
