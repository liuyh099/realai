package cn.realai.online.tool.modelcallthreadpool;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.realai.online.core.bo.ExperimentBO;
import cn.realai.online.core.entity.Experiment;
import cn.realai.online.core.entity.VariableData;
import cn.realai.online.core.service.ExperimentService;
import cn.realai.online.core.service.VariableDataService;
import cn.realai.online.tool.redis.RedisClientTemplate;
import cn.realai.online.util.SpringContextUtils;

/**
 * 预处理回调任务
 *
 * @author lyh
 */
public class PreprocessTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(PreprocessTask.class);

    private Long experimentId;

    private String redisKey;

    public PreprocessTask(Long experimentId, String redisKey) {
        this.experimentId = experimentId;
        this.redisKey = redisKey;
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

        //解析
        List<VariableData> vdList = JSON.parseArray(vdStr, VariableData.class);

        //数据入库
        VariableDataService variableDataService = SpringContextUtils.getBean(VariableDataService.class);
        variableDataService.insertVariableDataList(vdList);

        //修改实验的预处理状态
        experimentService.maintainPreprocessStatus(experimentId, Experiment.PREFINISH_YES, "");
    }

}
