package cn.realai.online.lic;


/**
 * Description: 秘钥类型
 * <br>
 * <br>Author: Shunping.Fu
 * <br>Date: 2019/3/20
 */
public enum SecretKeyType {

    COMMON(0, "common","普通"),
    TUNING(1, "tuning","强制调优");

    private int code;
    private String name;
    private String msg;

    private SecretKeyType(int code, String name, String msg) {
        this.code = code;
        this.name=name;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public String getName() {
        return name;
    }

    public static SecretKeyType getType(int code){
        for(SecretKeyType secretKeyType: SecretKeyType.values()){
            if(code == secretKeyType.getCode()){
                return secretKeyType;
            }
        }
        return SecretKeyType.COMMON;
    }


}
