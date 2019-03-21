package cn.realai.online.core.service.impl;

import cn.realai.online.core.bo.ExperimentalTrainDetailBO;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.realai.online.core.bo.ExperimentBO;
import cn.realai.online.common.Constant;
import cn.realai.online.common.RedisKeyPrefix;
import cn.realai.online.core.dao.ExperimentDao;
import cn.realai.online.core.entity.Experiment;
import cn.realai.online.core.entity.MLock;
import cn.realai.online.core.service.ExperimentService;
import cn.realai.online.tool.redis.RedisClientTemplate;
import cn.realai.online.util.ConvertJavaBean;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class ExperimentServiceImpl implements ExperimentService {

    private static Logger logger = LoggerFactory.getLogger(ExperimentServiceImpl.class);

    @Autowired
    private ExperimentDao experimentDao;

    @Autowired
    private RedisClientTemplate redisClientTemplate;

    private String getExperimentRedisKey(long id) {
        return RedisKeyPrefix.EXPERIMENT_PREFIX + id;
    }

    @Override
    public ExperimentBO selectExperimentById(long id) {
        Experiment experiment = redisClientTemplate.get(getExperimentRedisKey(id), ExperimentBO.class);
        if (experiment == null) {
            experiment = experimentDao.selectExperimentById(id);
            //设置缓存
        }
        if (experiment == null) {
            return null;
        }
        //封装成BO
        ExperimentBO experimentBO = convertBO(experiment);
        return experimentBO;
    }

    /*
     * 转换成bo
     */
    private ExperimentBO convertBO(Experiment experiment) {
        ExperimentBO experimentBO = new ExperimentBO();
        ConvertJavaBean.convertJavaBean(experimentBO, experiment);
        return experimentBO;
    }

    @Override
    public List<ExperimentBO> findList(Experiment experiment) {
        List<Experiment> list = experimentDao.findList(experiment);
        List<ExperimentBO> result = JSON.parseArray(JSON.toJSONString(list), ExperimentBO.class);
        //BeanUtilsBean.copyProperties(list,result);
        return result;
    }

    /*
     * 预处理完成，修改与处理结果
     * @param experimentId 实验id
     * @param
     */
    @Override
    public int updatePreprocessStatus(Long experimentId, int preFinishStatus) {
        int ret = experimentDao.updatePreprocessStatus(experimentId, preFinishStatus);
        return ret;
    }

    @Override
    public Integer deleteExperimentByIds(List<Long> ids) {
        return experimentDao.deleteExperimentByIds(ids);
    }

    /*
     * 修改实验的状态
     * @param experimentId
     * @param statusTraining
     * @return
     */
    @Override
    public int updateExperimentStatus(long experimentId, int status) {
        int ret = experimentDao.updateExperimentStatus(experimentId, status);
        return ret;
    }

    @Override
    public ExperimentalTrainDetailBO selectExperimentDetailById(long id) {
        Experiment experiment = experimentDao.selectExperimentById(id);
        ExperimentalTrainDetailBO experimentalTrainDetailBO = JSON.parseObject(JSON.toJSONString(experiment), ExperimentalTrainDetailBO.class);
        return experimentalTrainDetailBO;
    }

    @Override
    public boolean checkTrainName(String name, Long id) {
        Experiment experiment = new Experiment();
        experiment.setName(name);
        List<Experiment> experimentList = experimentDao.checkName(experiment);
        if (CollectionUtils.isEmpty(experimentList)) {
            return true;
        }
        if (id != null) {
            for (Experiment experiment1 : experimentList) {
                if (!experiment1.getId().equals(id)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Long insert(Experiment experiment) {
        experimentDao.insert(experiment);
        return experiment.getId();
    }

    @Override
    public int trainResultMaintain(Long experimentId, String sampleReview, String modelUrl,
                                   String segmentationStatisticsImageUrl, String badTopCountImageUrl, String rocTestImageUrl,
                                   String rocTrainImageUrl, String rocValidateImageUrl, String ksTestImageUrl, String ksTrainImageUrl,
                                   String ksValidateImageUrl) {
        return experimentDao.trainResultMaintain(experimentId, sampleReview, modelUrl, segmentationStatisticsImageUrl,
                badTopCountImageUrl, rocTestImageUrl, rocTrainImageUrl, rocValidateImageUrl, ksTestImageUrl,
                ksTrainImageUrl, ksValidateImageUrl);
    }

    @Override
    public MLock getExperimentTrainMLockInstance(long experimentId) {
        return new MLock(Constant.TRAIN_MLOCK_LOCK, Constant.TRAIN_MLOCK_PREFIX + experimentId, Constant.TRAIN_MLOCK_LOCK_LEASE_TIME);
    }

    @Override
    public Integer selectFileUpdate(Experiment experiment) {
        return experimentDao.selectFileUpdate(experiment);
    }

    @Override
    public Integer updateParam(Experiment experiment) {
        return experimentDao.updateParam(experiment);
    }

}
