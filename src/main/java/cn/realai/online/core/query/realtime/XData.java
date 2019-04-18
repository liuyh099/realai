package cn.realai.online.core.query.realtime;

import com.alibaba.fastjson.JSONObject;

/**
 * 数据载体
 * @author lyh
 */
public class XData {

	//异质数据源
	private JSONObject hetro;
	
	//同质数据源
	private JSONObject homo;

	public JSONObject getHetro() {
		return hetro;
	}

	public void setHetro(JSONObject hetro) {
		this.hetro = hetro;
	}

	public JSONObject getHomo() {
		return homo;
	}

	public void setHomo(JSONObject homo) {
		this.homo = homo;
	}

}
