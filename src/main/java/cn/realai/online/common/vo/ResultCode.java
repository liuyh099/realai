package cn.realai.online.common.vo;

public enum ResultCode {

    SUCCESS(0),

    PYTHON_SUCCESS(200),
    
    BLENDING_ERROR(201),
    
    PYTHON_WAIT(202),

    PARAM_ERROR(400),

    ILLEAGAL_REQUEST(401),

    NOT_FOUND(404),

    SESSION_TIME_OUT(408),

    DATA_ERROR(500),

    MULTI_REQUEST(900),

    NO_PERMISSION(600),

    REAL_TIME_ERROR(700),
    
    REAL_TIME_NO_RELEASE(701),
    
    REAL_TIME_EXPIRED(702),
    
	REAL_TIME_TIME_OUT(703);
	
    private int code;

    private ResultCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static ResultCode getByType(int code) {
        for (ResultCode resultCode : values()) {
            if (resultCode.getCode() == code) {
                return resultCode;
            }
        }
        return null;
    }

}
