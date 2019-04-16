package cn.realai.online.core.dao;

import cn.realai.online.core.entity.TuningRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TuningRecordDao {

	Integer insert(TuningRecord record);

	Integer update(TuningRecord record);

    Integer updateByServiceIdAndType(TuningRecord record);

    TuningRecord get(Long id);

    List<TuningRecord> findList(TuningRecord record);

    //根据模型ids查询每个模型最近一次的调优记录
    List<TuningRecord> findLatestListByModelIds(List<Long> modelIds);

    List<TuningRecord> findBySecretKeyAndStatus(@Param("secretKey") String secretKey, @Param("status") String status);
}
