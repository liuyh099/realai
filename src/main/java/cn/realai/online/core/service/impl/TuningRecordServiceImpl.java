package cn.realai.online.core.service.impl;

import cn.realai.online.core.dao.TuningRecordDao;
import cn.realai.online.core.entity.BatchRecord;
import cn.realai.online.core.entity.TuningRecord;
import cn.realai.online.core.service.TuningRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能描述：TODO
 *
 * @author admin
 * @create 2019-03-22 17:56
 */
@Service
public class TuningRecordServiceImpl implements TuningRecordService {

    @Autowired
    private TuningRecordDao tuningRecordDao;

    @Override
    public Integer insert(TuningRecord record) {
        return tuningRecordDao.insert(record);
    }

    @Override
    public Integer update(TuningRecord record) {
        return tuningRecordDao.update(record);
    }

    @Override
    public Integer updateByServiceIdAndType(TuningRecord record) {
        return tuningRecordDao.updateByServiceIdAndType(record);
    }

    @Override
    public BatchRecord get(Long id) {
        return tuningRecordDao.get(id);
    }

    @Override
    public BatchRecord findList(TuningRecord record) {
        return tuningRecordDao.findList(record);
    }
}
