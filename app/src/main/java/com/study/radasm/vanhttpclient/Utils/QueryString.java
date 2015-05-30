package com.study.radasm.vanhttpclient.Utils;

import android.util.Log;

import com.study.radasm.vanhttpclient.VanException.VanIllegalParamsException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * URI查询部分key-value的拼接
 * <p/>
 * Created by RadAsm on 15/5/30.
 */
public class QueryString {
    private static final String TAG=QueryString.class.getSimpleName();
    /**
     * 默认采用UTF-8进行url编码
     */
    private static final String DEFAULT_ENCODE = "UTF-8";
    private static final VanIllegalParamsException vanIllegalParamsException = new VanIllegalParamsException();

    /**
     * 因为URLEncoder.encode()不能够识别不需要进行转义的字符，如“/”,":"等，所以我们需要对query部分的key-value进行单独编码
     *
     * @param key   query部分的key
     * @param value query部分的value
     *
     * @throws VanIllegalParamsException get请求参数错误时抛出的异常
     */
    public static String encode(String key, String value) throws VanIllegalParamsException {
        if (!com.study.radasm.vanhttpclient.Utils.TextUtils.isNotEmpty(key, value)) {
            Log.e(TAG,vanIllegalParamsException.getVanMessage());
            throw vanIllegalParamsException;
        }
        StringBuilder queryString = new StringBuilder();
        try {
            String encoded_key = URLEncoder.encode(key, DEFAULT_ENCODE);
            String encoded_value = URLEncoder.encode(value, DEFAULT_ENCODE);
            queryString.append(encoded_key);
            queryString.append("=");
            queryString.append(encoded_value);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String encoded_queryString = queryString.toString();
        if(TextUtils.isNotEmpty(encoded_queryString)){
            return encoded_queryString;
        }else{
            Log.e("url编码","发生错误！");
            throw new NullPointerException();
        }
    }

    /***
     * 将url编码好的query参数添加到url的后面
     * @param key query部分的key
     * @param value query部分的value
     * @return
     */
    public static String add(String key,String value) throws VanIllegalParamsException {
        String encodedString = encode(key, value);
        StringBuilder could_add = new StringBuilder();
        could_add.append(encodedString);
        could_add.append("&");
        return could_add.toString();
    }
}
