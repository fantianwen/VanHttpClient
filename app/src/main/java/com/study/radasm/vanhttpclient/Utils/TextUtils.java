package com.study.radasm.vanhttpclient.Utils;

/**
 * 判断String类型的数据是否为null或者是“”
 * Created by RadAsm on 15/5/30.
 */
public class TextUtils {
    /**
     * judge weather args are all not empty("" or null)
     * @param args
     * @return
     */
    public static boolean isNotEmpty(String... args) {
        boolean flag = true;
        int count=args.length;
        if(count>0){
            for(int i=0;i<count;i++){
                boolean b = !android.text.TextUtils.isEmpty(args[i]);
                flag = flag & b;
            }
        }
        return flag;
    }

}
