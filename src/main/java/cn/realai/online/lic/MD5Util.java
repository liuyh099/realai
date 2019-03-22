package cn.realai.online.lic;

import java.security.MessageDigest;

/**
 * Description: MD5加密工具
 * <br>
 * <br>Author: Shunping.Fu
 * <br>Date: 2017/9/4
 */
public class MD5Util {

    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    private static final String encryptionAlgorithm = "SHA-1";

    public static String bytesToHexString(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] hexStringToBytes(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
                    .digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    /**
     * MD5加密
     * @param password
     * @return
     */
    public static String MD5Encrypt(String password) {
        MessageDigest md = null;
        String strDes = "";
        try {
            byte[] bt = password.getBytes("UTF-8");
            md = MessageDigest.getInstance("MD5");
            md.update(bt);

            strDes = bytesToHexString(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDes;
    }

    public static String MD5Encrypt(String password, String mobile) {
        return MD5Encrypt(password+mobile);
    }

}
