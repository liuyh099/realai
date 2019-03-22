package cn.realai.online.core.dao;

import cn.realai.online.core.entity.TuningRecord;

import java.util.List;

public interface TuningRecordDao {

	Integer insert(TuningRecord record);

	Integer update(TuningRecord record);

    Integer updateByServiceIdAndType(TuningRecord record);

    TuningRecord get(Long id);

    List<TuningRecord> findList(TuningRecord record);
}
