package com.study.radasm.vanhttpclient.Utils;

/**
 * Java类的一些基础的工具方法
 * Created by RadAsm on 15/5/21.
 */
public class ClassUtils {

    /**
     * 获取类的简单名字
     * @param clazz
     * @return
     */
    public static String getSimpleName(Class<? extends Object> clazz) {
        String simpleName = clazz.getSimpleName();
        return simpleName;
    }
}
