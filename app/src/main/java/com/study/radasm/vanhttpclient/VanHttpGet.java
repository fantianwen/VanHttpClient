package com.study.radasm.vanhttpclient;

import android.net.Uri;
import android.util.Log;

import com.study.radasm.vanhttpclient.Tasks.GetTask;
import com.study.radasm.vanhttpclient.Utils.ClassUtils;
import com.study.radasm.vanhttpclient.Utils.LogUtils;
import com.study.radasm.vanhttpclient.Utils.QueryString;
import com.study.radasm.vanhttpclient.Utils.ThreadManager;
import com.study.radasm.vanhttpclient.Utils.UriUtils;
import com.study.radasm.vanhttpclient.VanCallBack.Callback;
import com.study.radasm.vanhttpclient.VanException.VanIllegalParamsException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Get方式网络请求
 *      execute()方法是较为基础的，因为只是单纯的返回一个InputStream流，并没有进行显式去书写获得具体的东西。
 * <p/>
 * Created by RadAsm on 15/5/20.
 */
public class VanHttpGet {

    private static final String TAG = ClassUtils.getSimpleName(VanHttpGet.class);

    private HttpClient httpClient;

    /**===================================构造函数========================================**/

    public VanHttpGet() {
        this(VanClient.DEFAULT_SOCKET_TIMEOUT);

    }

    public VanHttpGet(int timeout){
        this(timeout,VanClient.getDefaultUserAgent(null));
    }

    public VanHttpGet(String userAgent){
        this(VanClient.DEFAULT_SOCKET_TIMEOUT,userAgent);

    }

    public VanHttpGet(int timeout,String userAgent){
        httpClient=VanClient.getHttpClientInstance(timeout,userAgent);
    }

    /**===================================构造函数========================================**/


    /**
     * get请求执行体(同步请求)
     *
     * @param baseUri       请求服务器的homePath
     * @param apiPath       请求服务器的api接口
     * @param requestParams get请求参数
     */
    public void execute(Uri baseUri, String apiPath, HashMap<String, String> requestParams, Callback callback) throws IOException, VanIllegalParamsException {
        String basePath = UriUtils.uri2String(baseUri);
        execute(basePath, apiPath, requestParams, callback);

    }

    /**
     * Get异步请求
     *
     * @param baseUri       主机url地址
     * @param apiPath       get请求api
     * @param requestParams get请求参数
     * @param callback      请求结果回调
     */
    public void asyncExecute(Uri baseUri, String apiPath, HashMap<String, String> requestParams, Callback callback) throws VanIllegalParamsException {

        String basePath = UriUtils.uri2String(baseUri);
        String requestPath = basePath + apiPath + "?" + QueryString.catGetParams(requestParams);

        GetTask getTask = new GetTask(requestPath, callback);

        ThreadManager.executeShort(getTask);

    }


    /**
     * 重载方法 使用String类型的BasePath(同步请求方式)
     *
     * @param basePath
     * @param apiPath
     * @param requestParams
     * @param callback
     */
    public void execute(String basePath, String apiPath, HashMap<String, String> requestParams, Callback callback) throws VanIllegalParamsException, IOException {

        String requestPath = basePath + apiPath + "?" + QueryString.catGetParams(requestParams);

        HttpGet httpGet = new HttpGet(requestPath);
        HttpResponse response = httpClient.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == HttpStatus.SC_OK) {
            //request请求成功
            LogUtils.i(TAG, "get请求成功！");
            InputStream is = response.getEntity().getContent();
            callback.onSuccess(is);
        } else {
            Log.e(TAG, "get请求返回异常！");
            //request请求失败
            callback.onFailure(statusCode);
        }
    }

    /**
     * Get异步请求(String 方式)
     *
     * @param basePath      主机url地址
     * @param apiPath       get请求api
     * @param requestParams get请求参数
     * @param callback      请求结果回调
     */
    public void asyncExecute(String basePath, String apiPath, HashMap<String, String> requestParams, Callback callback) throws VanIllegalParamsException {

        String requestPath = basePath + apiPath + "?" + QueryString.catGetParams(requestParams);

        GetTask getTask = new GetTask(requestPath, callback);

        ThreadManager.executeShort(getTask);

    }









}

