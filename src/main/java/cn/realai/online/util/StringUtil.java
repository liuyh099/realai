package cn.realai.online.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	/**
	 * 判断一个字符串是否全部由数字字符构成
	 * 是返回true，否则返回false
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {  
		if (str == null || "".equals(str)) {
			return false;
		}
        Pattern pattern = Pattern.compile("[0-9]*");  
        Matcher isNum = pattern.matcher(str);  
        if (!isNum.matches()) {  
            return false;  
        }  
        return true;  
    }
	
}

