package cn.realai.online.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.realai.online.core.entity.PersonalInformation;

public interface PersonalInformationDao {

	void insertList(@Param("piList")List<PersonalInformation> piList);

	List<PersonalInformation> findListByExperimentId(@Param("experimentId")Long experimentId);

}
