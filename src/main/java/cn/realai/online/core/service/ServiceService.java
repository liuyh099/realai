package cn.realai.online.core.service;

import cn.realai.online.core.bo.ServiceBO;
import cn.realai.online.core.entity.Service;
import cn.realai.online.lic.FileLicenseInfo;
import cn.realai.online.lic.LicenseException;

import java.util.List;

public interface ServiceService {

	Service get(Long serviceId);

	ServiceBO selectServiceById(long serviceId);

	List<Service> list(Service service);

	Integer insert(Service service) throws LicenseException;

	Integer delete(List<Long> ids);

	Integer update(Service service);


    List<ServiceBO> getAlreadyPublishService();

   // void online(Long serviceId);

	/**
	 * 检查服务是否可用
	 *
	 * @param serviceId
	 * @return
     */
	boolean checkService(long serviceId);

	List<Service> findListByModelStatus(String status);

	List<Service> findListByAlreadyPublishModel();

	void secretKeyHandler (FileLicenseInfo fileLicenseInfo);

}
