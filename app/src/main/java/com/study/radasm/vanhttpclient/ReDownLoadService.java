package com.study.radasm.vanhttpclient;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

/**
 * Created by RadAsm on 15/6/4.
 */
public class ReDownLoadService extends IntentService{
    private static final String COMMAND = "command";
    private static final String TYPE = "type";
    private static final String URLPATH = "urlPath";

    /**当通过获取头部信息发现缓存过期时重新下载二进制文件*/
    private static final int TYPE_REDOWNWHENEXPIRE=0x0001;


    /**通知handle循环*/
    private static final int RECHECKEXPIRATION = 0x1000;


    private Check2RedownHandler check2RedownHandler;



    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public ReDownLoadService() {
        super("loop2cacheFileThread");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void setIntentRedelivery(boolean enabled) {
        super.setIntentRedelivery(enabled);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle bundle = intent.getExtras().getBundle(COMMAND);
        if(bundle==null){
            throw new NullPointerException("未知的指令");
        }
        int type = (int) bundle.get(TYPE);
        final String urlPath = bundle.getString(URLPATH);
        switch (type){
            case TYPE_REDOWNWHENEXPIRE://文件过期重新下载
                /**
                 * 每隔一个小时检查文件是否过期，如果过期，重新下载
                 */
                check2RedownHandler=new Check2RedownHandler();

                check2RedownHandler.sendEmptyMessage(RECHECKEXPIRATION);

                break;
            default:
                break;

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private class Check2RedownHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            final String urlPath = (String) msg.obj;

            new Thread(){
                @Override
                public void run() {
                    try {
                        URL filrUrl = new URL(urlPath);
                        URLConnection uc = filrUrl.openConnection();
                        long expiration = uc.getExpiration();
                        Date currentTime = new Date();

                        long currentTimeTime = currentTime.getTime();

                        if(currentTimeTime>=expiration){
                            //TODO 过期了，重新下载

                        }
                        /**
                         * 如果没有过期，计算将要过期的时间，在该时间后重新进行检查
                         */
                        check2RedownHandler.sendEmptyMessageDelayed(RECHECKEXPIRATION,currentTimeTime-expiration);

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }.start();


        }
    }

}
