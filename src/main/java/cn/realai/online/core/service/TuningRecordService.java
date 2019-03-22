package cn.realai.online.core.service;

import cn.realai.online.core.entity.BatchRecord;
import cn.realai.online.core.entity.TuningRecord;

/**
 * 功能描述：TODO
 *
 * @author admin
 * @create 2019-03-22 17:55
 */
public interface TuningRecordService {

    Integer insert(TuningRecord record);

    Integer update(TuningRecord record);

    Integer updateByServiceIdAndType(TuningRecord record);

    BatchRecord get(Long id);

    BatchRecord findList(TuningRecord record);

}
