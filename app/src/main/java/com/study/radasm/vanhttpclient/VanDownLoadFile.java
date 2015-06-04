package com.study.radasm.vanhttpclient;

import java.net.URL;

/**
 * 从服务器下载（二进制）文件到指定的文件夹
 *      思路：1>根据head中的Content-type字段进行判断，应该是一个二进制文件；
 *           2>http服务器并不总是在数据发送完就立即关闭连接，因此，这样就不知道何时停止读取；
 *           3>为了解决2中的问题，我们需要读取head中的Content-length字段，通过这个字段，不断进行判断，直到读取完毕。
 *
 * Created by RadAsm on 15/6/4.
 */
public class VanDownLoadFile {

    public VanDownLoadFile(){

    }

    /**
     * 保存服务器下载的二进制文件，并提供回调结果，结果中提供最终存储的文件的路径。
     *      文件在服务器上面的全路径名称：url.getFile();
     * @param url
     */
    public void saveBinaryFile(URL url){

    }









}
