package cn.realai.online.core.bo.model;

public class BatchRequestBase extends BatchRequest{

	
	private boolean done;
	
	private Integer count;
	
	private String type;
	    
	public static final String TYPE_PERSONALCOMBORESULTSET = "personalComboResultSet";
	public static final String TYPE_PERSONALHETRORESULTSET = "personalHetroResultSet";
	public static final String TYPE_PERSONALHOMORESULTSET = "personalHomoResultSet";
	public static final String TYPE_PERSONALINFORMATION = "personalInformation";
	public static final String TYPE_THOUSANDPEOPLE_INFORMATION = "thousandPeopleInformation";
	public static final String TYPE_PSICHECKRESULT = "psiCheckResult";
	public static final String TYPE_DOENURL = "downUrl";
	public static final String TYPE_DONE = "done";
	
	public boolean getDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
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
