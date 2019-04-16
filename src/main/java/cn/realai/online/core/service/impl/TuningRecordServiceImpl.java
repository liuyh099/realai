package cn.realai.online.core.service.impl;

import cn.realai.online.core.dao.TuningRecordDao;
import cn.realai.online.core.entity.TuningRecord;
import cn.realai.online.core.service.TuningRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * 功能描述：TODO
 *
 * @author admin
 * @create 2019-03-22 17:56
 */
@Service
public class TuningRecordServiceImpl implements TuningRecordService {

    private static final Logger log = LoggerFactory.getLogger(TuningRecordServiceImpl.class);

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
    public TuningRecord get(Long id) {
        return tuningRecordDao.get(id);
    }

    @Override
    public List<TuningRecord> findList(TuningRecord record) {
        return tuningRecordDao.findList(record);
    }

    @Override
    public List<TuningRecord> findLatestListByModelIds(List<Long> modelIds) {
        return tuningRecordDao.findLatestListByModelIds(modelIds);
    }

    @Override
    public List<TuningRecord> findBySecretKeyAndStatus(String secretKey, String status) {
        return tuningRecordDao.findBySecretKeyAndStatus(secretKey, status);
    }

    @Override
    public void invalidateBySecretKey(String secretKey) {
        List<TuningRecord> tuningRecords = tuningRecordDao.findBySecretKeyAndStatus(secretKey, TuningRecord.STATUS.VALID.value);
        if (!CollectionUtils.isEmpty(tuningRecords)) {
            for (TuningRecord record:tuningRecords) {
                log.warn("调优记录ID:{}被置为无效", record.getId());
                record.setStatus(TuningRecord.STATUS.INVALID.value);
                record.setUpdateTime(new Date().getTime());
                tuningRecordDao.update(record);
            }
        } else {
            log.warn("调优秘钥{}无相关调优记录", secretKey);
        }
    }
}
