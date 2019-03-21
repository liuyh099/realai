package cn.realai.online.lic;

public class LicenseException extends Exception {
    /**	
     * Member Description
     */
    private static final long serialVersionUID = -5692657775328208846L;
    /**
     * 无参构造函数
     */
    public LicenseException() {
        super();
    }
    /**
     * 构造函数
     * @param message 信息
     */
    public LicenseException(String message) {
        super(message);
    }
/**
 * 构造函数
 * @param t 异常
 */
    public LicenseException(Throwable t) {
        super(t);
    }
/**
 * 构造函数
 * @param message 信息
 * @param t 异常
 */
    public LicenseException(String message, Throwable t) {
        super(message, t);
    }
}
