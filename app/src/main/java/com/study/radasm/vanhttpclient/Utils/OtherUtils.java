package com.study.radasm.vanhttpclient.Utils;

import com.study.radasm.vanhttpclient.VanCommon.VanParams;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

/**
 * 一些工具类
 * Created by RadAsm on 15/6/15.
 */
public class OtherUtils {

    /**
     * 将InoputStream流转化成Byte流
     *
     * @param is
     * @return
     */
    public static String transIs2String(InputStream is,String charset) {
        return is2Bao(is,charset);
    }

    /**
     * 这里应该不需要使用耗时的操作的Callable进行构造，在相应式的程序设计中，最终的异步访问应该让族中调用者显示的进行
     */
    public static String is2Bao(InputStream is,String charset) {
        ByteArrayOutputStream bao = null;
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(is,charset));
            bao = new ByteArrayOutputStream();
            int ch;
            int i=0;
            while((ch=bufferedReader.read())!=-1){
                bao.write(ch);
            }
            bao.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (bao != null) {
                try {
                    bao.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        byte[] bytes = bao.toByteArray();
        String s = new String(bytes);
        return s;
    }


    /**
     * 检验一个url是不是合规的Url地址
     * @param url
     */
    public static boolean isIllegalUrl(String url){
        boolean isIllegal=false;
        String lowerUrl = url.toLowerCase();
        if(lowerUrl.startsWith("http")||lowerUrl.startsWith("https")){
            isIllegal=true;
        }
        return isIllegal;
    }

    public static String getCharset(Map<String,List<String>> headerFields){
        String encode= VanParams.stdEncode;
        List<String> strings2 = headerFields.get("Content-Type");
        for(int i=0;i<strings2.size();i++){
            if(strings2.get(i).equals("charset")){
                encode=strings2.get(i);
            }
        }
        return encode;
    }
}
