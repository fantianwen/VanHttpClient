package com.study.radasm.vanhttpclient;

import android.net.Uri;
import android.util.Log;

import com.study.radasm.vanhttpclient.Utils.ClassUtils;
import com.study.radasm.vanhttpclient.Utils.LogUtils;
import com.study.radasm.vanhttpclient.VanCallBack.Callback;
import com.study.radasm.vanhttpclient.VanException.VanException;
import com.study.radasm.vanhttpclient.VanException.VanIllegalParamsException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Get方式网络请求
 * <p/>
 * Created by RadAsm on 15/5/20.
 */
public class VanHttpGet {

    private static final String TAG = ClassUtils.getSimpleName(VanHttpGet.class);

    public VanHttpGet() {

    }

    /**
     * get请求执行体(同步请求)
     *
     * @param baseUri       请求服务器的homePath
     * @param apiPath       请求服务器的api接口
     * @param requestParams get请求参数
     */
    public void execute(Uri baseUri, String apiPath, HashMap<String, String> requestParams,Callback callback) throws IOException, VanIllegalParamsException{
        HttpClient httpClient = new DefaultHttpClient();
        String basePath = baseUri.toString();

        /**拼接request的地址部分*/
        StringBuffer paramsPath = new StringBuffer();
        Set<String> keys = requestParams.keySet();
        if(keys.isEmpty()){
            //为空，抛出异常
            Log.e(TAG,"请求参数为空！");
            VanIllegalParamsException vanIllegalParamsException = new VanIllegalParamsException(VanException.EMPTY_PARAMS);
            vanIllegalParamsException.printStackTrace();
            throw vanIllegalParamsException;
        }else{
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = requestParams.get(key);
                paramsPath.append(key + "=" + value);
                paramsPath.append("&");
            }
            //去除掉最后一个“&”
            paramsPath.deleteCharAt(paramsPath.length() - 1);
            String requestPath = basePath + apiPath + "?" + paramsPath.toString();

            HttpGet httpGet = new HttpGet(requestPath);
            HttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode== HttpStatus.SC_OK){
                //request请求成功
                LogUtils.i(TAG,"get请求成功！");
                InputStream is = response.getEntity().getContent();
                callback.onSuccess(is);
            }else{
                Log.e(TAG,"get请求返回异常！");
                //request请求失败
                callback.onFailure(statusCode);
            }
        }
    }
    //TODO 重载方法的书写 以及需要测试

}
