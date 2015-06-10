package com.study.radasm.vanhttpclient;

import android.net.Uri;

import com.study.radasm.vanhttpclient.Utils.ClassUtils;
import com.study.radasm.vanhttpclient.Utils.LogUtils;
import com.study.radasm.vanhttpclient.VanCallBack.Callback;
import com.study.radasm.vanhttpclient.VanException.VanException;
import com.study.radasm.vanhttpclient.VanException.VanIllegalParamsException;
import com.study.radasm.vanhttpclient.common.VanClientConnectionManager;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Post方式请求网络
 * <p/>
 * Created by RadAsm on 15/5/21.
 */
public class VanHttpPost {

    private static final String TAG = ClassUtils.getSimpleName(VanHttpPost.class);
    private DefaultHttpClient httpClient;
    private VanClientConnectionManager vanccm;


    public VanHttpPost() {


    }
    /**
     * Post请求执行体（同步方式）xUtils-2.6.14.jar
     * notice：该种post请求方式携带的实体数据适用于一般性的表单提交，即为一般性的<application/x-www-form-urlencoded>
     * 这样的表单提交方式。
     */
    public void execute(Uri baseUri, String apiPath, HashMap<String, String> requestParams, Callback callback) throws IOException, VanIllegalParamsException {

        String requestUri = baseUri.toString() + apiPath;
        HttpPost httpPost = new HttpPost(requestUri);
        /**构造请求实体*/
        List<NameValuePair> parameters = new ArrayList<>();
        if(requestParams.isEmpty()){
            /**如果是空的请求参数，抛出异常*/
            LogUtils.e(TAG, "post请求参数为空！");
            VanIllegalParamsException vanIllegalParamsException = new VanIllegalParamsException(VanException.EMPTY_PARAMS);
            LogUtils.e(TAG, vanIllegalParamsException.getVanMessage());
            throw vanIllegalParamsException;
        }else{
            Set<String> keySet = requestParams.keySet();
            for (final String key : keySet) {
                final String value = requestParams.get(key);
                parameters.add(new NameValuePair() {
                    @Override
                    public String getName() {
                        return key;
                    }

                    @Override
                    public String getValue() {
                        return value;
                    }
                });
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters);

            /**将entity放入post请求中,并直接执行*/
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                LogUtils.i(TAG, "post请求成功！");
                InputStream is = response.getEntity().getContent();
                callback.onSuccess(is);
            }else{
                LogUtils.i(TAG,"post请求返回异常！");
                callback.onFailure(statusCode);
            }
        }
    }


    /**
     * post请求（同步方式）
     * @param basePath
     * @param apiPath
     * @param requestParams
     * @param callback
     */
    public void execute(String basePath, String apiPath, HashMap<String, String> requestParams, Callback callback){


    }
}
