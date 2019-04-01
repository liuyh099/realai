package cn.realai.online.core.service.impl;

import cn.realai.online.core.bo.ExperimentalTrainDetailBO;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.realai.online.core.bo.ExperimentBO;
import cn.realai.online.common.RedisKeyPrefix;
import cn.realai.online.core.dao.ExperimentDao;
import cn.realai.online.core.entity.Experiment;
import cn.realai.online.core.service.ExperimentService;
import cn.realai.online.tool.redis.RedisClientTemplate;
import cn.realai.online.util.ConvertJavaBean;
import org.springframework.util.CollectionUtils;
import cn.realai.online.common.config.Config;


import java.util.HashMap;
import java.util.List;

@Service
public class ExperimentServiceImpl implements ExperimentService {

    private static Logger logger = LoggerFactory.getLogger(ExperimentServiceImpl.class);

    @Autowired
    private ExperimentDao experimentDao;

    @Autowired
    private RedisClientTemplate redisClientTemplate;
    
    @Autowired
    private Config config;
    
    private String getExperimentRedisKey(long id) {
        return RedisKeyPrefix.EXPERIMENT_PREFIX + id;
        
    }

    @Override
    public ExperimentBO selectExperimentById(long id) {
//        Experiment experiment = redisClientTemplate.get(getExperimentRedisKey(id), ExperimentBO.class);
//        if (experiment == null) {
        Experiment experiment = experimentDao.selectExperimentById(id);
            //设置缓存
//        }
//        if (experiment == null) {
//            return null;
//        }
        //封装成BO
        ExperimentBO experimentBO = convertBO(experiment);
        return experimentBO;
    }

    /*
     * 转换成bo
     */
    private ExperimentBO convertBO(Experiment experiment) {
        ExperimentBO experimentBO = new ExperimentBO();
        convertUrl(experiment);
        ConvertJavaBean.convertJavaBean(experimentBO, experiment);
        return experimentBO;
    }
    
    private void convertUrl(Experiment experiment) {
    	String url = experiment.getKsTestImageUrl();
    	if (url != null && !"".equals(url)) {
        	experiment.setKsTestImageUrl(config.getNginxUrl() + url);
        }
        url = experiment.getBadTopCountImageUrl();
        if (url != null && !"".equals(url)) {
        	experiment.setBadTopCountImageUrl(config.getNginxUrl() + url);
        }
        url = experiment.getKsTrainImageUrl();
        if (url != null && !"".equals(url)) {
        	experiment.setKsTrainImageUrl(config.getNginxUrl() + url);
        }
        url = experiment.getKsValidateImageUrl();
        if (url != null && !"".equals(url)) {
        	experiment.setKsValidateImageUrl(config.getNginxUrl() + url);
        }
        url = experiment.getModelUrl();
        if (url != null && !"".equals(url)) {
        	experiment.setModelUrl(config.getNginxUrl() + url);
        }
        url = experiment.getRocTestImageUrl();
        if (url != null && !"".equals(url)) {
        	experiment.setRocTestImageUrl(config.getNginxUrl() + url);
        }
        url = experiment.getRocTrainImageUrl();
        if (url != null && !"".equals(url)) {
        	experiment.setRocTrainImageUrl(config.getNginxUrl() + url);
        }
        url = experiment.getRocValidateImageUrl();
        if (url != null && !"".equals(url)) {
        	experiment.setRocValidateImageUrl(config.getNginxUrl() + url);
        }
        url = experiment.getSegmentationStatisticsImageUrl();
        if (url != null && !"".equals(url)) {
        	experiment.setSegmentationStatisticsImageUrl(config.getNginxUrl() + url);
        }
    }

    @Override
    public List<Experiment> findList(Experiment experiment) {
        List<Experiment> list = experimentDao.findList(experiment);
        for (Experiment e : list) {
        	convertUrl(e);
        }
        return list;
    }

    /*
     * 预处理完成，修改与处理结果
     * @param experimentId 实验id
     * @param
     */
    @Override
    public int maintainPreprocessStatus(Long experimentId, int preFinishStatus, String errMsg) {
        int ret = experimentDao.maintainPreprocessStatus(experimentId, preFinishStatus, errMsg);
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
        } else {
            if (id != null) {
                for (Experiment experiment1 : experimentList) {
                    if (!experiment1.getId().equals(id)) {
                        return false;
                    }
                }
            } else {
                return false;
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
    public int trainResultMaintain(Long experimentId, int status, String sampleReview, String modelUrl,
                                   String segmentationStatisticsImageUrl, String badTopCountImageUrl, String rocTestImageUrl,
                                   String rocTrainImageUrl, String rocValidateImageUrl, String ksTestImageUrl, String ksTrainImageUrl,
                                   String ksValidateImageUrl) {
        return experimentDao.trainResultMaintain(experimentId, status, sampleReview, modelUrl, segmentationStatisticsImageUrl,
                badTopCountImageUrl, rocTestImageUrl, rocTrainImageUrl, rocValidateImageUrl, ksTestImageUrl,
                ksTrainImageUrl, ksValidateImageUrl);
    }

    @Override
    public Integer selectFileUpdate(Experiment experiment) {
        return experimentDao.selectFileUpdate(experiment);
    }

    @Override
    public Integer updateParam(Experiment experiment) {
        return experimentDao.updateParam(experiment);
    }

    @Override
    public HashMap findByServiceIdAndReleaseStatus(Long serviceId, Integer releaseStatus) {
        return experimentDao.findByServiceIdAndReleaseStatus(serviceId, releaseStatus);
    }

    @Override
    public void doubleCreate(Experiment experiment) {
        experimentDao.doubleCreate(experiment);
    }

    @Override
    public Experiment getPublishExperimentByServerId(Long id) {
        return experimentDao.getPublishExperimentByServerId(id);
    }

    @Override
    public void updateExperimentOffline(Long experimentId, Long serviceId, int releasNo) {
        experimentDao.updateExperimentOffline(experimentId,serviceId,releasNo);
    }

    @Override
    public void updateExperimentTrainStatus(Long experimentId, int releasYes,Long publishTime) {
        experimentDao.updateExperimentTrainStatus(experimentId,releasYes,publishTime);
    }

    @Override
    public Long getLastServerId() {
        return experimentDao.getLastServerId();
    }

    @Override
    public List<Experiment> findPublishByServiceId(Long serviceId) {
        return experimentDao.findPublishByServiceId(serviceId);
    }

    @Override
    public Experiment getById(Long experimentId) {
        return experimentDao.getById(experimentId);
    }

    @Override
    public Integer updateName(Long id, String name) {
        return experimentDao.updateName(id,name);
    }

	@Override
	public void maintainErrorMsg(Long experimentId, int statusTrainingError, String errMsg) {
		experimentDao.maintainErrorMsg(experimentId, statusTrainingError, errMsg);
	}

    @Override
    public Integer updateNameAndRemark(Experiment experiment) {
        return experimentDao.updateNameAndRemark(experiment);
    }

    @Override
    public List<Long> findNotPublishExperimentIds(List<Long> ids) {
        return experimentDao.findNotPublishExperimentIds(ids);
    }

    @Override
    public int logicDeleteExperiment(List<Long> ids) {
        return experimentDao.logicDeleteExperiment(ids);
    }
}
