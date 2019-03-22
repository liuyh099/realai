package cn.realai.online.lic;

import cn.realai.online.util.DateUtil;

public abstract class LicenseConstants {

    public static final long CHECK_INTERVAL = 30 * 1000;


    /**
     * license文件名
     */
    public final static String LICENSE_FILE_NAME = "*.lic";


    public final static String DATE_FORMART = DateUtil.ymd;


}
