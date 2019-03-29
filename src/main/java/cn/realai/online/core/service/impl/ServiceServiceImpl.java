package cn.realai.online.core.service.impl;

import cn.realai.online.core.dao.ServiceDao;
import cn.realai.online.core.entity.Service;
import cn.realai.online.lic.*;
import cn.realai.online.util.DateUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.realai.online.core.bo.ServiceBO;
import cn.realai.online.core.service.ServiceService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@org.springframework.stereotype.Service
@Transactional(readOnly = true)
public class ServiceServiceImpl implements ServiceService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ServiceDao serviceDao;

	@Autowired
	private ServiceLicenseCheck serviceLicenseCheck;

	@Autowired
	private DataCipherHandler dataCipherHandler;

	@Autowired
	private ServiceLicenseInfoSource serviceLicenseInfoSource;

	@Override
	public Service get(Long serviceId) {
		return serviceDao.get(serviceId);
	}

	@Override
	public ServiceBO selectServiceById(long serviceId) {
		Service service = serviceDao.get(serviceId);
		if(service == null) return null;

		convertData(service);
		ServiceBO serviceBO = new ServiceBO();
		BeanUtils.copyProperties(service, serviceBO);
		return serviceBO;
	}


	@Override
	public List<Service> list(Service service) {
		List<Service> services = serviceDao.findList(service);
		if(services != null && !services.isEmpty()) {
			for (Service s : services) {
				convertData(s);
			}
		}
		return services;
	}

	@Override
	@Transactional(readOnly = false)
	public Integer insert(Service service) throws LicenseException {
		Service searchService = new Service();
		searchService.setName(service.getName());
		List old = list(searchService);
		if(old != null && old.size() > 0) {
			logger.error("服务名称已存在！");
			throw new RuntimeException("服务名称已存在！");
		}

		searchService = new Service();
		searchService.setSecretKey(service.getSecretKey());
		old = list(searchService);
		if(old != null && old.size() > 0) {
			logger.error("服务秘钥已被使用！");
			throw new RuntimeException("服务秘钥已被使用！");
		}

		if(StringUtils.isNotBlank(service.getTuningSecretKey())) {
			searchService = new Service();
			searchService.setTuningSecretKey(service.getTuningSecretKey());
			old = list(searchService);
			if(old != null && old.size() > 0) {
				logger.error("调优秘钥已被使用！");
				throw new RuntimeException("调优秘钥已被使用！");
			}
		}

		ServiceDetail detail = new ServiceDetail();
		detail.setDeployUseTimes("0");
		detail.setServiceName(service.getName());
		service.setDetail(dataCipherHandler.encryptData(detail, service.getSecretKey()));
		FileLicenseInfo fileLicenseInfo = serviceLicenseInfoSource.checkSource(service.getSecretKey());
		service.setCreateTime(new Date().getTime());
		service.setType(Integer.parseInt(fileLicenseInfo.getServiceType()));
		return serviceDao.insert(service);
	}

	@Override
	@Transactional(readOnly = false)
	public Integer delete(List<Long> ids) {
		return serviceDao.delete(ids);
	}


	@Override
	@Transactional(readOnly = false)
	public Integer update(Service service) {
//		cn.realai.online.core.entity.Service old = selectServiceById(service.getId());
//		BeanUtils.copyProperties(service, old);
		return serviceDao.update(service);
	}

	@Override
	public List<ServiceBO> getAlreadyPublishService() {
		List<Service> serviceList =serviceDao.getAlreadyPublishService();
		List<ServiceBO> list = JSON.parseArray(JSON.toJSONString(serviceList),ServiceBO.class);
		return list;
	}

	@Override
	public boolean checkService(long serviceId) {
		Service searchService = get(serviceId);
		if(searchService != null && StringUtils.isNotBlank(searchService.getSecretKey())) {
			try {
				serviceLicenseCheck.checkServiceLic(searchService.getSecretKey());
				return true;
			} catch (LicenseException e) {
			}
		}
		return false;
	}
	/*@Override
	public void online(Long serviceId) {
		 serviceDao.online(serviceId);
	}*/



	public Service convertData(Service service) {
		String secretKey =  service.getSecretKey();
		try {
			int releaseCount = Integer.parseInt(dataCipherHandler.getDateJsonByCiphertext(service.getDetail()).getDeployUseTimes());
			service.setReleaseCount(releaseCount);
		} catch (LicenseException e) {
//			logger.error("读取解密信息失败！", e);
		}
		if(secretKey != null) {
			try {
				FileLicenseInfo fileLicenseInfo = serviceLicenseCheck.checkServiceLic(secretKey);
				fileLicenseInfo.getRangeTimeLower();
				service.setExpireDate(DateUtil.stringToLong(fileLicenseInfo.getRangeTimeUpper(), LicenseConstants.DATE_FORMART));
				service.setStartTime(DateUtil.stringToLong(fileLicenseInfo.getRangeTimeLower(), LicenseConstants.DATE_FORMART));
				service.setDeployTimesUpper(Integer.parseInt(fileLicenseInfo.getDeployTimesUpper()));
				service.setType(Integer.parseInt(fileLicenseInfo.getServiceType()));
				if(StringUtils.isNotBlank(fileLicenseInfo.getBusinessType())) {
					service.setBusinessType(Integer.parseInt(fileLicenseInfo.getBusinessType()));
				}
			} catch (LicenseException e) {
				logger.error("非法秘钥 secretKey："+secretKey, e);
			}
		}
		return service;
	}

}
