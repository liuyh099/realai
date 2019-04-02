package cn.realai.online.calculation.requestbo;

public class DeleteExperimentRequestBO {

	private Long modelId;
	
	private Long ExperimentId;
	
	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public Long getExperimentId() {
		return ExperimentId;
	}

	public void setExperimentId(Long experimentId) {
		ExperimentId = experimentId;
	}
	
}
