package cn.realai.online.tool.modelcallthreadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.realai.online.core.bo.ExperimentBO;
import cn.realai.online.core.entity.PSICheckResult;
import cn.realai.online.core.entity.PersonalComboResultSet;
import cn.realai.online.core.entity.PersonalHetroResultSet;
import cn.realai.online.core.entity.PersonalHomoResultSet;
import cn.realai.online.core.entity.PersonalInformation;
import cn.realai.online.core.service.ExperimentService;
import cn.realai.online.tool.redis.RedisClientTemplate;
import cn.realai.online.util.SpringContextUtils;

/**
 * 每日跑批任务
 *
 * @author lyh
 */
public class BatchTask implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(PreprocessTask.class);
	
	private String type;
	
	private static final String TYPE_PERSONALCOMBORESULTSET = "personalComboResultSet";
    private static final String TYPE_PERSONALHETRORESULTSET = "personalHetroResultSet";
    private static final String TYPE_PERSONALHOMORESULTSET = "personalHomoResultSet";
    private static final String TYPE_PERSONALINFORMATION = "personalInformation";
    private static final String TYPE_PSICHECKRESULT = "psiCheckResult";
	
    private Long experimentId;

    private String redisKey;

    public BatchTask(Long experimentId, String redisKey, String type) {
        this.experimentId = experimentId;
        this.redisKey = redisKey;
        this.type = type;
    }

    @Override
    public void run() {
    	//验证实验是否存在
        ExperimentService experimentService = SpringContextUtils.getBean(ExperimentService.class);
        ExperimentBO experiment = experimentService.selectExperimentById(experimentId);
        if (experiment == null) {
            logger.error("PreprocessTask run. 实验预处理错误,实验不存在. experimentId{}", experimentId);
            return;
        }
    	
        //读取预处理结果配置文件
        RedisClientTemplate redisClientTemplate = SpringContextUtils.getBean(RedisClientTemplate.class);
        String vdStr = redisClientTemplate.get(redisKey);
        
        //Class clazz = 
    	
    }

    private Class getDataClass() {
    	if (TYPE_PERSONALCOMBORESULTSET.equals(type)) {
        	return PersonalComboResultSet.class;
        } else if (TYPE_PERSONALHETRORESULTSET.equals(type)) {
        	return PersonalHetroResultSet.class;
        } else if (TYPE_PERSONALHOMORESULTSET.equals(type)) {
        	return PersonalHomoResultSet.class;
        } else if (TYPE_PERSONALINFORMATION.equals(type)) {
        	return PersonalInformation.class;
        } else if (TYPE_PSICHECKRESULT.equals(type)) {
        	return PSICheckResult.class;
        }
        logger.error("BatchTask run. 跑批数据类型不能识别. type{}, experimentId{}", type, experimentId);
        return null;
    }
    
}
