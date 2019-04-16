package cn.realai.online.core.query.realtime;

/**
 * 线上实时接口请求数据封装类
 * @author lyh
 */
public class RealTimeData {

	//服务id
	private Long serviceId;
	
	//实验id,请求方不用送,服务器自动复制
	private Long modelId;
	
	//银行用户id
	private String personalId;
	
	//用户姓名
	private String name;
	
	//用户电话号
	private String phone;
	
	//用户身份证号
	private String cardNo;
	
	//同质和异质数据
	private XData data;

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

	public XData getData() {
		return data;
	}

	public void setData(XData data) {
		this.data = data;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

}
