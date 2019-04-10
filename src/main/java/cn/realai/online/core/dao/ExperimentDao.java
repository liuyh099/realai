package cn.realai.online.core.dao;

import org.apache.ibatis.annotations.Param;

import cn.realai.online.core.entity.Experiment;

import java.util.HashMap;
import java.util.List;

public interface ExperimentDao {

    int insertModel(@Param("experiment") Experiment experiment);

    Experiment selectExperimentById(@Param("id") long id);

    List<Experiment> findList(Experiment experiment);

    int maintainPreprocessStatus(@Param("experimentId") Long experimentId,
                               @Param("preFinishStatus") int preFinishStatus,
                               @Param("errMsg")String errMsg);

    int updateExperimentStatus(@Param("experimentId") long experimentId,
                               @Param("status") int status);

    /**
     * 删除实验训练列表
     *
     * @param ids
     * @return
     */
    Integer deleteExperimentByIds(@Param("ids") List<Long> ids);

    /**
     * 检查名称
     *
     * @param experiment
     * @return
     */
    List<Experiment> checkName(Experiment experiment);

    /**
     * 插入实验
     *
     * @param experiment
     * @return
     */
    Long insert(Experiment experiment);

    int trainResultMaintain(@Param("experimentId") Long experimentId,
    						@Param("status")int status,
                            @Param("sampleReview") String sampleReview,
                            @Param("modelUrl") String modelUrl,
                            @Param("segmentationStatisticsImageUrl") String segmentationStatisticsImageUrl,
                            @Param("badTopCountImageUrl") String badTopCountImageUrl,
                            @Param("rocTestImageUrl") String rocTestImageUrl,
                            @Param("rocTrainImageUrl") String rocTrainImageUrl,
                            @Param("rocValidateImageUrl") String rocValidateImageUrl,
                            @Param("ksTestImageUrl") String ksTestImageUrl,
                            @Param("ksTrainImageUrl") String ksTrainImageUrl,
                            @Param("ksValidateImageUrl") String ksValidateImageUrl,
                            @Param("trainingEndTime")Long trainingEndTime);

    /**
     * 更新选择的文件
     *
     * @param experiment
     * @return
     */
    Integer selectFileUpdate(Experiment experiment);

    /**
     * 更新选择参数
     *
     * @param experiment
     * @return
     */
    Integer updateParam(Experiment experiment);

	HashMap findByServiceIdAndReleaseStatus(@Param("serviceId") Long serviceId, @Param("releaseStatus") Integer releaseStatus);

    /**
     * 二次创建实验
     * @param experiment
     */
    void doubleCreate(Experiment experiment);

    /**
     * 根据服务ID查询发布的训练
     * @param id
     * @return
     */
    Experiment getPublishExperimentByServerId(Long id);

    /**
     * 更新服务下线
     * @param experimentId
     * @param serviceId
     * @param releasNo
     */
    void updateExperimentOffline(@Param("id") Long experimentId, @Param("serviceId")Long serviceId, @Param("status")int releasNo);

    /**
     * 更新服务训练状态
     * @param experimentId
     * @param releasYes
     */
    void updateExperimentTrainStatus(@Param("id")Long experimentId, @Param("status")int releasYes,@Param("publishTime")Long publishTime);

    /**
     * 实验训练
     * @param experimentId
     * @param statusTraining
     * @param currentTimeMillis
     * @return
     */
    int train(@Param("experimentId")long experimentId, @Param("status")int statusTraining, @Param("trainTime")long currentTimeMillis);

    /**
     * 获得最近发布的服务id
     * @return
     */
    Long getLastServerId();

    /**
     * 获得服务发布的实验列表
     * @param serviceId
     * @return
     */
    List<Experiment> findPublishByServiceId(@Param("serviceId") Long serviceId);

    Experiment getById(@Param("id") Long experimentId);

    Integer updateName(@Param("id") Long id, @Param("name") String name);

    /**
     * 实验失败，修改实验状态并记录错误信息
     * @param experimentId
     * @param statusTrainingError
     * @param errMsg
     */
	void maintainErrorMsg(@Param("experimentId")Long experimentId,
			@Param("status")int status,
			@Param("errMsg")String errMsg);

    /**
     * 更新名称和备注
     * @param experiment
     * @return
     */
    Integer updateNameAndRemark(Experiment experiment);

    /**
     * 筛选未发布的实验ID
     * @param ids
     * @return
     */
    List<Long> findNotPublishExperimentIds(@Param("ids") List<Long> ids);

    /**
     * 逻辑删除实验
     * @param ids
     * @return
     */
    int logicDeleteExperiment(@Param("ids")List<Long> ids);


}
