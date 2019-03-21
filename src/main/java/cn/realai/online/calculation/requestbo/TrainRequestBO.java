package cn.realai.online.calculation.requestbo;

import java.util.List;

public class TrainRequestBO {

	//实验id
	private long experimentId;
	
	//如果是而此实验，这个字段为原实验id
	private Long oldExperimentId;
    
	//命令
	private String command;
    
	//样本切分类型
	private int sampleSplitType;
	
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
	
	public void setCommand(String command) {
		this.command = command;
	}
	
	public int getSampleSplitType() {
		return sampleSplitType;
	}

	public void setSampleSplitType(int sampleSplitType) {
		this.sampleSplitType = sampleSplitType;
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

	public Long getOldExperimentId() {
		return oldExperimentId;
	}

	public void setOldExperimentId(Long oldExperimentId) {
		this.oldExperimentId = oldExperimentId;
	}
	
}
