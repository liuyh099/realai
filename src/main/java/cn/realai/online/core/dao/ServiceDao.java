package cn.realai.online.core.dao;

import cn.realai.online.core.entity.Service;

import java.util.List;

public interface ServiceDao {

    List<Service> findList(Service service);

    Integer insert(Service service);

    Integer delete(List<Long> ids);

    Service get(Long id);

    Integer update(Service service);

    List<Service> getAlreadyPublishService();
}
