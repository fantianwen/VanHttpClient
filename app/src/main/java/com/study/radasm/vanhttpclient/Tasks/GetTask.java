package com.study.radasm.vanhttpclient.Tasks;

import android.util.Log;

import com.study.radasm.vanhttpclient.Utils.LogUtils;
import com.study.radasm.vanhttpclient.VanCallBack.Callback;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;

/**
 * get请求任务
 * Created by RadAsm on 15/6/3.
 */
public class GetTask implements Runnable{
    private static final String TAG=GetTask.class.getSimpleName();

    private String requestPath;
    private Callback callback;

    public GetTask(String requestPath,Callback callback){
        this.requestPath=requestPath;
        this.callback=callback;
    }

    @Override
    public void run() {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(requestPath);
        HttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == HttpStatus.SC_OK) {
            //request请求成功
            LogUtils.i(TAG, "get请求成功！");
            InputStream is = null;
            try {
                is = response.getEntity().getContent();
            } catch (IOException e) {
                e.printStackTrace();
            }
            callback.onSuccess(is);
        } else {
            Log.e(TAG, "get请求返回异常！");
            //request请求失败
            callback.onFailure(statusCode);
        }
    }
}
