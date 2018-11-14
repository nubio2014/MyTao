package com.mlzq.mytao.http;

import android.app.Application;
import android.os.Handler;

import com.mlzq.mytao.BuildConfig;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;


/**
 * @author zdd
 * @Description:
 * @date 2014年10月25日 下午12:26:57
 * @remark
 */
public class MyApplication extends Application {
    public static final String UPDATE_STATUS_ACTION = "com.umeng.message.example.action.UPDATE_STATUS";//友盟推送
    public static String DeviceToken;
    private static MyApplication mInstance;
    public static final String TAG = MyApplication.class.getSimpleName();
    private Handler handler;
    String TagName = "BAOBAO";


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;


        /**********************Log日志管理start***************************************/
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // 可选)是否显示线程信息。默认的真实
                .methodCount(3)         // (可选) 有多少行显示方法。默认值2
                .methodOffset(7)        // (Optional)隐藏内部抵消方法调用。默认的5
                //	.logStrategy(customLog) // (Optional) 更改日志打印策略。默认的日志的猫
                .tag(TagName)   // (Optional)
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

        /**********************Log日志管理end***************************************/

        /******************网络请求设置start ***********************************/
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
//        CookieJarImpl cookieJar1 = new CookieJarImpl(new MemoryCookieStore());
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor("TAG", BuildConfig.DEBUG))
                .hostnameVerifier(new HostnameVerifier()
                {
                    @Override
                    public boolean verify(String hostname, SSLSession session)
                    {
                        return true;
                    }
                })
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
        OkHttpUtils.initClient(okHttpClient);
        /******************网络请求设置end ***********************************/


//        //字体设置
//        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/kt.ttf");
//        try {
//            Field field = Typeface.class.getDeclaredField("SERIF");
//            field.setAccessible(true);
//            field.set(null, tf);
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }




    }







    public static synchronized MyApplication getInstance() {
        return mInstance;
    }


}
