package cn.realai.online.common.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class Result<T> {
	
	private int code;
	
	private String msg;
	
	private T data;
	
	public Result() {}

	public Result(int code, String message, T resultJson) {
		this.code = code;
		this.msg = message;
		this.data = resultJson;
	}

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

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String toString() {
		return JSON.toJSONString(this, new SerializerFeature[] { SerializerFeature.WriteDateUseDateFormat });
	}
	
}
