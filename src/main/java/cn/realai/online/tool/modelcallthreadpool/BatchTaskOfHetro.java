package cn.realai.online.tool.modelcallthreadpool;

import java.util.List;

import com.alibaba.fastjson.JSON;

import cn.realai.online.core.entity.PersonalHetroResultSet;
import cn.realai.online.core.service.PersonalHetroResultSetService;
import cn.realai.online.tool.redis.RedisClientTemplate;
import cn.realai.online.util.SpringContextUtils;

public class BatchTaskOfHetro extends BaseBatchTask {

	public BatchTaskOfHetro(Long experimentId, Long batchId, String redisKey) {
		this.experimentId = experimentId;
		this.batchId = batchId;
		this.redisKey = redisKey;
	}
	
	@Override
	public void run() {
		RedisClientTemplate redisClientTemplate = SpringContextUtils.getBean(RedisClientTemplate.class);
        String redisValue = redisClientTemplate.get(redisKey);
		List<PersonalHetroResultSet> list = JSON.parseArray(redisValue, PersonalHetroResultSet.class);
    	if (list == null || list.size() == 0) {
    		return ;
    	}
    	
    	for (PersonalHetroResultSet phrs : list) {
    		phrs.setBatchId(batchId);
    	}
    	
    	PersonalHetroResultSetService personalHetroResultSetService = SpringContextUtils.getBean(PersonalHetroResultSetService.class);
    	personalHetroResultSetService.insertList(list);
    	redisClientTemplate.delete(redisKey);
	}

}
