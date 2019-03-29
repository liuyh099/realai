package cn.realai.online.calculation.requestbo;

/**
 * 线上实时接口
 * @author lyh
 */
public class RealTimeRequestBO {

	private String command;
	
	private Long modelId;
	
	private String jsonData;

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
	
}
