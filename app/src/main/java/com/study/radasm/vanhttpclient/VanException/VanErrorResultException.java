package com.study.radasm.vanhttpclient.VanException;

import android.util.Log;

/**
 * Created by RadAsm on 15/5/20.
 */
public class VanErrorResultException extends VanException {
    public static final String TAG=VanIllegalParamsException.class.getSimpleName();

    public VanErrorResultException(){
        error_code=ERROR_RESULT;
        vanMessage=MSG_ERROR_RESULT;
    }

    @Override
    public void printStackTrace() {
        Log.e(TAG,vanMessage);
        super.printStackTrace();
    }
}
