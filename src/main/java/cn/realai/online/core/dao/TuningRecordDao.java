package cn.realai.online.core.dao;

import cn.realai.online.core.entity.BatchRecord;
import cn.realai.online.core.entity.TuningRecord;

public interface TuningRecordDao {

	Integer insert(TuningRecord record);

	Integer update(TuningRecord record);

    Integer updateByServiceIdAndType(TuningRecord record);

    BatchRecord get(Long id);

    BatchRecord findList(TuningRecord record);
}
