package cn.realai.online.tool.modelcallthreadpool;

import java.util.List;

import com.alibaba.fastjson.JSON;

import cn.realai.online.core.entity.PersonalHomoResultSet;
import cn.realai.online.core.service.PersonalHomoResultSetService;
import cn.realai.online.tool.redis.RedisClientTemplate;
import cn.realai.online.util.SpringContextUtils;
import cn.realai.online.util.StringUtil;

public class BatchTaskOfHomo extends BaseBatchTask {

	public BatchTaskOfHomo(Long experimentId, Long batchId, String redisKey) {
		this.experimentId = experimentId;
		this.batchId = batchId;
		this.redisKey = redisKey;
	}
	
	@Override
	public void run() {
		RedisClientTemplate redisClientTemplate = SpringContextUtils.getBean(RedisClientTemplate.class);
        String redisValue = redisClientTemplate.get(redisKey);
		List<PersonalHomoResultSet> list = JSON.parseArray(redisValue, PersonalHomoResultSet.class);
    	if (list == null || list.size() == 0) {
    		return ;
    	}
		
    	String batchStr = list.get(0).getBatchStr();
    	if (!StringUtil.isNumeric(batchStr)) {
    		Long batchId = getBatchId();
    		for (PersonalHomoResultSet phrs : list) {
    			phrs.setBatchId(batchId);
    		}
    	}
		PersonalHomoResultSetService personalHomoResultSetService = SpringContextUtils.getBean(PersonalHomoResultSetService.class);
		personalHomoResultSetService.insertList(list);
	}

}
