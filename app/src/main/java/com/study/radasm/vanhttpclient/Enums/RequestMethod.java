package com.study.radasm.vanhttpclient.Enums;

/**
 * url请求的方式
 *
 * Created by RadAsm on 15/6/16.
 */
public enum RequestMethod {
    METHOD_GET(0x0001),METHOD_POST(0x0002);

    private int method;

    RequestMethod(int method){
        this.method=method;
    }

    public int getMethod(){
        return method;
    }

}
