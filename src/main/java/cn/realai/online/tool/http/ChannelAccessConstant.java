package cn.realai.online.tool.http;

/**
 * http请求参数封装
 *
 * @author Administrator
 */
public class ChannelAccessConstant {

    public static final String CHARACTER_ENCODING_GBK = "GBK";
    public static final String CHARACTER_ENCODING_UTF8 = "UTF-8";

    public static final int CONNECTION_TIMEOUT = 3000;
    public static final int READ_TIMEOUT = 3000;

    public static final int WECHAT_CONNECTION_TIMEOUT = 10000;
    public static final int WECHAT_READ_TIMEOUT = 30000;

    public static final int IMAGE_CONNECTION_TIMEOUT = 30000;
    public static final int IMAGE_READ_TIMEOUT = 60000;

    public static final String REQUEST_POST = "POST";
    public static final String REQUEST_GET = "GET";

    public static final String FORMAT_JSON = "json";
    public static final String FORMAT_XML = "xml";

    public static final String REQUEST_CONTENT_TYPE = "application/x-www-form-urlencoded";

    public static final int SINGLE_CHANNEL_RETRY_TIMES = 3;
    public static final int RESTRY_SLEEP_TIME = 500;

    public static final int HTTP_RESP_SUCCESS_CODE = 200;

}
