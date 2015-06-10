package com.study.radasm.vanhttpclient;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.lidroid.xutils.HttpUtils;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private HttpUtils httpUtils;
    private String webUserAgent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//
//        httpUtils = new HttpUtils();
//
//
//
//        Class sysResCls = null;
//        try {
//            sysResCls = Class.forName("com.android.internal.R$string");
//            Field webUserAgentField = sysResCls.getDeclaredField("web_user_agent");
//            Integer resId = (Integer) webUserAgentField.get(null);
//            webUserAgent = this.getString(resId);
//
//            Log.e(TAG,webUserAgent);
//
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
//
//        ContentProducer contentProducer = new ContentProducer() {
//            @Override
//            public void writeTo(OutputStream outputStream) throws IOException {
//                Writer writer = new OutputStreamWriter(outputStream, "UTF-8");
//                writer.write(...);//进行内容的书写
//
//            }
//        };
//        EntityTemplate entityTemplate=new EntityTemplate(contentProducer);
//
//
//        HttpGet httpGet = new HttpGet("www.baidu.com");
//        ResponseHandler<?> responseHandler=new ResponseHandler<Object>() {
//            @Override
//            public Object handleResponse(HttpResponse httpResponse) throws IOException {
//                return null;
//            }
//        };
//        try {
//            Object result = defaultHttpClient.execute(httpGet, responseHandler);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        BasicHttpParams basicHttpParams = new BasicHttpParams();
//
//
//
//        HttpProtocolParamBean httpProtocolParamBean = new HttpProtocolParamBean(basicHttpParams);
//        httpProtocolParamBean.setContentCharset();
//        httpProtocolParamBean.setHttpElementCharset();
//        httpProtocolParamBean.setUseExpectContinue();
//        httpProtocolParamBean.setUserAgent();
//        httpProtocolParamBean.setVersion();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
