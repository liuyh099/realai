package cn.realai.online.calculation.requestbo;

public class BatchOfOfflineRequestBO {
	
	private String command;
	
	private Long modelId;
	private Long batchId; 
	private String xtableHeterogeneousDataSource;
	private String xtableHomogeneousDataSource;
	private String ytableDataSource;
	public Long getModelId() {
		return modelId;
	}
	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}
	public Long getBatchId() {
		return batchId;
	}
	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}
	public String getXtableHeterogeneousDataSource() {
		return xtableHeterogeneousDataSource;
	}
	public void setXtableHeterogeneousDataSource(String xtableHeterogeneousDataSource) {
		this.xtableHeterogeneousDataSource = xtableHeterogeneousDataSource;
	}
	public String getXtableHomogeneousDataSource() {
		return xtableHomogeneousDataSource;
	}
	public void setXtableHomogeneousDataSource(String xtableHomogeneousDataSource) {
		this.xtableHomogeneousDataSource = xtableHomogeneousDataSource;
	}
	public String getYtableDataSource() {
		return ytableDataSource;
	}
	public void setYtableDataSource(String ytableDataSource) {
		this.ytableDataSource = ytableDataSource;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	
}
