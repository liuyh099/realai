package cn.realai.online.tool.modelcallthreadpool;

import java.util.List;

import com.alibaba.fastjson.JSON;

import cn.realai.online.core.entity.PersonalInformation;
import cn.realai.online.core.service.PersonalInformationService;
import cn.realai.online.tool.redis.RedisClientTemplate;
import cn.realai.online.util.SpringContextUtils;

public class BatchTaskOfPersonalInfo extends BaseBatchTask {

	public BatchTaskOfPersonalInfo(Long experimentId, Long batchId, String redisKey) {
		this.experimentId = experimentId;
		this.batchId = batchId;
		this.redisKey = redisKey;
	}
	
	@Override
	public void run() {
		RedisClientTemplate redisClientTemplate = SpringContextUtils.getBean(RedisClientTemplate.class);
        String redisValue = redisClientTemplate.get(redisKey);
		List<PersonalInformation> list = JSON.parseArray(redisValue, PersonalInformation.class);
    	if (list == null || list.size() == 0) {
    		return ;
    	}
		
    	for (PersonalInformation pi : list) {
    		pi.setBatchId(batchId);
    	}
    	
		PersonalInformationService personalInformationService = SpringContextUtils.getBean(PersonalInformationService.class);
		personalInformationService.insertList(list);
		redisClientTemplate.delete(redisKey);
	}

}
