package cn.realai.online.core.entity;

public class RealTimeDataRecord {

	//服务id
	private Long serviceId;
	
	//银行用户id
	private String personalId;  
	
	//用户姓名
	private String name;
	
	//用户电话号
	private String phone;
	
	//用户身份证号
	private String cardNo;
	
	//得分
	private String score;
	
	//请求信息
	private String requestContent;
	
	//创建时间
	private Long createTime;
	
	//请求用时
	private Long  spendTime;
	
	//请求唯一标识
	private String reqId;
	
	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public String getPersonalId() {
		return personalId;
	}

	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getRequestContent() {
		return requestContent;
	}

	public void setRequestContent(String requestContent) {
		this.requestContent = requestContent;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getSpendTime() {
		return spendTime;
	}

	public void setSpendTime(Long spendTime) {
		this.spendTime = spendTime;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
	
}
