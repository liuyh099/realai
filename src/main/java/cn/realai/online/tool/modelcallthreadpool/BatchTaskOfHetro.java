package cn.realai.online.tool.modelcallthreadpool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.realai.online.core.entity.PersonalHetroResultSet;
import cn.realai.online.core.entity.PersonalInformation;
import cn.realai.online.core.entity.SampleGrouping;
import cn.realai.online.core.entity.VariableData;
import cn.realai.online.core.service.PersonalHetroResultSetService;
import cn.realai.online.core.service.PersonalInformationService;
import cn.realai.online.core.service.SampleGroupingService;
import cn.realai.online.core.service.VariableDataService;
import cn.realai.online.tool.redis.RedisClientTemplate;
import cn.realai.online.util.SpringContextUtils;

public class BatchTaskOfHetro extends BaseBatchTask {

	private static Logger logger = LoggerFactory.getLogger(BatchTaskOfCombo.class);
	
	public BatchTaskOfHetro(Long experimentId, Long batchId, String redisKey) {
		this.experimentId = experimentId;
		this.batchId = batchId;
		this.redisKey = redisKey;
	}
	
	@Override
	public void run() {
		logger.info("BatchTaskOfHetro run. experimentId{}, batchId{}, redisKey{}", experimentId, batchId, redisKey);
		RedisClientTemplate redisClientTemplate = SpringContextUtils.getBean(RedisClientTemplate.class);
		try {
			String redisValue = redisClientTemplate.get(redisKey);
			List<PersonalHetroResultSet> list = JSON.parseArray(redisValue, PersonalHetroResultSet.class);
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
	        
	    	for (PersonalHetroResultSet phrs : list) {
	    		Long pid = piMap.get(phrs.getPersonalId());
	    		if (pid == null && errorCount <3) {
	    			logger.warn("BatchTaskOfHetro run getPid error. 获取人的id没有获取到. experimentId{}, batchId{}, redisKey{}, personalId{}, errorCount{}", 
	    					experimentId, batchId, redisKey, phrs.getPersonalId(), errorCount);
	    			Thread.sleep(2000L);
	    			errorCount= errorCount + 1;
	    			ModelCallPool.modelCallPool.execute(this);
	    			return ;
	    		} else if (errorCount == 3) {
	    			logger.error("BatchTaskOfHetro run getPid error. 获取人的id没有获取到. experimentId{}, batchId{}, redisKey{}, personalId{}, errorCount{}", 
	    					experimentId, batchId, redisKey, phrs.getPersonalId(), errorCount);
	    		}
	    		phrs.setBatchId(batchId);
	    		phrs.setExperimentId(experimentId);
	    		phrs.setPid(pid);
	    		phrs.setVariableId(vdMap.get(phrs.getVariableName()));
	    	}
	    	
	    	PersonalHetroResultSetService personalHetroResultSetService = SpringContextUtils.getBean(PersonalHetroResultSetService.class);
	    	personalHetroResultSetService.insertList(list);
	    	redisClientTemplate.delete(redisKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
