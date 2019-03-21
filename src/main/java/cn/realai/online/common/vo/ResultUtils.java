package cn.realai.online.common.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class ResultUtils {

    public static <T> Result<T> generateResult(ResultCode code, String message, T resultJson) {
        message = message != null ? message : "";
        Result<T> result = new Result<T>(code.getCode(), message, resultJson);
        return result;
    }

    public static <T> String generateResultStr(ResultCode code, String message, T resultJson) {
        return generateResult(code, message, resultJson).toString();
    }

    public static boolean isResultSuccess(String resultStr) {
        if (resultStr == null) {
            return false;
        }
        JSONObject json = JSON.parseObject(resultStr);
        return ResultCode.SUCCESS.getCode() == json.getIntValue("resultCode");
    }

    public static String generateKindEditorResultStr(String err, String message, String url) {
        JSONObject json = new JSONObject();
        json.put("err", err);
        json.put("msg", message);
        json.put("url", url);
        return json.toJSONString();
    }

    public static void main(String[] args) {
        Result<String> result = generateResult(ResultCode.DATA_ERROR, "hehe", "123");
        System.out.println(result);
    }

}
