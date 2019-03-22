package cn.realai.online.calculation.requestbo;

import java.util.List;

public class TrainRequestBO {

	//实验id
	private long experimentId;
	
	//如果是而此实验，这个字段为原实验id
	private Long oldExperimentId;   
    
	//命令
	private final String command = "train";
	
	private Integer validSampleType = 1;
	
	private Integer testSampleType = 1;
	
	//训练比例
    private Integer trainRatio;
    
    //测试比例
    private Integer testRatio;
    
    //验证比例
    private Integer validRatio;
    
    //异质需要训练列
    private List<String> needColumnsHetero;
    
    //同质需要训练列
    private List<String> needColumnsHomo;
    
    //异质删除列
    private List<String> deleteHetero;
    
    //同质删除列
    private List<String> deleteHomo;
    
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

	public List<String> getNeedColumnsHetero() {
		return needColumnsHetero;
	}
	
	public void setNeedColumnsHetero(List<String> needColumnsHetero) {
		this.needColumnsHetero = needColumnsHetero;
	}
	
	public List<String> getNeedColumnsHomo() {
		return needColumnsHomo;
	}
	
	public void setNeedColumnsHomo(List<String> needColumnsHomo) {
		this.needColumnsHomo = needColumnsHomo;
	}
	
	public List<String> getDeleteHetero() {
		return deleteHetero;
	}

	public void setDeleteHetero(List<String> deleteHetero) {
		this.deleteHetero = deleteHetero;
	}

	public List<String> getDeleteHomo() {
		return deleteHomo;
	}

	public void setDeleteHomo(List<String> deleteHomo) {
		this.deleteHomo = deleteHomo;
	}

	public Long getOldExperimentId() {
		return oldExperimentId;
	}

	public void setOldExperimentId(Long oldExperimentId) {
		this.oldExperimentId = oldExperimentId;
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
	
}
