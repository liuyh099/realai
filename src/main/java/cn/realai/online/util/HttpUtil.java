package cn.realai.online.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.realai.online.calculation.ReturnStructure;
import cn.realai.online.common.page.PageBO;
import cn.realai.online.tool.http.ChannelAccessConstant;
import cn.realai.online.tool.http.XHttpTools;
import cn.realai.online.tool.http.XHttpTools.XBody;
import cn.realai.online.tool.http.XHttpTools.XUrl;

/**
 * http请求工具
 * @author lyh
 */
public class HttpUtil {

	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	
	/**
	 * post方式的http请求，返回T对象
	 * @param url
	 * @param map 参数集合 multipart/form-data 格式
	 * @param T
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> ReturnStructure<T> postRequestObj(String urlStr, Map<String, String> map, Class T) {
		logger.info("HttpUtil getObject urlStr ==>  " + urlStr);
		XUrl url = XUrl.base(urlStr);
		XBody body = XBody.type(XBody.MULTIPART);
		for ( Entry e : map.entrySet()) {
			body.param(e.getKey().toString(), e.getValue().toString());
		}
		String respStr = XHttpTools.request(new XHttpTools.XOption(ChannelAccessConstant.CHARACTER_ENCODING_UTF8, ChannelAccessConstant.WECHAT_CONNECTION_TIMEOUT, ChannelAccessConstant.WECHAT_READ_TIMEOUT), url ,body).string();
		logger.debug("HttpUtil getObject respStr ==>  " + respStr);
    	ReturnStructure<T> rs = JSON.parseObject(respStr, ReturnStructure.class);
    	if (rs.getCode() == 200 && rs.getData() != null && !"".equals(rs.getData())) {
    		rs.setDataT((T)JSON.parseObject(rs.getData(), T));
    		return rs;
    	}
    	return rs;
	}
	
	/**
	 * post方式的http请求，返回List<T>
	 * @param url
	 * @param map 参数集合 multipart/form-data 格式
	 * @param T
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> ReturnStructure<T> postRequestList(String urlStr, Map<String, String> map, Class T) {
		logger.info("HttpUtil getObject urlStr ==>  " + urlStr);
		XUrl url = XUrl.base(urlStr);
		XBody body = XBody.type(XBody.MULTIPART);
		for ( Entry e : map.entrySet()) {
			body.param(e.getKey().toString(), e.getValue().toString());
		}
		String respStr = XHttpTools.request(new XHttpTools.XOption(ChannelAccessConstant.CHARACTER_ENCODING_UTF8, ChannelAccessConstant.WECHAT_CONNECTION_TIMEOUT, ChannelAccessConstant.WECHAT_READ_TIMEOUT), url ,body).string();
		logger.debug("HttpUtil getObject respStr ==>  " + respStr);
    	ReturnStructure<T> rs = JSON.parseObject(respStr, ReturnStructure.class);
    	if (rs.getCode() == 200 && rs.getData() != null && !"".equals(rs.getData())) {
    		rs.setDataTList(JSON.parseArray(rs.getData(), T));
    		return rs;
    	}
    	return rs;
	}
	
	/**
	 * post方式的http请求，返回T
	 * @param url
	 * @param map 参数集合 application/json 格式
	 * @param T
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T postRequest(String urlStr, String param, Class T) {
		logger.info("HttpUtil getObject urlStr ==>  " + urlStr);
		XUrl url = XUrl.base("http://" + urlStr);
		XBody body = XBody.type(XBody.JSON);
		body.param(param);
    	String respStr = XHttpTools.request(new XHttpTools.XOption(ChannelAccessConstant.CHARACTER_ENCODING_UTF8, ChannelAccessConstant.WECHAT_CONNECTION_TIMEOUT, ChannelAccessConstant.WECHAT_READ_TIMEOUT), url, body).string();
		logger.debug("HttpUtil getObject respStr ==>  " + respStr);
    	ReturnStructure rs = JSON.parseObject(respStr, ReturnStructure.class);
    	if (rs.getCode() == 200 && rs.getData() != null && !"".equals(rs.getData())) {
    		return (T)JSON.parseObject(rs.getData(), T);
    	}
    	return null;
	}
	
	/**
	 * get方式的http请求，返回T
	 * @param url
	 * @param T
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T getRequest(String urlStr, Class T) {
		logger.info("HttpUtil getObject urlStr ==>  " + urlStr);
		XUrl url = XUrl.base(urlStr);
    	String respStr = XHttpTools.request(new XHttpTools.XOption(ChannelAccessConstant.CHARACTER_ENCODING_UTF8, ChannelAccessConstant.WECHAT_CONNECTION_TIMEOUT, ChannelAccessConstant.WECHAT_READ_TIMEOUT),url,null).string();
		logger.debug("HttpUtil getObject respStr ==>  " + respStr);
    	ReturnStructure rs = JSON.parseObject(respStr, ReturnStructure.class);
    	if (rs.getCode() == 200 && rs.getData() != null && !"".equals(rs.getData())) {
    		return (T)JSON.parseObject(rs.getData(), T);
    	}
    	return null;
	}
	
	/**
	 * get方式的http请求，返回T  这个接口只为请求新闻和专家论点的详情调用
	 * @param url
	 * @param T  只能为ViewBDO
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T getViewBDO(String urlStr, Class T) {
		logger.info("HttpUtil getViewBDO urlStr ==>  " + urlStr);
		XUrl url = XUrl.base(urlStr);
    	String respStr = XHttpTools.request(new XHttpTools.XOption(ChannelAccessConstant.CHARACTER_ENCODING_UTF8, ChannelAccessConstant.WECHAT_CONNECTION_TIMEOUT, ChannelAccessConstant.WECHAT_READ_TIMEOUT),url,null).string();
    	logger.debug("HttpUtil getViewBDO respStr ==>  " + respStr.getBytes());

    	ReturnStructure rs = JSON.parseObject(respStr, ReturnStructure.class);
    	if (rs.getCode() == 200 && rs.getData() != null && !"".equals(rs.getData())) {
    		return (T)JSON.parseObject(rs.getData(), T);
    	}
    	return null;
	}
	
	public static void main(String[] args) {
		//String respStr = "{'id':45781,'date':'2018-08-1208:04:00.0','title':'美国威胁对土耳其加征关税土总统:美国威胁行不通','url':'http://money.163.com/18/0812/08/DP0BHC5E00258105.html','source':'央视新闻','author':null,'content':'</p><p>针对美国10号宣布提高土耳其钢铁和铝产品进口关税一事，11号，土耳其总统埃尔多安回应称，美国威胁土耳其是一种错误的做法。土耳其总统埃尔多安：美国永远无法使用威胁语言来迫使土耳其就范，我们只懂得法律和权利这种语言，不懂得威胁。</p><p></p><p></p><p>埃尔多安还呼吁土耳其人支持土耳其货币里拉，他说，“这是一场事关独立的战争。”10号，美方宣布将土耳其钢铁和铝产品进口关税提高一倍。土耳其贸易部10号发表声明称，美国翻倍征收钢铝材关税“违反世界贸易组织规则</p><p></p><p>”，土耳其将通过国际平台保护国内钢铝出口商的利益。土耳其外交部随后也发表声明说，“制裁和施压只会损害两个北约盟国的关系”，土耳其将对美国进行报复。</p><p></p><p></p><p>本文来源：央视新闻</p><p>责任编辑：杨泽宇_NF6036</p><p></p><p>','del_status':'2','img01':'http://cms-bucket.nosdn.127.net/catchpic/5/5a/5a04983f1c3f8f84d19e582f0a9bd898.png?imageView&thumbnail=550x0','img02':null,'img03':null,'img04':null,'news_type':'热点新闻','auditing_time':'2018-08-1311:02:14.0'}";
		//Gson gson = new Gson();
		/*Gson gson = new Gson();
    	ReturnStructure rs = gson.fromJson(respStr, ReturnStructure.class);*/
		//ViewBDO rs = JSON.parseObject(respStr, ViewBDO.class);
		//org.json.JSONObject json = new org.json.JSONObject(respStr);
		//System.out.println(rs);
		//System.out.println(json.toString());
	}
	
	/**
	 * get方式http请求，返回List<T>
	 * @param url
	 * @param T
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> PageBO<T> getList(String urlStr, Class T) {
		logger.debug("HttpUtil getList urlStr ==>  " + urlStr);
		XUrl url = XUrl.base(urlStr);
    	String respStr = XHttpTools.request(new XHttpTools.XOption(ChannelAccessConstant.CHARACTER_ENCODING_UTF8, ChannelAccessConstant.WECHAT_CONNECTION_TIMEOUT, ChannelAccessConstant.WECHAT_READ_TIMEOUT),url,null).string();
    	logger.debug("HttpUtil getList respStr ==>  " + respStr);
    	ReturnStructure rs = JSON.parseObject(respStr, ReturnStructure.class);
    	PageBO<T> page = new PageBO<T>();
    	List<T> list = new ArrayList<T>();
    	page.setPageContent(list);
    	if (rs == null) {
    		return page;
    	}
    	if (rs.getCode() == 200 && rs.getData() != null && !"".equals(rs.getData())) {
    		page.setPageContent(JSON.parseArray(rs.getData(), T));
    	}
    	return page;
	}
	
}
