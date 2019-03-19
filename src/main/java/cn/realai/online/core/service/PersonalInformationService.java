package cn.realai.online.core.service;

import java.util.List;

import cn.realai.online.core.entity.PersonalInformation;

public interface PersonalInformationService {

	void insertList(List<PersonalInformation> piList);

	List<PersonalInformation> findListByExperimentId(Long experimentId);

}
