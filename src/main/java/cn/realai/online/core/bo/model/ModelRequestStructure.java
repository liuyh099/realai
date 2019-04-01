package cn.realai.online.core.bo.model;

/**
 * python 回调时发送的json数据封装类
 *
 * @author 86183
 */
public class ModelRequestStructure {

    private Long experimentId;

    private String task;

    private Integer code;

    private String msg;

    private String data;
    
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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;

    }

}
