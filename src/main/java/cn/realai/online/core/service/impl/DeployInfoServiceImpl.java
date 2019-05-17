package cn.realai.online.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.realai.online.core.dao.DeployInfoDao;
import cn.realai.online.core.entity.DeployInfo;
import cn.realai.online.core.service.DeployInfoService;
import cn.realai.online.lic.FileLicenseInfo;
import cn.realai.online.lic.LicenseException;
import cn.realai.online.lic.ServiceLicenseInfoSource;

@Service
public class DeployInfoServiceImpl implements DeployInfoService {

	@Autowired
	private DeployInfoDao deployInfoDao;
	
	@Autowired
	private ServiceLicenseInfoSource serviceLicenseInfoSource;
	
	@Override
	public DeployInfo selectDeployInfoById(Long serviceId) {
		return deployInfoDao.selectDeployInfoById(serviceId);
	}

	@Override
	public int insertDeployInfo(DeployInfo deployInfo) {
		if (checkgetSecretKey(deployInfo.getSecretKey())) {
			return -1;
		}
		deployInfo.setCreateTime(System.currentTimeMillis());
		return deployInfoDao.insertDeployInfo(deployInfo);
	}

	@Override
	public int updateDeployInfo(DeployInfo deployInfo) {
		if (checkgetSecretKey(deployInfo.getSecretKey())) { 
			return -1;  
		}
		deployInfo.setUpdateTime(System.currentTimeMillis());
		return deployInfoDao.updateDeployInfo(deployInfo);
	}

	//检查密钥是否可用
	@Override
	public boolean checkgetSecretKey(String licKey) {
		try {
			FileLicenseInfo licenseInfo = serviceLicenseInfoSource.checkSource(licKey);
			return true;
		} catch (LicenseException e) {
			e.printStackTrace();
		}
		return false;
	}

}
