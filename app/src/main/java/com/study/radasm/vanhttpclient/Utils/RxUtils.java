package com.study.radasm.vanhttpclient.Utils;

import java.util.concurrent.Callable;

import rx.Observable;

/**
 * RxJava响应式编程。
 * Created by RadAsm on 15/6/15.
 */
public class RxUtils<T> {

    /**
     * 从Callable创建一个Observable，之后这个Callable会使用compute()进行执行
     * @param func
     * @param <T>
     * @return
     */
    public static <T> Observable<T> createObservable(Callable<T> func){
        return Observable.create(f -> {
            try {
                f.onNext(func.call());
            } catch (Exception e) {
                f.onError(e);
            }
        });
    }


}
