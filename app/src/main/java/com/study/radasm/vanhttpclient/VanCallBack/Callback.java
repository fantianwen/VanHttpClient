package com.study.radasm.vanhttpclient.VanCallBack;

import java.io.InputStream;

/**
 * 回调接口
 * <p/>
 * Created by RadAsm on 15/5/20.
 */
public interface Callback {
    void onSuccess(InputStream is);

    void onFailure(int status_code);
}
