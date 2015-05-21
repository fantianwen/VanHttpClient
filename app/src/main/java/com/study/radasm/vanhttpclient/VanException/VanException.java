package com.study.radasm.vanhttpclient.VanException;

/**
 * 自定义异常基类
 *
 * Created by RadAsm on 15/5/20.
 */
public class VanException extends Exception{
    /**请求参数为空*/
    public static final int EMPTY_PARAMS=0;
    /**请求参数错误*/
    public static final int ILLEGAL_PARAMS=1;
    /**请求返回结果错误*/
    public static final int ERROR_RESULT=2;


    public static final String MSG_EMPTY_PARAMS="请求参数为空";
    public static final String MSG_ILLEGAL_PARAMS="请求参数非法";
    public static final String MSG_ERROR_RESULT="返回结果错误";


    /**错误代码*/
    public int error_code;
    /**错误信息*/
    public String vanMessage;


    public VanException() {
    }

    public VanException(String detailMessage) {
        super(detailMessage);
    }

    public VanException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public VanException(Throwable throwable) {
        super(throwable);
    }

    public VanException(int error_code){
        this.error_code=error_code;
    }

    public VanException(int error_code,String vanMessage){
        this.error_code=error_code;
        this.vanMessage=vanMessage;
    }

    public int getError_code() {
        return error_code;
    }

    public String getVanMessage() {
        return vanMessage;
    }

    public String getVanMessage(int error_code){
        switch (error_code){
            case ILLEGAL_PARAMS:
                /**请求参数非法*/
                this.vanMessage=MSG_ILLEGAL_PARAMS;
                break;

            default:
                break;
        }
        return this.vanMessage;
    }

}
