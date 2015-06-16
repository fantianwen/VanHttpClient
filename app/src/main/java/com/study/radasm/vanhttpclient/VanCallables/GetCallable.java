package com.study.radasm.vanhttpclient.VanCallables;

import com.study.radasm.vanhttpclient.Utils.OtherUtils;
import com.study.radasm.vanhttpclient.VanCommon.QueryParams;
import com.study.radasm.vanhttpclient.VanCommon.VanParams;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;

/**
 * 请求进行Get请求的Callable
 * <p>
 * Created by RadAsm on 15/6/15.
 */
public class GetCallable implements Callable<String> {
    private VanParams vanParams;

    public GetCallable(VanParams vanParams) {
        this.vanParams = vanParams;

    }

    /**
     * 执行网络操作
     *
     * @return
     * @throws Exception
     */
    @Override
    public String call() throws Exception {
        String response=null;

        String requestUrl = vanParams.url;

        int timeout =vanParams.timeout;

        if(android.text.TextUtils.isEmpty(requestUrl)){
            throw new IllegalArgumentException("请求地址不能为空！");
        }
        boolean illegalUrl = OtherUtils.isIllegalUrl(requestUrl);
        if(!illegalUrl){
            /*加上默认的“http”的scheme*/
            StringBuilder legalUrlBuilder = new StringBuilder();
            requestUrl = legalUrlBuilder.append(VanParams.stdScheme).append(requestUrl).toString();
        }

        QueryParams queryParmas = vanParams.queryParmas;
        if(queryParmas==null) {
            //直接使用url进行访问
            URL url = new URL(requestUrl);
            URLConnection urlConnection = url.openConnection();

            urlConnection.setConnectTimeout(timeout);
            urlConnection.setReadTimeout(timeout);

            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            response = OtherUtils.transIs2String(inputStream);
        }
        return response;
    }


}
