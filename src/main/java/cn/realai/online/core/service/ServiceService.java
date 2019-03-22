package cn.realai.online.core.service;

import cn.realai.online.core.bo.ServiceBO;
import cn.realai.online.core.entity.Service;

import java.util.List;

public interface ServiceService {

	Service get(Long serviceId);

	ServiceBO selectServiceById(long serviceId);

	List<Service> list(Service service);

	Integer insert(Service service);

	Integer delete(List<Long> ids);

	Integer update(Service service);



}
