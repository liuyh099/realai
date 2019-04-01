package cn.realai.online.core.bo.model;

public class BatchRequestBase {

	private Integer code;
	
	private String msg;
	
	private Long modelId;
	
	private boolean done;
	
	private Integer count;
	
	private String type;
	    
	public static final String TYPE_PERSONALCOMBORESULTSET = "personalComboResultSet";
	public static final String TYPE_PERSONALHETRORESULTSET = "personalHetroResultSet";
	public static final String TYPE_PERSONALHOMORESULTSET = "personalHomoResultSet";
	public static final String TYPE_PERSONALINFORMATION = "personalInformation";
	public static final String TYPE_PSICHECKRESULT = "psiCheckResult";
	public static final String TYPE_DOENURL = "downUrl";
	public static final String TYPE_DONE = "done";
	
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

	public boolean getDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
