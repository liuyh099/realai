package cn.realai.online.calculation.requestbo;

import cn.realai.online.common.Constant;

public class DeleteExperimentRequestBO {

	private Long modelId;
	
	private final String command = Constant.COMMAND_DELETE;

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public String getCommand() {
		return command;
	}
	
}
