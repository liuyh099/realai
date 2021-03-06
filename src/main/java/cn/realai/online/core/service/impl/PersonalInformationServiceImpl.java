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
            return;
        }
        for (PersonalInformation pi : piList) {
            pi.setCreateTime(System.currentTimeMillis());
        }
        personalInformationDao.insertList(piList);
    }

    @Override
    public List<PersonalInformation> findListByExperimentIdAndBatchId(Long experimentId, Long batchId) {
        return personalInformationDao.findListByExperimentIdAndBatchId(experimentId, batchId);
    }

    @Override
    public List<PersonalInformation> findList(PersonalInformation personal) {
        return personalInformationDao.findList(personal);
    }

    @Override
    public PersonalInformation get(Long id) {
        return personalInformationDao.get(id);
    }

	@Override
	public Long insert(PersonalInformation personalInformation) {
		if (personalInformation == null) {
            return null;
        }
		personalInformation.setCreateTime(System.currentTimeMillis());
        personalInformationDao.insert(personalInformation);
        return personalInformation.getId();
	}

}
