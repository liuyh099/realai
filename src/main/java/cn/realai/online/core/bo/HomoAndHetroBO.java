package cn.realai.online.core.bo;

import java.util.List;

import cn.realai.online.core.entity.VariableData;

public class HomoAndHetroBO {

	private Long experimentId;
	
	private List<VariableData> homoList;
	
	private List<VariableData> hetroList;

	public HomoAndHetroBO(Long experimentId) {
		this.experimentId = experimentId;
	}
	
	public List<VariableData> getHomoList() {
		return homoList;
	}

	public void setHomoList(List<VariableData> homoList) {
		this.homoList = homoList;
	}

	public List<VariableData> getHetroList() {
		return hetroList;
	}

	public void setHetroList(List<VariableData> hetroList) {
		this.hetroList = hetroList;
	}

	public Long getExperimentId() {
		return experimentId;
	}

	public void setExperimentId(Long experimentId) {
		this.experimentId = experimentId;
	}
	
}
