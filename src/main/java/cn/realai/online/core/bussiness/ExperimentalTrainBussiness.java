package cn.realai.online.core.bussiness;

import cn.realai.online.common.page.PageBO;
import cn.realai.online.core.bo.*;
import cn.realai.online.core.entity.Experiment;
import cn.realai.online.core.query.ExperimentalTrainCreateModelDataQuery;
import cn.realai.online.core.query.ExperimentalTrainQuery;
import cn.realai.online.core.query.FaceListDataQuery;
import cn.realai.online.core.query.IdQuery;
import cn.realai.online.core.vo.ExperimentalTrainDoubleCreateVO;
import cn.realai.online.core.vo.ExperimentalTrainVO;

import java.util.List;

public interface ExperimentalTrainBussiness {
    PageBO<ExperimentBO> pageList(ExperimentalTrainQuery experimentalTrainQuery);

    Integer deleteExperimentByIds(List<Long> ids);

    int train(long experimentId);

    void testPreprocess(long experimentId);

    ExperimentalTrainDetailBO detail(long experimentId);

    /**
     * 新增选择文件
     *
     * @param experimentBO
     * @return
     */
    Long selectFileAdd(ExperimentBO experimentBO);

    /**
     * 检查文件名是否重复
     *
     * @param name
     * @param id
     * @return
     */
    boolean checkTrainName(String name, Long id);

    /**
     * 根据Id查询实验信息
     *
     * @param trainId
     * @return
     */
    ExperimentBO selectById(Long trainId);

    /**
     * 更新选择文件内容
     *
     * @param experimentBO
     * @return
     */
    Integer selectFileUpdate(ExperimentBO experimentBO);

    /**
     * 更新实验参数
     *
     * @param experimentBO
     * @return
     */
    Integer updateParam(ExperimentBO experimentBO);

    /**
     * 查询同质或者异质数据
     *
     * @param experimentalTrainCreateModelDataQuery
     * @return
     */
    PageBO<VariableDataBO> pageHomOrHemeList(ExperimentalTrainCreateModelDataQuery experimentalTrainCreateModelDataQuery);

    /**
     * 删除VariableData 数据
     *
     * @param experimentId
     * @param ids
     */
    void deleteVariableData(Long experimentId, List<Long> ids);

    /**
     * 实验评估指标数据
     *
     * @param experimentId
     * @return
     */
    ExperimentalResultQuatoBO quota(Long experimentId);

    /**
     * 实验指标查看
     *
     * @param parentId
     * @return
     */
    List<ExperimentResultSetBO> quotaGroup(Long parentId);

    /**
     * TOP数据
     *
     * @param experimentId
     * @return
     */
    ExperimentalResultTopBO quotaTopGroup(Long experimentId);

    /**
     * 样本摘要
     *
     * @param experimentId
     * @return
     */
    List<SampleSummaryBO> summary(Long experimentId);

    /**
     * 千人千面页面数据
     *
     * @param faceListDataQuery
     * @return
     */
    PageBO<PersonalInformationBO> personalInformationPage(FaceListDataQuery faceListDataQuery, Integer batchType);

    /**
     * 千人千面列表数据详情信息
     *
     * @param id
     * @return
     */
    PersonalInformationBO listDataDetail(Long id);

    /**
     * 千人千面异质最强组合特征
     *
     * @param id
     * @return
     */
    List<PersonalComboResultSetBO> listDataDetailTopGroup(Long id);

    /**
     * 千人千面异质数据TOP 10
     *
     * @param id
     * @return
     */
    List<PersonalHetroResultSetBO> listDataDetailTopTen(Long id);

    /**
     * 千人千面异质数据列表
     *
     * @param query
     * @return
     */
    PageBO<PersonalHetroResultSetBO> listPersonalHetroResultSet(IdQuery query);

    /**
     * 千人千面同质数据列表
     *
     * @param query
     * @return
     */
    PageBO<PersonalHomoResultSetBO> listPersonalHomoResultSet(IdQuery query);

    /**
     * 千人千面echars
     *
     * @param id
     * @return
     */
    List<PersonalHomoResultSetBO> listDataDetailSameCharts(Long id);

    /**
     * 获得可发布实验列表
     * @return
     */
    List<ExperimentBO> getCanPublishTrain();

    /**
     * 获得模型表现
     * @param id
     * @return
     */
    List<ModelPerformanceBO> findModelPerformance(Long id);

    /**
     * 查询组名
     * @param experimentId
     * @return
     */
    List<SampleGroupingBO> getGroupOptionName(Long experimentId);

    /**
     *
     * @param batchRecordBO
     * @param isTranFlag 是否是训练
     * @return
     */
    List<BatchRecordBO> findBatchRecordBOList(BatchRecordBO batchRecordBO, boolean isTranFlag);

    /**
     * 二次创建实验
     * @param bo
     * @return
     */
    Boolean doubleCreate(ExperimentalTrainDoubleCreateBO bo);

    /**
     * 根据服务ID获取实验
     * @param serverId
     * @return
     */
    List<ExperimentBO> findExperimentByServerId(Long serverId);

    /**
     * 根据服务id查询发布的实验ID
     * @param id
     * @return
     */
    ExperimentBO getPublishExperimentByServerId(Long id);
}
