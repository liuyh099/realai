package cn.realai.online.calculation.requestbo;

import java.util.List;

public class TrainRequestBO {

	//实验id
	private long experimentId;
	
	//如果是而此实验，这个字段为原实验id
	private Long parentId;   
    
	//命令
	private String command;
	
	private Integer validSampleType = 1;
	
	private Integer testSampleType = 1;
	
	//训练比例
    private Integer trainRatio;
    
    //测试比例
    private Integer testRatio;
    
    //验证比例
    private Integer validRatio;
    
    //异质需要训练列
    private List<String> columnsHetero;
    
    //同质需要训练列
    private List<String> columnsHomo;
    
    //异质删除列
    private List<String> deleteColumnsHetero;
    
    //同质删除列
    private List<String> deleteColumnsHomo;
    
	public long getExperimentId() {
		return experimentId;
	}

	public void setExperimentId(long experimentId) {
		this.experimentId = experimentId;
	}

	public String getCommand() {
		return command;
	}

	public Integer getTrainRatio() {
		return trainRatio;
	}

	public void setTrainRatio(Integer trainRatio) {
		this.trainRatio = trainRatio;
	}

	public Integer getTestRatio() {
		return testRatio;
	}

	public void setTestRatio(Integer testRatio) {
		this.testRatio = testRatio;
	}

	public Integer getValidRatio() {
		return validRatio;
	}

	public void setValidRatio(Integer validRatio) {
		this.validRatio = validRatio;
	}

	public List<String> getColumnsHetero() {
		return columnsHetero;
	}

	public void setColumnsHetero(List<String> columnsHetero) {
		this.columnsHetero = columnsHetero;
	}

	public List<String> getColumnsHomo() {
		return columnsHomo;
	}

	public void setColumnsHomo(List<String> columnsHomo) {
		this.columnsHomo = columnsHomo;
	}

	public List<String> getDeleteColumnsHetero() {
		return deleteColumnsHetero;
	}

	public void setDeleteColumnsHetero(List<String> deleteColumnsHetero) {
		this.deleteColumnsHetero = deleteColumnsHetero;
	}

	public List<String> getDeleteColumnsHomo() {
		return deleteColumnsHomo;
	}

	public void setDeleteColumnsHomo(List<String> deleteColumnsHomo) {
		this.deleteColumnsHomo = deleteColumnsHomo;
	}

	public Integer getValidSampleType() {
		return validSampleType;
	}

	public void setValidSampleType(Integer validSampleType) {
		this.validSampleType = validSampleType;
	}

	public Integer getTestSampleType() {
		return testSampleType;
	}

	public void setTestSampleType(Integer testSampleType) {
		this.testSampleType = testSampleType;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public void setCommand(String command) {
		this.command = command;
	}
	
}
