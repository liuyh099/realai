package cn.realai.online.calculation.requestbo;

import cn.realai.online.common.Constant;

public class DeployRequestBO {

	private Long modelId;
	
	private Long originalId;
	
	private final String command = Constant.COMMAND_DEPLOY;
	
	private String secretKey;
	
	private Long serviceId;


	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public String getCommand() {
		return command;
	}

	public Long getOriginalId() {
		return originalId;
	}

	public void setOriginalId(Long originalId) {
		this.originalId = originalId;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	
}
