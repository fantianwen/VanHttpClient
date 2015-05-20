package com.study.radasm.vanhttpclient.VanException;

import android.util.Log;

/**
 * Get请求携带参数错误
 *
 * Created by RadAsm on 15/5/20.
 */
public class VanIllegalParamsException extends VanException{
    private static final String TAG=VanIllegalParamsException.class.getSimpleName();

    public VanIllegalParamsException(){
        error_code=ILLEGAL_PARAMS;
        vanMessage=getVanMessage(error_code);
    }

    /**打印异常信息*/
    @Override
    public void printStackTrace() {
        Log.e(TAG,vanMessage);
        super.printStackTrace();
    }
}
