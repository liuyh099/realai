package cn.realai.online.tool.modelcallthreadpool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.realai.online.core.entity.PersonalInformation;
import cn.realai.online.core.entity.SampleGrouping;
import cn.realai.online.core.service.PersonalInformationService;
import cn.realai.online.core.service.SampleGroupingService;
import cn.realai.online.tool.redis.RedisClientTemplate;
import cn.realai.online.util.SpringContextUtils;

public class BatchTaskOfPersonalInfo extends BaseBatchTask {

	private static Logger logger = LoggerFactory.getLogger(BatchTaskOfPersonalInfo.class);
	
	public BatchTaskOfPersonalInfo(Long experimentId, Long batchId, String redisKey) {
		this.experimentId = experimentId;
		this.batchId = batchId;
		this.redisKey = redisKey;
	}
	
	@Override
	public void run() {
		logger.info("BatchTaskOfPersonalInfo run. experimentId{}, batchId{}, redisKey{}", experimentId, batchId, redisKey);
		RedisClientTemplate redisClientTemplate = SpringContextUtils.getBean(RedisClientTemplate.class);
		try {
			String redisValue = redisClientTemplate.get(redisKey);
			List<PersonalInformation> list = JSON.parseArray(redisValue, PersonalInformation.class);
	    	if (list == null || list.size() == 0) {
	    		return ;
	    	}
	    	
	    	//样本分组
	    	SampleGroupingService sampleGroupingService = SpringContextUtils.getBean(SampleGroupingService.class);
	        List<SampleGrouping> sampleGroupingList = sampleGroupingService.findListByExperimentId(experimentId, true, true);

	        Map<String, Long> sgMap = new HashMap<String, Long>();
	        for (SampleGrouping sg : sampleGroupingList) {
	            sgMap.put(sg.getGroupName(), sg.getId());
	        }
			
	    	for (PersonalInformation pi : list) {
	    		pi.setBatchId(batchId);
	    		pi.setExperimentId(experimentId);
	    		pi.setGroupId(sgMap.get(pi.getGroupName()));
	    	}
	    	
			PersonalInformationService personalInformationService = SpringContextUtils.getBean(PersonalInformationService.class);
			personalInformationService.insertList(list);
			redisClientTemplate.delete(redisKey);
			
		} catch (Exception e) {
		}
	}

}
