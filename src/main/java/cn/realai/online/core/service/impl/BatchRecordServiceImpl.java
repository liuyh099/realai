package cn.realai.online.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.realai.online.core.dao.BatchRecordDao;
import cn.realai.online.core.entity.BatchRecord;
import cn.realai.online.core.service.BatchRecordService;

@Service
public class BatchRecordServiceImpl implements BatchRecordService {

	@Autowired
	private BatchRecordDao batchRecordDao;
	
	@Override
	public Long insert(BatchRecord batchRecord) {
		batchRecord.setCreateTime(System.currentTimeMillis());
		return batchRecordDao.insert(batchRecord);
	}

}
