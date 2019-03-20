package cn.realai.online.core.dao;

import org.apache.ibatis.annotations.Param;

import cn.realai.online.core.entity.Experiment;

import java.util.List;

public interface ExperimentDao {

	int insertModel(@Param("experiment") Experiment experiment);

	Experiment selectExperimentById(@Param("id") long id);

    List<Experiment> findList(Experiment experiment);

	int updatePreprocessStatus(@Param("experimentId")Long experimentId, 
			@Param("preFinishStatus")int preFinishStatus);

	int updateExperimentStatus(@Param("experimentId")long experimentId, 
			@Param("status")int status);

	/**
	 * 删除实验训练列表
	 * @param ids
	 * @return
	 */
	Integer deleteExperimentByIds(@Param("ids") List<Long> ids);
}
