package cn.realai.online.core.service;

import cn.realai.online.core.bo.ExperimentBO;
import cn.realai.online.core.bo.ExperimentalTrainDetailBO;
import cn.realai.online.core.entity.Experiment;
import cn.realai.online.core.entity.MLock;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface ExperimentService {

    ExperimentBO selectExperimentById(long id);

    List<Experiment> findList(Experiment experiment);

    /*
     * 预处理完成，修改与处理结果
     */
    int updatePreprocessStatus(Long experimentId, int preFinishStatus);


    /**
     * id 删除实验训练列表
     *
     * @param ids
     */
    Integer deleteExperimentByIds(List<Long> ids);

    /*
     * 修改实验的状态
     * @param experimentId
     * @param statusTraining
     * @return
     */
    int updateExperimentStatus(long experimentId, int status);

    ExperimentalTrainDetailBO selectExperimentDetailById(long id);

    /**
     * 检查实验名称
     *
     * @param name
     * @param id
     * @return
     */
    boolean checkTrainName(String name, Long id);

    /**
     * 插入实验
     *
     * @param experiment
     * @return
     */
    Long insert(Experiment experiment);

    /*
     * 实验训练结果维护
     * @param experimentId 实验id
     * @param sampleReview 样本综述
     * @param modelUrl 模型url
     * @param segmentationStatisticsImageUrl 分段统计图片地址
     * @param badTopCountImageUrl badTop总数图片地址
     * @param rocTestImageUrl 测试roc图片地址
     * @param rocTrainImageUrl 训练roc图片地址
     * @param rocValidateImageUrl 验证roc图片地址
     * @param ksTestImageUrl 测试ks图片地址
     * @param ksTrainImageUrl 训练ks图片地址
     * @param ksValidateImageUrl 验证ks图片地址
     */
    int trainResultMaintain(Long experimentId, String sampleReview, String modelUrl,
                            String segmentationStatisticsImageUrl, String badTopCountImageUrl, String rocTestImageUrl,
                            String rocTrainImageUrl, String rocValidateImageUrl, String ksTestImageUrl, String ksTrainImageUrl,
                            String ksValidateImageUrl);

    /*
     * 获取实验训练锁的实例
     * @param experimentId 实验id
     * @return
     */
    MLock getExperimentTrainMLockInstance(long experimentId);


    /**
     * 更新选择文件的内容
     *
     * @param experiment
     * @return
     */
    Integer selectFileUpdate(Experiment experiment);

    /**
     * 更新实验参数
     *
     * @param experiment
     * @return
     */
    Integer updateParam(Experiment experiment);

	HashMap findByServiceIdAndReleaseStatus(Long serviceId, Integer releaseStatus);

    /**
     * 二次创建实验
     * @param experiment
     */
    void doubleCreate(Experiment experiment);

    /**
     * 获得发布的实验根据服务ID
     * @param id
     * @return
     */
    Experiment getPublishExperimentByServerId(Long id);

    /**
     * 更新实验下线
     * @param experimentId
     * @param serviceId
     * @param releasNo
     */
    void updateExperimentOffline(Long experimentId, Long serviceId, int releasNo);

    /**
     * 更新实验上线
     * @param experimentId
     * @param releasYes
     */
    void updateExperimentTrainStatus(Long experimentId, int releasYes);
}
