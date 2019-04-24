package cn.realai.online.core.bo.model;

/**
 * python 回调时发送的json数据封装类
 *
 * @author 86183
 */
public class ModelRequest extends BatchRequest {

    private Long experimentId;

    private String task;

    private String data;
    
    private Integer stage;
    
    
    public Integer getStage() {
		return stage;
	}

	public void setStage(Integer stage) {
		this.stage = stage;
	}

	public Long getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(Long experimentId) {
        this.experimentId = experimentId;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;

    }

}
