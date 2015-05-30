package tests;

import android.test.InstrumentationTestCase;
import android.util.Log;

import com.study.radasm.vanhttpclient.Utils.QueryString;
import com.study.radasm.vanhttpclient.VanException.VanIllegalParamsException;

/**
 * Created by RadAsm on 15/5/30.
 */
public class VanTest extends InstrumentationTestCase {
    private static final String TAG=VanTest.class.getSimpleName();
    public void test() throws VanIllegalParamsException {
        StringBuilder sb = new StringBuilder();

        String hha = QueryString.add("hha", "44");
        sb.append(hha);

        String hehe = QueryString.add("hehe", "1133");
        sb.append(hehe);

        sb.deleteCharAt(sb.length() - 1);
        Log.i(TAG,sb.toString());
    }
}
