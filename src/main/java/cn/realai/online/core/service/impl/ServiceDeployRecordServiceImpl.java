package cn.realai.online.core.service.impl;

import cn.realai.online.core.dao.ServiceDeployRecordDao;
import cn.realai.online.core.entity.ServiceDeployRecord;
import cn.realai.online.core.service.ServiceDeployRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceDeployRecordServiceImpl implements ServiceDeployRecordService {

    @Autowired
    private ServiceDeployRecordDao serviceDeployRecordDao;

    @Override
    public int insert(ServiceDeployRecord serviceDeployRecord) {
        return serviceDeployRecordDao.insert(serviceDeployRecord);
    }

    @Override
    public List<ServiceDeployRecord> findList(ServiceDeployRecord serviceDeployRecord) {
        return serviceDeployRecordDao.findList(serviceDeployRecord);
    }
}
