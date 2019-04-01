package cn.realai.online.core.bussiness.impl;

import cn.realai.online.core.bo.ServiceDeployRecordBO;
import cn.realai.online.core.bussiness.ServiceDeployRecordBussiness;
import cn.realai.online.core.entity.ServiceDeployRecord;
import cn.realai.online.core.service.ServiceDeployRecordService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ServiceDeployRecordBussinessImpl implements ServiceDeployRecordBussiness {

    @Autowired
    private ServiceDeployRecordService serviceDeployRecordService;

    @Override
    @Transactional(readOnly = false)
    public int insert(ServiceDeployRecordBO serviceDeployRecordBO) {
        ServiceDeployRecord serviceDeployRecord = new ServiceDeployRecord();
        BeanUtils.copyProperties(serviceDeployRecordBO, serviceDeployRecord);
        return serviceDeployRecordService.insert(serviceDeployRecord);
    }

    @Override
    public List<ServiceDeployRecordBO> findServiceDeployRecordByServiceId(Long serviceId) {
        ServiceDeployRecord serviceDeployRecord = new ServiceDeployRecord();
        serviceDeployRecord.setServiceId(serviceId);
        List<ServiceDeployRecord> list = serviceDeployRecordService.findList(serviceDeployRecord);
        List<ServiceDeployRecordBO> result = JSON.parseArray(JSON.toJSONString(list),ServiceDeployRecordBO.class);
        return result;
    }
}
