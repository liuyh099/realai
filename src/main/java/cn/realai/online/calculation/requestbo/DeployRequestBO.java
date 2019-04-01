package cn.realai.online.calculation.requestbo;

import cn.realai.online.common.Constant;

public class DeployRequestBO {

	private Long modelId;
	
	private Long originalId;
	
	private final String command = Constant.COMMAND_DEPLOY;


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
	
}
