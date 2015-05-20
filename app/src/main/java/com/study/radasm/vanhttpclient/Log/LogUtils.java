package com.study.radasm.vanhttpclient.Log;

import android.util.Log;

/**
 * Created by RadAsm on 15/5/20.
 */
public class LogUtils{
    /**info一般信息类输出级别*/
    public static final int LEVEL_I=10;
    /**error错误类信息输出级别*/
    public static final int LEVEL_E=100;
    /**debug调试类信息输出级别*/
    public static final int LEVEL_D=1000;

    /**控制日志的输出级别*/
    public static final int LOG_LEVEL=0;

    /**
     * 输出info信息日志
     * @param tag
     * @param msg
     */
    public static void i(String tag,String msg){
        if(LOG_LEVEL<LEVEL_I){
            Log.i(tag,msg);
        }
    }

    /**
     * 输出error信息日志
     * @param tag
     * @param msg
     */
    public static void e(String tag,String msg){
        if(LOG_LEVEL<LEVEL_E){
            Log.e(tag,msg);
        }
    }

    /**
     * 输出debug信息日志
     * @param tag
     * @param msg
     */
    public static void d(String tag,String msg){
        if(LOG_LEVEL<LEVEL_D){
            Log.d(tag,msg);
        }
    }

}
