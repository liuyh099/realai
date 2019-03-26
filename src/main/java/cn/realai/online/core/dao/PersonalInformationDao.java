package cn.realai.online.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.realai.online.core.entity.PersonalInformation;

public interface PersonalInformationDao {

    void insertList(@Param("piList") List<PersonalInformation> piList);

    List<PersonalInformation> findListByExperimentIdAndBatchId(@Param("experimentId") Long experimentId,
    		@Param("batchId")Long batchId);

    /**
     * 查询personal信息列表
     *
     * @param personal
     * @return
     */
    List<PersonalInformation> findList(PersonalInformation personal);

    /**
     * 根据ID 查询personal信息
     *
     * @param id
     * @return
     */
    PersonalInformation get(Long id);
}
