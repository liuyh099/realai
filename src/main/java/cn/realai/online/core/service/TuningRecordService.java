package cn.realai.online.core.service;

import cn.realai.online.core.entity.BatchRecord;
import cn.realai.online.core.entity.TuningRecord;

import java.util.List;

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

    TuningRecord get(Long id);

    List<TuningRecord> findList(TuningRecord record);

}