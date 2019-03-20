package cn.realai.online.core.service;

import cn.realai.online.core.bo.ExperimentBO;
import cn.realai.online.core.bo.ExperimentalTrainDetailBO;
import cn.realai.online.core.entity.Experiment;

import java.util.List;

public interface ExperimentService {

	ExperimentBO selectExperimentById(long id);

    List<ExperimentBO> findList(Experiment experiment);

    /*
     * 预处理完成，修改与处理结果
     */
	int updatePreprocessStatus(Long experimentId, int preFinishStatus);


	/**
	 * id 删除实验训练列表
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
	 * @param name
	 * @param id
	 * @return
	 */
	boolean checkTrainName(String name, Long id);

	/**
	 * 插入实验
	 * @param experiment
	 * @return
	 */
    Long insert(Experiment experiment);
}
