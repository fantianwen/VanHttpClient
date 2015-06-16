package com.study.radasm.vanhttpclient.WebService;

import com.study.radasm.vanhttpclient.Utils.RxUtils;
import com.study.radasm.vanhttpclient.VanCallables.GetCallable;
import com.study.radasm.vanhttpclient.VanCommon.QueryParams;
import com.study.radasm.vanhttpclient.VanCommon.VanParams;

import java.util.concurrent.Callable;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by RadAsm on 15/6/14.
 */
public class WebService <T> {

    /**
     * 同步
     * @param url
     * @param queryParams
     * @return
     */
    public static <T> Observable<T> execute(String url ,QueryParams queryParams){
        /**先将VanParams转化成一个Callable对象*/
        VanParams vanParams = new VanParams(url, queryParams);
        GetCallable getCallable = new GetCallable(vanParams);
        return execute(getCallable);

    }

    /**
     * 异步
     * @param url
     * @param queryParams
     * @return
     */
    public static <T> Observable<T> aynscExecute(String url ,QueryParams queryParams){
        /**先将VanParams转化成一个Callable对象*/
        VanParams vanParams = new VanParams(url, queryParams);
        GetCallable getCallable = new GetCallable(vanParams);
        return aynscExecute(getCallable);

    }



    /**
     * 同步线程 直接使用一个Callable进行执行
     * @param getCall
     * @return
     */
    public static Observable execute(Callable getCall) {
        return RxUtils.createObservable(getCall);
    }

    /**
     * 默认是异步线程
     *
     * 执行get方法，并将其注册到线程池中，最后使用Android.mainThread进行UI的绘制
     * @param getCall
     * @return
     */
    public static <T> Observable<T> aynscExecute(Callable getCall) {
        return RxUtils.createObservable(getCall).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread());
    }




}
