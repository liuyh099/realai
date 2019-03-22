package cn.realai.online.calculation.requestbo;

import cn.realai.online.common.Constant;

public class DeployRequestBO {

	private Long experimentId;
	
	private final String command = Constant.COMMAND_DEPLOY;

	public Long getExperimentId() {
		return experimentId;
	}

	public void setExperimentId(Long experimentId) {
		this.experimentId = experimentId;
	}

	public String getCommand() {
		return command;
	}
	
}
