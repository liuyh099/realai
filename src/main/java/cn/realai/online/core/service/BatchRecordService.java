package cn.realai.online.core.service;

import cn.realai.online.core.entity.BatchRecord;

public interface BatchRecordService {

    Long insert(BatchRecord batchRecord);

    /**
     * 根据ID和其他条件查询批次信息
     *
     * @param batchRecord
     * @return
     */
    BatchRecord getByEntity(BatchRecord batchRecord);
}
