package com.study.radasm.vanhttpclient.Utils;

import android.net.Uri;

/**
 * Uri类相关的工具
 *
 * Created by RadAsm on 15/6/3.
 */
public class UriUtils {
    /**
     * 将Uri地址转化成String字符串
     * @param uri Uri对象
     * @return Uri转化成String字符串的值
     */
    public static String uri2String(Uri uri){
        if(uri==null){
            throw new NullPointerException();
        }
        return uri.toString();
    }
}
