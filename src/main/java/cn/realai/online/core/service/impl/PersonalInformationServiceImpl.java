package cn.realai.online.core.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.realai.online.core.entity.PersonalInformation;
import cn.realai.online.core.service.PersonalInformationService;

@Service
public class PersonalInformationServiceImpl implements PersonalInformationService {

	@Override
	public void insertList(List<PersonalInformation> piList) {
		
	}

	@Override
	public List<PersonalInformation> findListByExperimentId(Long experimentId) {
		return null;
	}

}
