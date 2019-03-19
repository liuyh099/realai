package cn.realai.online.util;

import org.apache.tomcat.util.buf.HexUtils;

public class EncodingPasswordUtils {
    /**
     * 密码加密实现
     * @param password
     * @return
     */
    public static String encodingPassword(String password) {
        password=password.replace("&apos;", "'").replace("&amp;", "&").replace("&quot;", "\"").replace("&nbsp;&nbsp;", "\t")
                .replace("&nbsp;", " ").replace("&lt;", "<").replace("&gt;",  ">");
        byte[] salt = Digests.generateSalt(8);
        byte[] hashPassword = Digests.sha1(password.getBytes(), salt, 1024);
        String saltString= HexUtils.toHexString(salt);
        String hashPasswordString= HexUtils.toHexString(hashPassword);
        password=saltString+hashPasswordString;
        return password;
    }

    public static String encodingPassword(String password, byte[] salt) {
        password=password.replace("&apos;", "'").replace("&amp;", "&").replace("&quot;", "\"").replace("&nbsp;&nbsp;", "\t")
                .replace("&nbsp;", " ").replace("&lt;", "<").replace("&gt;",  ">");
        byte[] hashPassword = Digests.sha1(password.getBytes(), salt, 1024);
        String saltString= HexUtils.toHexString(salt);
        String hashPasswordString= HexUtils.toHexString(hashPassword);
        password=saltString+hashPasswordString;
        return password;
    }
}
