package cn.realai.online.core.service.impl;

import cn.realai.online.core.dao.ServiceDao;
import cn.realai.online.lic.FileLicenseInfo;
import cn.realai.online.lic.LicenseException;
import cn.realai.online.lic.ServiceLicenseCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.realai.online.core.bo.ServiceBO;
import cn.realai.online.core.service.ServiceService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ServiceServiceImpl implements ServiceService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ServiceDao serviceDao;

	@Autowired
	private ServiceLicenseCheck serviceLicenseCheck;
	
	@Override
	public ServiceBO selectServiceById(long serviceId) {
		cn.realai.online.core.entity.Service service = serviceDao.get(serviceId);
		if(service == null) return null;

		ServiceBO serviceBO = new ServiceBO();
		BeanUtils.copyProperties(service, serviceBO);
		return serviceBO;
	}

	@Override
	public List<cn.realai.online.core.entity.Service> list(cn.realai.online.core.entity.Service service) {
		return serviceDao.findList(service);
	}

	@Override
	@Transactional(readOnly = false)
	public Integer insert(cn.realai.online.core.entity.Service service) {
		cn.realai.online.core.entity.Service searchService = new cn.realai.online.core.entity.Service();
		searchService.setName(service.getName());
		List old = list(searchService);
		if(old != null && old.size() > 0) {
			logger.error("服务名称已存在！");
			throw new RuntimeException("服务名称已存在！");
		}
		searchService.setSecretKey(service.getSecretKey());
		old = list(searchService);
		if(old != null && old.size() > 0) {
			logger.error("服务秘钥已被使用！");
			throw new RuntimeException("服务秘钥已被使用！");
		}
		searchService.setTuningSecretKey(service.getTuningSecretKey());
		old = list(searchService);
		if(old != null && old.size() > 0) {
			logger.error("调优秘钥已被使用！");
			throw new RuntimeException("调优秘钥已被使用！");
		}

		return serviceDao.insert(service);
	}

	@Override
	@Transactional(readOnly = false)
	public Integer delete(List<Long> ids) {
		return serviceDao.delete(ids);
	}


	@Override
	@Transactional(readOnly = false)
	public Integer update(cn.realai.online.core.entity.Service service) {
//		cn.realai.online.core.entity.Service old = selectServiceById(service.getId());
//		BeanUtils.copyProperties(service, old);
		return serviceDao.update(service);
	}


	public cn.realai.online.core.entity.Service convertData(long serviceId) {
		cn.realai.online.core.entity.Service service = selectServiceById(serviceId);
		String secretKey =  service.getSecretKey();
//		service.setReleaseCount(fileLicenseInfo.ge);
		if(secretKey != null) {
			try {
				FileLicenseInfo fileLicenseInfo = serviceLicenseCheck.checkServiceLic(secretKey);
				fileLicenseInfo.getRangeTimeLower();
//				service.setExpireDate(fileLicenseInfo.getRangeTimeUpper());
				
			} catch (LicenseException e) {
				logger.error("非法秘钥 secretKey："+secretKey, e);
			}
		}
		return service;
	}

}
