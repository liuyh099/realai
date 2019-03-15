package cn.realai.online.core.entity;

/**
 * psi检测结果
 * @author lyh
 */
public class PSICheckResult {

	private long id;
	
	//值
	private double psi;
	
	private String variableName;
	
	private long experimentId;
	
	//预警标志
	private int alter; //1.不预警 2.预警

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getPsi() {
		return psi;
	}

	public void setPsi(double psi) {
		this.psi = psi;
	}

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	public long getExperimentId() {
		return experimentId;
	}

	public void setExperimentId(long experimentId) {
		this.experimentId = experimentId;
	}

	public int getAlter() {
		return alter;
	}

	public void setAlter(int alter) {
		this.alter = alter;
	}
	
}
