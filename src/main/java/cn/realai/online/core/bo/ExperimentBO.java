package cn.realai.online.core.bo;

import cn.realai.online.core.entity.Experiment;

public class ExperimentBO extends Experiment{

	//服务名称
	private String serviceName;
	
	//状态名称
	private String statusName;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
}
