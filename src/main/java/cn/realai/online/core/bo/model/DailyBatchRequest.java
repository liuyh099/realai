package cn.realai.online.core.bo.model;

public class DailyBatchRequest {

	private Long modelId;
	
	private String xtableHetro;
	
	private String xtableHomo;
	
	private String ytable;
	
	private String batchDate;

	public String getBatchDate() {
		return batchDate;
	}

	public void setBatchDate(String batchDate) {
		this.batchDate = batchDate;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public String getXtableHetro() {
		return xtableHetro;
	}

	public void setXtableHetro(String xtableHetro) {
		this.xtableHetro = xtableHetro;
	}

	public String getXtableHomo() {
		return xtableHomo;
	}

	public void setXtableHomo(String xtableHomo) {
		this.xtableHomo = xtableHomo;
	}

	public String getYtable() {
		return ytable;
	}

	public void setYtable(String ytable) {
		this.ytable = ytable;
	}
	
}
