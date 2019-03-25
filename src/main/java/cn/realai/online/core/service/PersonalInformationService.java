package cn.realai.online.core.service;

import java.util.List;

import cn.realai.online.core.entity.PersonalInformation;

public interface PersonalInformationService {

    void insertList(List<PersonalInformation> piList);

    List<PersonalInformation> findListByExperimentIdAndBatchId(Long experimentId, Long batchId);

    /**
     * 查询personal信息
     *
     * @param personal
     * @return
     */
    List<PersonalInformation> findList(PersonalInformation personal);

    /**
     * 根据id查询personal信息
     *
     * @param id
     * @return
     */
    PersonalInformation get(Long id);
}
