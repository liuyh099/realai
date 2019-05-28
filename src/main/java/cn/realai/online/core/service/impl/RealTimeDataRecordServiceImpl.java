package cn.realai.online.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.realai.online.core.dao.RealTimeDataRecordDao;
import cn.realai.online.core.entity.RealTimeDataRecord;
import cn.realai.online.core.service.RealTimeDataRecordService;

@Service
public class RealTimeDataRecordServiceImpl implements RealTimeDataRecordService {

	@Autowired
	private RealTimeDataRecordDao realTimeDataRecordDao;

	@Override
	public int insertRealTimeDataRecord(RealTimeDataRecord rtdr) {
		return realTimeDataRecordDao.insertRealTimeDataRecord(rtdr);
	}
	
}
