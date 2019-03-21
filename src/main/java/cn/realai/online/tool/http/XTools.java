package cn.realai.online.tool.http;

import java.io.*;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import cn.realai.online.tool.http.XHttpTools.XBody;
import cn.realai.online.tool.http.XHttpTools.XResp;
import cn.realai.online.tool.http.XHttpTools.XUrl;


/**
 * 常用的基本的函数的集合和索引
 */
public final class XTools {

    private XTools() {
    }

    /**
     * 字符串MD5散列
     *
     * @param str 被散列的字符串
     * @return 散列结果，全小写字母
     */
    public static String md5(String str) {
        try {
            return XCodeTools.hash(XCodeTools.HASH_MD5, str.getBytes("UTF-8"));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 文件MD5散列
     *
     * @param file 被散列的文件
     * @return 散列结果，全小写字母
     */
    public static String md5(File file) {
        try {
            return XCodeTools.hash(XCodeTools.HASH_MD5, file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 字符串SHA1散列
     *
     * @param str 被散列的字符串
     * @return 散列结果，全小写字母
     */
    public static String sha1(String str) {
        try {
            return XCodeTools.hash(XCodeTools.HASH_SHA1, str.getBytes("UTF-8"));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 文件SHA1散列
     *
     * @param file 被散列的文件
     * @return 散列结果，全小写字母
     */
    public static String sha1(File file) {
        try {
            return XCodeTools.hash(XCodeTools.HASH_SHA1, file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用默认的请求选项进行HTTP请求，
     * 如需对HTTP请求进行更复杂的配置，请移步XHttpTools.request(XOption option, XUrl url, XBody body);方法
     *
     * @param url 请求的url，HTTP方法：GET
     * @return 请求的响应体
     */
    public static XResp http(XUrl url) {
        return XHttpTools.request(XHttpTools.DEFAULT_OPTION, url, null);
    }

    /**
     * 使用默认的请求选项进行HTTP请求，
     * 如需对HTTP请求进行更复杂的配置，请移步XHttpTools.request(XOption option, XUrl url, XBody body);方法
     *
     * @param url  请求的url，HTTP方法：body == null ? "GET" : "POST"
     * @param body 请求的请求体
     * @return 请求的响应体
     */
    public static XResp http(XUrl url, XBody body) {
        return XHttpTools.request(XHttpTools.DEFAULT_OPTION, url, body);
    }

    /**
     * 判断字符串是否为空
     *
     * @param str 要判断的字符串
     * @return str == null || str.length() == 0
     */
    public static boolean strEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 判断字符串是否没有可见字符
     *
     * @param str 要判断的字符串
     * @return str == null || str.trim().length() == 0
     */
    public static boolean strBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 将几个字符串用一个固定的字符串连接起来
     *
     * @param strSet 字符串集合
     * @param glue   用于连接的字符串
     * @return 连接后的字符串
     */
    public static String strJoin(Collection<String> strSet, String glue) {
        StringBuilder sbStr = new StringBuilder();
        for (String str : strSet) {
            if (sbStr.length() > 0) {
                sbStr.append(glue);
            }
            sbStr.append(str);
        }
        return sbStr.toString();
    }

    /**
     * 将键值对集合用固定的字符串连接起来
     *
     * @param strMap    键值对集合
     * @param glueInner 连接键和值的字符串
     * @param glueOuter 连接键值对之间的字符串
     * @return 拼接后的字符串
     */
    public static String strJoin(Map<?, ?> strMap, String glueInner, String glueOuter) {
        StringBuilder sbStr = new StringBuilder();
        for (Object key : strMap.keySet()) {
            if (sbStr.length() > 0) {
                sbStr.append(glueOuter);
            }
            sbStr.append(String.valueOf(key)).append(glueInner).append(String.valueOf(strMap.get(key)));
        }
        return sbStr.toString();
    }

    /**
     * 将字符串保存成文件
     *
     * @param str     要保存成文件的字符串
     * @param path    保存文件的位置
     * @param charset 字符串的编码格式
     * @return 保存后的文件
     * @throws IOException 在保存时可能会发生IO异常
     */
    public static File strToFile(String str, String path, String charset) throws IOException {
        File file = new File(path);
        try (FileOutputStream fOutStream = new FileOutputStream(file)) {
            fOutStream.write(str.getBytes(charset));
            fOutStream.flush();
            return file;
        }
    }

    /**
     * 将文件读取成字符串
     *
     * @param file    要读取成字符串的文件
     * @param charset 字符串的编码格式
     * @return 读取出的字符串
     * @throws IOException 在读取时可能会发生IO异常
     */
    public static String fileToStr(File file, String charset) throws IOException {
        try (FileInputStream fInStream = new FileInputStream(file)) {
            return streamToStr(fInStream, charset);
        }
    }

    /**
     * 将文件复制到另外一个文件
     *
     * @param file 源文件
     * @param path 目标文件的地址
     * @return 目标文件
     * @throws IOException 在复制时可能会发生IO异常
     */
    public static File fileToFile(File file, String path) throws IOException {
        File fileTo = new File(path);
        try (FileInputStream fInStream = new FileInputStream(file); FileOutputStream fOutStream = new FileOutputStream(fileTo)) {
            streamToStream(fInStream, fOutStream);
            return fileTo;
        }
    }

    /**
     * 将输入流中的全部数据读取成字符串
     *
     * @param inStream 要读取的输入流，不会关闭该输入流
     * @param charset  字符串的编码格式
     * @return 读取出的字符串
     * @throws IOException 在读取时可能会发生IO异常
     */
    public static String streamToStr(InputStream inStream, String charset) throws IOException {
        int count;
        char[] buffer = new char[1024];
        StringBuilder sbStr = new StringBuilder();
        BufferedReader bufReader = new BufferedReader(new InputStreamReader(inStream, charset));
        while ((count = bufReader.read(buffer)) > 0) {
            sbStr.append(buffer, 0, count);
        }
        return sbStr.toString();
    }

    /**
     * 将输入流中的全部数据读取成文件
     *
     * @param inStream 要读取的输入流，不会关闭该输入流
     * @param path     要保存的文件的位置
     * @return 保存后的文件
     * @throws IOException 在读取时可能会发生IO异常
     */
    public static File streamToFile(InputStream inStream, String path) throws IOException {
        int count;
        byte[] buffer = new byte[1024];
        File file = new File(path);
        BufferedInputStream bufInStream = new BufferedInputStream(inStream);
        try (FileOutputStream fOutStream = new FileOutputStream(file)) {
            while ((count = bufInStream.read(buffer)) > 0) {
                fOutStream.write(buffer, 0, count);
            }
            fOutStream.flush();
        }
        return file;
    }

    /**
     * 将输入流中的全部数据读取到输出流
     *
     * @param inStream  要读取的输入流，不会关闭该输入流
     * @param outStream 要写入的输出流，不会关闭该输出流
     * @throws IOException 输入输出时可能会发生IO异常
     */
    public static void streamToStream(InputStream inStream, OutputStream outStream) throws IOException {
        int count;
        byte[] buffer = new byte[1024];
        BufferedInputStream bufInStream = new BufferedInputStream(inStream);
        BufferedOutputStream bufOutStream = new BufferedOutputStream(outStream);
        while ((count = bufInStream.read(buffer)) > 0) {
            bufOutStream.write(buffer, 0, count);
        }
        bufOutStream.flush();
    }

    /**
     * 获取任意时间当天00:00:00时刻的date对象
     *
     * @param date 任意时间的date对象
     * @return 任意时间当天00:00:00时刻的date对象
     */
    public static Date date(Date date) {
        return XTimeTools.date(date);
    }

    /**
     * 获取任意一天的类型
     *
     * @param date 任意一天的date对象
     * @return 任意一天的类型。1：工作日(XTimeTools.WORKDAY)，2：公休日(XTimeTools.RESTDAY)，3：节假日(XTimeTools.HOLIDAY)
     */
    public static int dateType(Date date) {
        return XTimeTools.dateType(date);
    }

    /**
     * 获取以某天所在的那一周为基准偏移若干周的某天00:00:00时刻的date对象
     *
     * @param base       基准时间的date对象，如果为null则以当前时间为基准
     * @param weekOffset 偏移的周数
     * @param dayIndex   那一周的第几天（每周的第一天是周一，周一为0）
     * @return 以base所在的那一周为基准偏移weekOffset周的dayIndex天00:00:00时刻的date对象
     */
    public static Date dateByWeek(Date base, int weekOffset, int dayIndex) {
        return XTimeTools.dateByWeek(base, weekOffset, dayIndex);
    }

    /**
     * 获取以某天所在的那一月为基准偏移若干月的某天00:00:00时刻的date对象
     *
     * @param base        基准时间的date对象，如果为null则以当前时间为基准
     * @param monthOffset 偏移的月数
     * @param dayIndex    那一月的第几天（一号为0）
     * @return 以base所在的那一月为基准偏移monthOffset月的dayIndex天00:00:00时刻的date对象
     */
    public static Date dateByMonth(Date base, int monthOffset, int dayIndex) {
        return XTimeTools.dateByMonth(base, monthOffset, dayIndex);
    }

    /**
     * 获取以某天所在的那一年为基准偏移若干年的某月的某天00:00:00时刻的date对象
     *
     * @param base       基准时间的date对象，如果为null则以当前时间为基准
     * @param yearOffset 偏移的年数
     * @param monthIndex 那一年的第几个月（一月为0）
     * @param dayIndex   那一月的第几天（一号为0）
     * @return 以base所在的那一年为基准偏移yearOffset年的monthIndex月的dayIndex天00:00:00时刻的date对象
     */
    public static Date dateByYear(Date base, int yearOffset, int monthIndex, int dayIndex) {
        return XTimeTools.dateByYear(base, yearOffset, monthIndex, dayIndex);
    }

    /**
     * 获取以某天所在的那一月为基准偏移若干月的某周的周一00:00:00时刻的date对象
     *
     * @param base        基准时间的date对象，如果为null则以当前时间为基准
     * @param monthOffset 偏移的月数
     * @param weekIndex   那一月的第几周（每周的第一天是周一，每月的第一周是第一个周一所在的那一周，第一周为0）
     * @return 以base所在的那一月为基准偏移monthOffset月的weekIndex周的周一00:00:00时刻的date对象
     */
    public static Date weekByMonth(Date base, int monthOffset, int weekIndex) {
        return XTimeTools.weekByMonth(base, monthOffset, weekIndex);
    }

    /**
     * 获取以某天所在的那一年为基准偏移若干年的某月的某周的周一00:00:00时刻的date对象
     *
     * @param base       基准时间的date对象，如果为null则以当前时间为基准
     * @param yearOffset 偏移的年数
     * @param monthIndex 那一年的第几个月（一月为0）
     * @param weekIndex  那一月的第几周（每周的第一天是周一，每月的第一周是第一个周一所在的那一周，第一周为0）
     * @return 以base所在的那一年为基准偏移yearOffset年的monthIndex月的weekIndex周的周一00:00:00时刻的date对象
     */
    public static Date weekByYear(Date base, int yearOffset, int monthIndex, int weekIndex) {
        return XTimeTools.weekByYear(base, yearOffset, monthIndex, weekIndex);
    }

    /**
     * 获取任意一天是一周中的第几天
     *
     * @param base 基准时间的date对象，如果为null则以当前时间为基准
     * @return base那天是那周中的第几天（每周的第一天是周一，周一为0）
     */
    public static int dateInWeek(Date base) {
        return XTimeTools.dateInWeek(base);
    }

    /**
     * 获取任意一天是一个月中的第几天
     *
     * @param base 基准时间的date对象，如果为null则以当前时间为基准
     * @return base那天是那个月中的第几天（一号为0）
     */
    public static int dateInMonth(Date base) {
        return XTimeTools.dateInMonth(base);
    }

    /**
     * 获取任意一天是一年中的第几天
     *
     * @param base 基准时间的date对象，如果为null则以当前时间为基准
     * @return base那天是那一年中的第几天（第一天为0）
     */
    public static int dateInYear(Date base) {
        return XTimeTools.dateInYear(base);
    }

    /**
     * 获取任意一天所在的周是哪个月的第几周
     *
     * @param base 基准时间的date对象，如果为null则以当前时间为基准
     * @return base那天所在的周是哪个月的第几周（正数表示本月第几周，负数表示上月第几周,例：1=本月第二周，-3=上月第四周）
     */
    public static int weekInMonth(Date base) {
        return XTimeTools.weekInMonth(base);
    }

    /**
     * 获取任意一天所在的周是哪一年的第几周
     *
     * @param base 基准时间的date对象，如果为null则以当前时间为基准
     * @return base那天所在的周是哪一年的第几周（正数表示今年第几周，负数表示去年第几周,例：1=今年第二周，-51=去年第52周）
     */
    public static int weekInYear(Date base) {
        return XTimeTools.weekInYear(base);
    }

    /**
     * 将阳历时间转化成农历时间
     *
     * @param solarDate 阳历时间的Date对象
     * @return 对应的农历时间的字符串，例1：1992年八月初六。例2：2033年闰冬月廿八
     */
    public static String solarToLunar(Date solarDate) {
        return XTimeTools.solarToLunar(solarDate);
    }

    /**
     * 将农历时间转换成阳历时间
     *
     * @param lunarDate 农历时间字符串。例1：1992年八月初六。例2：2033年闰冬月廿八
     * @return 阳历时间的Date对象
     */
    public static Date lunarToSolar(String lunarDate) {
        return XTimeTools.lunarToSolar(lunarDate);
    }

    /**
     * 判断是否是windows系统
     *
     * @return 是否是windows系统
     */
    public static boolean sysWindows() {
        return System.getProperties().getProperty("os.name").toLowerCase().contains("windows");
    }

    /**
     * 判断是否是MacOS系统
     *
     * @return 是否是MacOS系统
     */
    public static boolean sysMacOS() {
        String osName = System.getProperties().getProperty("os.name").toLowerCase();
        return osName.contains("mac") && !osName.contains("x");
    }

    /**
     * 判断是否是MacOSX系统
     *
     * @return 是否是MacOSX系统
     */
    public static boolean sysMacOSX() {
        String osName = System.getProperties().getProperty("os.name").toLowerCase();
        return osName.contains("mac") && osName.contains("x");
    }

    /**
     * 判断是否是Linux系统
     *
     * @return 是否是Linux系统
     */
    public static boolean sysLinux() {
        String osName = System.getProperties().getProperty("os.name").toLowerCase();
        return osName.contains("linux");
    }
}
