package cn.realai.online.lic;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * 非对称加密算法RSA
 */
public class RSAUtils {

    /**
     * 指定加密算法为RSA
     */
    private static final String ALGORITHM = "RSA";
    /**
     * 加密填充方式
     */
    public static final String ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";//加密填充方式
    /**
     * 密钥长度，用来初始化
     */
    private static final int KEYSIZE = 1024;
    /**
     * 公钥
     */
    private static Key publicKey = null;
    /**
     * 私钥
     */
    private static Key privateKey = null;
    /**
     * 公钥字符串
     */
    private static String publicKeyString = null;
    /**
     * 私钥字符串
     */
    private static String privateKeyString = null;
    /**
     * 指定字符集
     */
    private static String CHARSET = "UTF-8";
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    private RSAUtils(){

    }

    /**
     * 生成密钥对
     *
     * @throws Exception
     */
    public static Map<String, String> generateKeyPair() throws Exception {

//        // /** RSA算法要求有一个可信任的随机数源 */
        SecureRandom secureRandom = new SecureRandom();
        /** 为RSA算法创建一个KeyPairGenerator对象 */
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        /** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
        keyPairGenerator.initialize(KEYSIZE, secureRandom);
//        /** 生成密匙对 */
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        Map<String, String> keyMap = new HashMap<String, String>();
//        /** 得到公钥 */
        BASE64Encoder encoder = new BASE64Encoder();

        keyMap.put("public", new String(encoder.encode(keyPair.getPublic().getEncoded()).getBytes(), "UTF-8"));
        keyMap.put("private", new String(encoder.encode(keyPair.getPrivate().getEncoded()).getBytes(), "UTF-8"));
        return keyMap;
    }

    /**
     * 生成公钥对象
     *
     * @param publicKeyStr
     * @throws Exception
     */
    public static void setPublicKey(String publicKeyStr) throws Exception {
        RSAUtils.publicKey = generatePublicKey(publicKeyStr);
    }

    /**
     * 生成私钥对象
     *
     * @param privateKeyStr
     * @throws Exception
     */
    public static void setPrivateKey(String privateKeyStr) throws Exception {
        RSAUtils.privateKey = generatePrivateKey(privateKeyStr);
    }

