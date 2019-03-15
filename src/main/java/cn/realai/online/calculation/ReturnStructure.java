package cn.realai.online.calculation;

import java.util.List;

/**
 * 大数据接口返回数据格式
 * @author lyh
 */
public class ReturnStructure<T> {
	
	//返回码
	private int code;
	
	//返回信息
	private String msg;
	
	//返回数据（JSON格式）
	private String data;
	
	private T dataT;
	
	private List<T> dataTList;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
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

	public T getDataT() {
		return dataT;
	}

	public void setDataT(T dataT) {
		this.dataT = dataT;
	}

	public List<T> getDataTList() {
		return dataTList;
	}

	public void setDataTList(List<T> dataTList) {
		this.dataTList = dataTList;
	}

}
