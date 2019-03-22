package cn.realai.online.tool.modelcallthreadpool;

import java.util.List;

import com.alibaba.fastjson.JSON;

import cn.realai.online.core.entity.PersonalComboResultSet;
import cn.realai.online.core.service.PersonalComboResultSetService;
import cn.realai.online.tool.redis.RedisClientTemplate;
import cn.realai.online.util.SpringContextUtils;

public class BatchTaskOfCombo extends BaseBatchTask {

	public BatchTaskOfCombo(Long experimentId, Long batchId, String redisKey) {
		this.experimentId = experimentId;
		this.batchId = batchId;
		this.redisKey = redisKey;
	}
	
	@Override
	public void run() {
		RedisClientTemplate redisClientTemplate = SpringContextUtils.getBean(RedisClientTemplate.class);
        String redisValue = redisClientTemplate.get(redisKey);
		List<PersonalComboResultSet> list = JSON.parseArray(redisValue, PersonalComboResultSet.class);
    	if (list == null || list.size() == 0) {
    		return ;
    	}
    	
    	for (PersonalComboResultSet pcrs : list) {
    		pcrs.setBatchId(batchId);
    	}
    	
    	PersonalComboResultSetService personalComboResultSeService = SpringContextUtils.getBean(PersonalComboResultSetService.class);
    	personalComboResultSeService.insertList(list);
	}

}
