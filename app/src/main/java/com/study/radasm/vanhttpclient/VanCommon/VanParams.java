package com.study.radasm.vanhttpclient.VanCommon;


import com.study.radasm.vanhttpclient.Enums.RequestMethod;

import java.util.concurrent.Callable;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by RadAsm on 15/6/15.
 */
public class VanParams {
    /**默认的超时时间是2000ms*/
    private static final int DEFAULT_TIMEOUT = 2000;
    /**默认的请求方式是GET*/
    private static final int DEFAULT_REQUEST_METHOD = RequestMethod.METHOD_GET.getMethod();
    /**请求的url*/
    public String url;
    /**查询的参数*/
    public QueryParams queryParmas;

    /**连接超时的时间*/
    public int timeout;
    /**连接请求的方式*/
    public int requestMethod;


    public VanParams(String url) {
        this(url, null);
    }

    public VanParams(String url, QueryParams queryParams) {
        this(url,queryParams,DEFAULT_TIMEOUT);
    }

    public VanParams(String url,QueryParams queryParams,int timeout){
        this(url,queryParams,timeout,DEFAULT_REQUEST_METHOD);
    }
    
    public VanParams(String url,QueryParams queryParams,int timeout,int requestMethod){
        this.url=url;
        this.queryParmas=queryParams;
        this.timeout=timeout;
        this.requestMethod=requestMethod;
    }


    /**
     * 获得请求的Url地址
     * @return
     */
    public Observable requestUrl() {

        return Observable.create(subscriber -> {
            try {
                subscriber.onNext(conCat(url,queryParmas).call());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }


    public Callable conCat(String url, QueryParams queryParams) {
        return new Callable() {
            @Override
            public Object call() throws Exception {
                final StringBuilder sb=new StringBuilder();
                sb.append(url);
                sb.append("?");
                queryParams.comCatParams().subscribe(new Action1() {
                    @Override
                    public void call(Object o) {
                        sb.append(o.toString());
                    }
                });
                return sb.toString();
            }
        };
    }


}
