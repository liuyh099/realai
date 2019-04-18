package cn.realai.online.core.service.impl;

import cn.realai.online.core.dao.ServiceDao;
import cn.realai.online.core.entity.Service;
import cn.realai.online.core.service.TuningRecordService;
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

	@Autowired
	private LicenseCheckHandler licenseCheckHandler;

	@Autowired
	private TuningRecordService tuningRecordService;

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

		List<Service> olds = list(searchService);
		olds.forEach(oldService -> {
			if(StringUtils.isNotEmpty(oldService.getSecretKey())) {
				String secretkey = dataCipherHandler.getOriginalSecretKey(oldService.getSecretKey());
				if(StringUtils.equals(secretkey, service.getSecretKey())) {
					logger.error("服务秘钥已被使用！");
					throw new RuntimeException("服务秘钥已被使用！");
				}
			}
		});

		licenseCheckHandler.cancelSecretKeyCheck(service.getSecretKey());

		ServiceDetail detail = new ServiceDetail();
		detail.setDeployUseTimes("0");
		detail.setServiceName(service.getName());
		detail.setVersion(0);
		FileLicenseInfo fileLicenseInfo = serviceLicenseInfoSource.checkSource(service.getSecretKey());


		if(SecretKeyType.COMMON.getCode() != Integer.parseInt(fileLicenseInfo.getSecretKeyType())) {
			throw new RuntimeException("当前秘钥与该服务类型不匹配！");
		}

		if(StringUtils.isNotEmpty(fileLicenseInfo.getCancelSecretKey())) {
			detail.setTuningKeyIds(fileLicenseInfo.getCancelSecretKey());

			List<String> cancelSecretKeyList = licenseCheckHandler.getCancelSecretKeyList(fileLicenseInfo);

			for (String cancelSecretKey : cancelSecretKeyList) {
				if(StringUtils.isNotEmpty(cancelSecretKey)) {
					tuningRecordService.invalidateBySecretKey(cancelSecretKey);
				}
			}
		}


//		if(fileLicenseInfo.getOverdue() > 0 && (new Date().getTime() > fileLicenseInfo.getOverdue())) {
//			throw new RuntimeException("秘钥已过期");
//		}
		service.setDetail(dataCipherHandler.encryptData(detail, service.getSecretKey()));
		service.setSecretKey(dataCipherHandler.initSecretKey(service.getSecretKey(), detail.getVersion()));
		service.setCreateTime(new Date().getTime());
		service.setType(Integer.parseInt(fileLicenseInfo.getServiceType()));
		service.setBusinessType(Integer.parseInt(fileLicenseInfo.getBusinessType()));
		Integer result = serviceDao.insert(service);

		secretKeyHandler(fileLicenseInfo);

		return result;
	}


	public void secretKeyHandler (FileLicenseInfo fileLicenseInfo) {
		List<Service> targetServices = serviceDao.findList(new Service());
		List<String> stopSecretKeyList = licenseCheckHandler.getStopSecretKeyList(fileLicenseInfo);
		if(stopSecretKeyList != null && !stopSecretKeyList.isEmpty()) {
			for (String stopSecretKey : stopSecretKeyList) {
				if(targetServices != null && !targetServices.isEmpty()) {
					for(Service targetService : targetServices) {
						String targetSecretkey = dataCipherHandler.getOriginalSecretKey(targetService.getSecretKey());
						try {
							FileLicenseInfo targetLicInfo = serviceLicenseInfoSource.checkSource(targetSecretkey);
							if(StringUtils.equals(stopSecretKey, targetLicInfo.getId())) {
								ServiceDetail targetServiceDetail = dataCipherHandler.getDateJsonByCiphertext(targetService.getDetail());
								targetServiceDetail.setStatus(ServiceDetail.STATUS_STOP);
								targetService.setDetail(dataCipherHandler.encryptData(targetServiceDetail, targetSecretkey));
								serviceDao.update(targetService);
							}
						} catch (LicenseException e) {
						}
					}
				}
			}
		}
	}


	@Override
	@Transactional(readOnly = false)
	public Integer delete(List<Long> ids) {
		return serviceDao.delete(ids);
	}


	@Override
	@Transactional(readOnly = false)
	public Integer update(Service service) {
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
				ServiceDetail serviceDetail = dataCipherHandler.getDateJsonByCiphertext(searchService.getDetail());
				int version = serviceDetail.getVersion();
				String ciphertext = dataCipherHandler.getOriginalSecretKey(searchService.getSecretKey(), version);
				serviceLicenseCheck.checkServiceLic(ciphertext);
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
				ServiceDetail serviceDetail = dataCipherHandler.getDateJsonByCiphertext(service.getDetail());
				secretKey = dataCipherHandler.getOriginalSecretKey(secretKey, serviceDetail.getVersion());
				FileLicenseInfo fileLicenseInfo = serviceLicenseInfoSource.servicLicDecrypt(secretKey);
				fileLicenseInfo.getRangeTimeLower();
				service.setExpireDate(DateUtil.stringToLong(fileLicenseInfo.getRangeTimeUpper(), LicenseConstants.DATE_FORMART));
				service.setStartTime(DateUtil.stringToLong(fileLicenseInfo.getRangeTimeLower(), LicenseConstants.DATE_FORMART));
				service.setDeployTimesUpper(Integer.parseInt(fileLicenseInfo.getDeployTimesUpper()));
				service.setType(Integer.parseInt(fileLicenseInfo.getServiceType()));
				if(serviceDetail.getStatus() == ServiceDetail.STATUS_STOP) {
					service.setDiscard(true);
				}
				if(StringUtils.isNotBlank(fileLicenseInfo.getBusinessType())) {
					service.setBusinessType(Integer.parseInt(fileLicenseInfo.getBusinessType()));
				}

				service.setAvailable(true);
				try {
					serviceLicenseInfoSource.verifyLimit(fileLicenseInfo);
					serviceLicenseInfoSource.timesUpperCheck(fileLicenseInfo, serviceDetail);
				}catch (Exception e) {
					service.setAvailable(false);
				}

			} catch (LicenseException e) { 
				logger.error("非法秘钥 secretKey："+secretKey, e);
			}
		}
		return service;
	}

	@Override
	public List<Service> findListByModelStatus(String status) {
		return serviceDao.findListByModelStatus(status);
	}

	@Override
	public List<Service> findListByAlreadyPublishModel() {
		return serviceDao.findListByAlreadyPublishModel();
	}
}
