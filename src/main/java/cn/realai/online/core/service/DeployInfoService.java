package cn.realai.online.core.service;

import cn.realai.online.core.entity.DeployInfo;

public interface DeployInfoService {

	DeployInfo selectDeployInfoById(Long serviceId);

	int insertDeployInfo(DeployInfo deployInfo);

	int updateDeployInfo(DeployInfo deployInfo);

	boolean checkgetSecretKey(String licKey);  

}