    /**
     * 私钥加密方法
     *
     * @param privatekey
     * @return
     * @throws Exception
     */
    public static String encryptByPrivateKey(String source, String privatekey) throws Exception {
        generatePrivateKey(privatekey);
        /** 得到Cipher对象来实现对源数据的RSA加密 */
        Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] data = source.getBytes();
        /** 执行数据分组加密操作 */
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(encryptedData);
    }

    /**
     * 使用公钥解密算法
     *
     * @param cryptoSrc 密文
     * @return
     * @throws Exception
     */
    public static String decryptByPublicKey(String cryptoSrc, String publicStr) throws Exception {
        setPublicKey(publicStr);
        /** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
        Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        BASE64Decoder decoder = new BASE64Decoder();
        byte[] encryptedData = decoder.decodeBuffer(cryptoSrc);
        /** 执行解密操作 */

        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();

        return new String(decryptedData);
    }


    /**
     * 公钥加密方法
     *
     * @param pubString
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String source, String pubString) throws Exception {
        setPublicKey(pubString);
        /** 得到Cipher对象来实现对源数据的RSA加密 */
        Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] data = source.getBytes();
        /** 执行分组加密操作 */
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();

        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(encryptedData);
    }


    /**
     * 私钥解密算法
     *
     * @param cryptoSrc 密文
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String cryptoSrc, String privatekey) throws Exception {
        //生成私钥对象
        setPrivateKey(privatekey);
        /** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
        Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] encryptedData = decoder.decodeBuffer(cryptoSrc);
//        byte[] encryptedData = Base64Utils.decode(cryptoSrc);
//        System.out.println(cryptoSrc);
//        System.out.println(encryptedData);

        /** 执行解密操作 */
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData);
    }

    /**
     * 将给定的公钥字符串转换为公钥对象
     *
     * @param publicKeyStr
     * @return
     * @throws Exception
     */
    private static Key generatePublicKey(String publicKeyStr) throws Exception {
        publicKeyString = publicKeyStr;
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] buffer = base64Decoder.decodeBuffer(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (IOException e) {
            throw new Exception("公钥数据内容读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

    /**
     * 将给定的私钥字符串转换为私钥对象
     *
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    private static Key generatePrivateKey(String privateKeyStr) throws Exception {
        privateKeyString = privateKeyStr;
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] buffer = base64Decoder.decodeBuffer(privateKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
            return privateKey;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (IOException e) {
            throw new Exception("私钥数据内容读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    public static String getPublicKeyString() {
        return publicKeyString;
    }

    public static String getPrivateKeyString() {
        return privateKeyString;
    }

    public static void generateKeyPair(String publicKeyStr, String privateKeyStr) throws Exception {
        generatePublicKey(publicKeyStr);
        generatePrivateKey(privateKeyStr);
    }

    public static void main(String[] args) throws Exception {

        Map<String, String> keyMap = generateKeyPair();
        System.out.println("public------" + keyMap.get("public"));
        System.out.println("private------" + keyMap.get("private"));
//
//        long a = System.currentTimeMillis();
//        System.out.println("公钥字符串: ");
//        System.out.println(publicKeyString);
//        System.out.println();
//        System.out.println();
//        System.out.println("公钥字符串长度" + publicKeyString.length());
//        System.out.println();
//        System.out.println();
//        System.out.println("私钥字符串: ");
//        System.out.println(privateKeyString);
//        System.out.println();
//        System.out.println();
//        System.out.println("私钥字符串长度" + privateKeyString.length());
//        System.out.println();
//        System.out.println();
//        System.out.println("公钥对象: ");
//        System.out.println(publicKey);
//        System.out.println();
//        System.out.println();
//        System.out.println("私钥对象: ");
//        System.out.println(privateKey);
//        int num = 0;
////		for (int i = 0; i< 100; i++ ) {
//        String origin = "这本应该是iOS中一个标准、内置的解决空table和collection view的方式。默认的如果你的table view是空的，屏幕就是空的。但这不是你能提供的最好的用户体验。这本应该是iOS中一个标准、内置的解决空table和collection view的方式。默认的如果你的table view是空的，屏幕就是空的。但这不是你能提供的最好的用户体验。";
//        String s = new String(new BASE64Encoder().encodeBuffer(origin.getBytes()));
////        String s = new String(Base64Utils.encode(origin.getBytes()));
//
//        String en = encryptByPublicKey(s, publicKeyString);
//        System.out.println("en1-->" + en);
//        en = "PbdK5KWLRWEFMWznP3/RDNqk3sra8PIrFRbnpiYsHilFvVJXz5i5OqgpxyWpwkAmHsEZpED1YDmaK1yGcI5yVqHjGcjdft2ofv5VFbZHT61V1chMBnQTopbPnejoLqV47ji1kmn/Q8bXRKhwumDhbXY2xVnzl/iJsU7a+FG/0aBMRVPsUtgZ4X/ZREBUW7lL10lT0DqA2jXpC78bQFdLKViqARDB7f7Jj+oapcZn3kCXd1FK4DbQWrKDkunetdIR/nf+0sHtaSk9UGJa0G9h7QV3XE/657+TSB13aCq9wRixzxY5Fs+m8PjF0sVmGrllWQk7wR9RGiSsXj9z8P87/2C3UVuOB/crqr+1adwnalWtt5A8y9KpErfGsta6HBlj8YTj8JVCzK6TdBCaawcoE79kZGUCEH85MGwhOUTTW1/miNcmgm5eafRg6iW80wP5GVQ+ctYYDr4Im6UXx5jPOxcgoXT7+6fcqYsEecockcsNnWuoHfJpIW2A2M3Bc2d0XwtvNuIOeExYR97eBfbzrwxWpd0oaDYdLxuKIB+tNpUe3gUycOLqCqkK6KOeS48NobKFZ7drUfY0VOOiCg0TdTatrVb8hBYcjjeJkJ1FLuTy1F4GLFgDZcfDlwJNMY9Iy6T4NpT5Y6iL+o02goVx9x/JYtyos/nzgznvLkd3j3+GR/DdFBIVw1gPKbuR2vG8YTaI6/fgui66Cx5HtSB7WKvLDLcP4vsir7V+IrUqc9R/E61j4SO+ShCFs3sEEsYLMHhaLuNPq8HZkQLUTch3qo2LZmatbn82YLkU6SEEXfiJiOiDAOjuGYIkJ4avxLSDZcjqYbxFruG+VdLRJ+fHPg==";
////        en = "O+hG98gqNTJM3RCyPB+QGFxc/xQeHjd3iFnFRGKAeSmT1pVUD+3qkwfYJyZk4KUarQGBzxKYL/x6JqVve+txWWkPR9Jth8Tagdgfkt2PnG71bx90uFlWk4+2fEQjWsYlmIF+5Z6Ie1rCBT+05V2qEY1HrXmFlO8P/dyWmMmzTaBFVt9ALm5WttdPdJMu31roShL15cjpMBpva36os42FnCd5NeZzs6CAaRctmsxXhUWFfkhpPAfpAyHveGyhuLmMMAFSjd7md9m9tbeJEvod1Rv1s4Q/my5CRmJLOPTt1rTSel3hScmmLotNiqlB4LTptvb/p1yC2DxCNE1SV/1LWkaH1bldjEBrLuFQvsMWrkqxy6Xl4wU5UWnjBg5yOb+U1H12jjr7AAzsBCajZhHFAmxS8MuE2ICBQk+I3Ujr7JJjTRRP2MkZZ2jS+FBGarFq51wXYBvDoWRXqJ+dSwWdKN+yWzVbT/3My3qiW13mdA/MArlm6bR+5XTvv7Apy1lxeFlTcD/NU4h0ojtKwB8nOOXXlIFQc0qwOswdIKi/Hs264BWsmZ55Nr04XEuJpyRKKd5ZRGnYY5x0symhHq7zeLbEMgwaBOUECVycMVhZcjvAaoH9YZUdjCdO5mzqgEiocNa7oOMJPbJBeJCJBjTbIoYd7YmE2ZkepPFKsq/WALyFKSJql5CohuRtWGVQkFt29oQS0xyw3mE1jyIrO5RHmhNV8HIRSSM7wNXDlvgl0U2F+pvF7RNc45SNJHctegRUhQgpAq1OZFVWqEoCXIHPgVGGQH3ks2ioNjE1btsny7aTa3g65wzUplc8ebzOATCScUz8h/mkEeD+JMNzCHDKq174YY6y3Yi60Dz7W/kYoyNdaaESY20InW4LXNTUGEGebuW42YxeAK86xuSVHjcpgUc/knIi+U0kpAx/FD1yFMe6NOxRtq9EO3rFO7hrHRSsLztkzKEHFCMld9VT9LxmdvwZvBEKYYz8srYanFNpyrSlnt7pafhHbZadFcV3WQ5FBi8BGbfjw13iZksPqZqqmLNmEt6JTlDShjj93MIBXOb1O8Xp47UjW5i29TYYyRqyh/oOlLLPVhg9SNXzOmE8TsS0rECcj/TTLTPX+rfQjjL61jRcLTnGC5uzEsWEkKmiEeOW+XZUEn7FBzt2XAPTB4OTYH047bIW+EFfXUXELuA=";
////        System.out.println("en-->" + en);
//
//        String encodedString=new String(new BASE64Decoder().decodeBuffer(decryptByPrivateKey(en, privateKeyString)));
////        String encodedString = new String(Base64Utils.decode(decryptByPrivateKey(en, privateKeyString)));
////        String encodedString=new String(new BASE64Decoder().decodeBuffer(""));
//        System.out.println(encodedString);
//
//        System.out.println(++num);
//
//        System.out.println(System.currentTimeMillis() - a);
////		}

    }
}
