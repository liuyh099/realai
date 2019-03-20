package cn.realai.online.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.realai.online.core.dao.PersonalInformationDao;
import cn.realai.online.core.entity.PersonalInformation;
import cn.realai.online.core.service.PersonalInformationService;

@Service
public class PersonalInformationServiceImpl implements PersonalInformationService {

	@Autowired
	private PersonalInformationDao personalInformationDao;
	
	@Override
	public void insertList(List<PersonalInformation> piList) {
		if (piList == null || piList.size() == 0) {
			return ;
		}
		for (PersonalInformation pi : piList) {
			pi.setCreateTime(System.currentTimeMillis());
		}
		personalInformationDao.insertList(piList);
	}

	@Override
	public List<PersonalInformation> findListByExperimentId(Long experimentId) {
		return null;
	}

}
