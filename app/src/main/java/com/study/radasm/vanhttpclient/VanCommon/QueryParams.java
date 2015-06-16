package com.study.radasm.vanhttpclient.VanCommon;

import android.util.Log;

import com.study.radasm.vanhttpclient.Utils.TextUtils;
import com.study.radasm.vanhttpclient.VanException.VanIllegalParamsException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import rx.Observable;

/**
 * Created by RadAsm on 15/6/15.
 */
public class QueryParams {

    private static VanIllegalParamsException vanIllegalParamsException;
    public Map<String,Object> queryMap;

    public QueryParams(Map<String,Object> queryMap){
        this.queryMap=queryMap;

    }

    /**
     * 拼接query字符串
     * @return {@link Observable}
     */
    public Observable comCatParams(){

        return Observable.create(subscriber -> {
            try {
                subscriber.onNext(encodeMap(this.queryMap).call());
            } catch (Exception e) {
                subscriber.onError(e);
                e.printStackTrace();
            }
        });
    }


    private static final String DEFAULT_ENCODE = "UTF-8";
    private static final String TAG =QueryParams.class.getSimpleName();


    /**
     * 因为URLEncoder.encode()不能够识别不需要进行转义的字符，如“/”,":"等，所以我们需要对query部分的key-value进行单独编码
     *
     * @param key   query部分的key
     * @param value query部分的value
     *
     * @throws VanIllegalParamsException get请求参数错误时抛出的异常
     */
    public static String encode(String key, String value) throws VanIllegalParamsException {
        vanIllegalParamsException=new VanIllegalParamsException();
        if (!com.study.radasm.vanhttpclient.Utils.TextUtils.isNotEmpty(key, value)) {
            Log.e(TAG, vanIllegalParamsException.getVanMessage());
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


    public static Callable encodeMap(Map<String,Object> map){
        return new Callable() {
            @Override
            public Object call() throws Exception {
                StringBuilder sb=new StringBuilder();
                Set<Map.Entry<String, Object>> entries = map.entrySet();
                Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                while(iterator.hasNext()){
                    Map.Entry<String, Object> next = iterator.next();
                    String key = next.getKey();
                    Object value = next.getValue();
                    try {
                        String encode = encode(key, value.toString());
                        sb.append(encode);
                        sb.append("&");
                    } catch (VanIllegalParamsException e) {
                        e.printStackTrace();
                    }
                }
                String s = sb.toString();
                String substring = s.substring(0, s.length() - 1);
                return substring;
            }
        };
    }

}
