package com.study.radasm.vanhttpclient;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.lidroid.xutils.http.client.DefaultSSLSocketFactory;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import java.lang.reflect.Field;
import java.util.Locale;

/**
 * Created by RadAsm on 15/6/10.
 */
public class VanClient {
    /**
     * 默认连接超时时间：0，表示永不超时
     */
    public static final int DEFAULT_SOCKET_TIMEOUT = 0;

    /**默认重新尝试的时间间隔*/
    private final static int DEFAULT_RETRY_TIMES = 3;


    /**
     * 获取用户代理
     */
    public static String getDefaultUserAgent(Context context) {
        String webUserAgent = null;
        if (context != null) {
            try {
                Class sysResCls = Class.forName("com.android.internal.R$string");
                Field webUserAgentField = sysResCls.getDeclaredField("web_user_agent");
                Integer resId = (Integer) webUserAgentField.get(null);
                webUserAgent = context.getString(resId);
            } catch (Throwable ignored) {
            }
        }
        if (TextUtils.isEmpty(webUserAgent)) {
            webUserAgent = "Mozilla/5.0 (Linux; U; Android %s) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 %sSafari/533.1";
        }

        Locale locale = Locale.getDefault();
        StringBuffer buffer = new StringBuffer();
        // Add version
        final String version = Build.VERSION.RELEASE;
        if (version.length() > 0) {
            buffer.append(version);
        } else {
            // default to "1.0"
            buffer.append("1.0");
        }
        buffer.append("; ");
        final String language = locale.getLanguage();
        if (language != null) {
            buffer.append(language.toLowerCase());
            final String country = locale.getCountry();
            if (country != null) {
                buffer.append("-");
                buffer.append(country.toLowerCase());
            }
        } else {
            // default to "en"
            buffer.append("en");
        }
        // add the model for the release build
        if ("REL".equals(Build.VERSION.CODENAME)) {
            final String model = Build.MODEL;
            if (model.length() > 0) {
                buffer.append("; ");
                buffer.append(model);
            }
        }
        final String id = Build.ID;
        if (id.length() > 0) {
            buffer.append(" Build/");
            buffer.append(id);
        }
        return String.format(webUserAgent, buffer, "Mobile ");
    }

    /**
     * 获取默认的HttpClient，这个HttpClient可以自行定制
     * @return
     */
    public static HttpClient getHttpClientInstance(int connTimeout,String userAgent) {
        setHttpParams(connTimeout,userAgent);

        ClientConnectionManager conman=null;
        HttpParams params=null;

        SchemeRegistry schreg=null;
        schreg=new SchemeRegistry();
        /**http默认的端口号：80*/
        schreg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        /**https默认端口号：443*/
        schreg.register(new Scheme("https", DefaultSSLSocketFactory.getSocketFactory(), 443));
        /**一个多线程的client管理器，可以同时处理多个请求*/
        conman=new ThreadSafeClientConnManager(params,schreg);

        DefaultHttpClient httpClient=new DefaultHttpClient(conman, params);

        RetryHandler retryHandler=new RetryHandler(DEFAULT_RETRY_TIMES);

        /**设置http重连机制*/
        httpClient.setHttpRequestRetryHandler(retryHandler);

        return httpClient;

    }

    /**
     * 设置http连接的基础参数
     * @param connTimeout 连接超时
     * @param userAgent 用户代理
     */
    public static void setHttpParams(int connTimeout,String userAgent){
        HttpParams params = new BasicHttpParams();

        ConnManagerParams.setTimeout(params, connTimeout);
        HttpConnectionParams.setSoTimeout(params, connTimeout);
        HttpConnectionParams.setConnectionTimeout(params, connTimeout);

        if (TextUtils.isEmpty(userAgent)) {
            userAgent = getDefaultUserAgent(null);
        }
        HttpProtocolParams.setUserAgent(params, userAgent);

        ConnManagerParams.setMaxConnectionsPerRoute(params, new ConnPerRouteBean(10));
        ConnManagerParams.setMaxTotalConnections(params, 10);

        HttpConnectionParams.setTcpNoDelay(params, true);
        HttpConnectionParams.setSocketBufferSize(params, 1024 * 8);
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
    }

}
