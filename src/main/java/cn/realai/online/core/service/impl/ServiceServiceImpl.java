package cn.realai.online.core.service.impl;

import org.springframework.stereotype.Service;

import cn.realai.online.core.bo.ServiceBO;
import cn.realai.online.core.service.ServiceService;

import java.util.List;

@Service
public class ServiceServiceImpl implements ServiceService {

    @Override
    public ServiceBO selectServiceById(long serviceId) {
        return null;
    }

    @Override
    public List<cn.realai.online.core.entity.Service> list(cn.realai.online.core.entity.Service service) {
        return null;
    }

    @Override
    public Integer insert(cn.realai.online.core.entity.Service service) {
        return null;
    }

    @Override
    public Integer delete(List<Long> ids) {
        return null;
    }

    @Override
    public cn.realai.online.core.entity.Service get(Long id) {
        return null;
    }

    @Override
    public Integer update(cn.realai.online.core.entity.Service service) {
        return null;
    }

}
