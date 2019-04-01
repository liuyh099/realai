package cn.realai.online.tool.modelcallthreadpool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.realai.online.core.bussiness.impl.ModelCallBussinessImpl;
import cn.realai.online.core.entity.PersonalComboResultSet;
import cn.realai.online.core.entity.PersonalInformation;
import cn.realai.online.core.entity.SampleGrouping;
import cn.realai.online.core.entity.VariableData;
import cn.realai.online.core.service.PersonalComboResultSetService;
import cn.realai.online.core.service.PersonalInformationService;
import cn.realai.online.core.service.SampleGroupingService;
import cn.realai.online.core.service.VariableDataService;
import cn.realai.online.tool.redis.RedisClientTemplate;
import cn.realai.online.util.SpringContextUtils;

public class BatchTaskOfCombo extends BaseBatchTask {

	private static Logger logger = LoggerFactory.getLogger(BatchTaskOfCombo.class);
	
	public BatchTaskOfCombo(Long experimentId, Long batchId, String redisKey) {
		this.experimentId = experimentId;
		this.batchId = batchId;
		this.redisKey = redisKey;
	}
	
	@Override
	public void run() {
		try {
			logger.info("BatchTaskOfCombo run. experimentId{}, batchId{}, redisKey{}", experimentId, batchId, redisKey);
			RedisClientTemplate redisClientTemplate = SpringContextUtils.getBean(RedisClientTemplate.class);
	        String redisValue = redisClientTemplate.get(redisKey);
			List<PersonalComboResultSet> list = JSON.parseArray(redisValue, PersonalComboResultSet.class);
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
	    	
	    	for (PersonalComboResultSet pcrs : list) {
	    		Long pid = piMap.get(pcrs.getPersonalId());
	    		if (pid == null && errorCount < 3) {
	    			logger.warn("BatchTaskOfHetro run getPid error. 获取人的id没有获取到. experimentId{}, batchId{}, redisKey{}, personalId{}, errorCount{}", 
	    					experimentId, batchId, redisKey, pcrs.getPersonalId(), errorCount);
	    			Thread.sleep(2000L);
	    			errorCount= errorCount + 1;
	    			ModelCallPool.modelCallPool.execute(this);
	    			return ;
	    		} else if (errorCount == 3) {
	    			logger.error("BatchTaskOfHetro run getPid error. 获取人的id没有获取到. experimentId{}, batchId{}, redisKey{}, personalId{}, errorCount{}", 
	    					experimentId, batchId, redisKey, pcrs.getPersonalId(), errorCount);
	    		}
	    		pcrs.setPid(pid);
	    		pcrs.setBatchId(batchId);
	    		pcrs.setExperimentId(experimentId);
	    		pcrs.setVariableId1(vdMap.get(pcrs.getVariableName1()));
	    		pcrs.setVariableId2(vdMap.get(pcrs.getVariableName2()));
	    		pcrs.setVariableId3(vdMap.get(pcrs.getVariableName3()));
	    	}
	    	
	    	PersonalComboResultSetService personalComboResultSeService = SpringContextUtils.getBean(PersonalComboResultSetService.class);
	    	personalComboResultSeService.insertList(list);
	    	redisClientTemplate.delete(redisKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
