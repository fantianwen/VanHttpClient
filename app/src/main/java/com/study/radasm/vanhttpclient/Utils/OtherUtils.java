package com.study.radasm.vanhttpclient.Utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
    public static String transIs2String(InputStream is) {
        return is2Bao(is);
    }

    /**
     * 这里应该不需要使用耗时的操作的Callable进行构造，在相应式的程序设计中，最终的异步访问应该让族中调用者显示的进行
     */
    public static String is2Bao(InputStream is) {
        ByteArrayOutputStream bao = null;
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
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


}
