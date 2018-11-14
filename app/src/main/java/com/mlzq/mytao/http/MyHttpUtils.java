package com.mlzq.mytao.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.webkit.WebSettings;
import android.widget.Toast;

import com.mlzq.mytao.Pamera;
import com.mlzq.mytao.utile.TimeUtils;
import com.orhanobut.logger.BuildConfig;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.TaobaoResponse;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;


/**
 * Created by Dev on 2017/9/14.
 */

public class MyHttpUtils {
    public final static String URL="http://gw.api.taobao.com/router/rest";
    //Context context;
   // String url;
   // String params;




    public static void post( String url, Map<String, String> params, Callback callback){
        //  String content = null;// new Gson.toJson(new PhoneBean(phone,type));
        try {
            init();
            L.d(params+"");
            Map<String, String> object = new HashMap<String, String>();
            object.put("Content-Type",
                    "application/json");
            object.put("charset", "utf-8");
            OkHttpUtils.post().url(url).headers(object).params(params).build().execute(callback);
        }catch (Exception e){
            e.printStackTrace();
            Log.e("网络请求错误",e+"");
        }
    }
    public static void doPost(String url, Map<String, String> params, Callback callback){
        try {
            init();

            Map<String, String> object = new HashMap<String, String>();
            object.put("Content-Type",
                    "application/json");
            object.put("charset", "utf-8");

            params.put("app_key", Pamera.APPKEY);
            params.put("app_secret",Pamera.APP_Secret);
            params.put("v","2.0");
            params.put("timestamp", TimeUtils.getSystemMonthTOString());
            params.put("format","json");
            params.put("sign_method","md5");
            params.put("platform","2");
            params.put("sign",Sign.signTopRequest(params,Pamera.APP_Secret,"md5"));
            Log.e("发送的参数",params+"，头部："+object);
            OkHttpUtils.post().url( url).headers(object).params(params).build().execute(callback);
        }catch (Exception e){
            e.printStackTrace();
            Log.e("网络请求错误",e+"");
//            Intent it=new Intent(context, LoginActivity.class);
//            context.startActivity(it);
            //  context
        }
    }


//    public static void doPost( String url, Map<String, String> params, Callback callback){
//        try {
//            init();
//
//            Map<String, String> object = new HashMap<String, String>();
//            object.put("Content-Type",
//                    "application/json");
//            object.put("charset", "utf-8");
//            object.put("version", "1.0");
//            object.put("fromurl", "android");
//            Log.e("发送的参数",params+"，头部："+object);
//            OkHttpUtils.post().url( url).headers(object).params(params).build().execute(callback);
//        }catch (Exception e){
//            e.printStackTrace();
//            Log.e("网络请求错误",e+"");
////            Intent it=new Intent(context, LoginActivity.class);
////            context.startActivity(it);
//          //  context
//        }
//    }
    public static void doPostToken(String url, Map<String, String> params, Callback callback){
        try {
            init();
            Map<String, String> object = new HashMap<String, String>();
            object.put("Content-Type",
                    "application/json");
            object.put("charset", "utf-8");
            object.put("version", "1.0");
            object.put("fromurl", "android");
            Log.e("发送的参数",params+"，头部："+object);

            L.d(params+"");
            OkHttpUtils.post().url( url).headers(object).params(params).build().execute(callback);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private static void init(){
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
    }

    public static void postFile(Context context, int type, File file, String url, Callback callback)
    {
       // File file = new File(files);

        if (!file.exists())
        {
            Toast.makeText(context, "文件不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String ,String> map=new HashMap<>();
        map.put("type",type+"");
        OkHttpUtils.post().params(map) .url(url).addHeader("fromurl", "android").addHeader("version","1.0")
                .addFile("mFile",file.getName(),file)
                .build()
                .execute(callback);
    }

    /**
     * 多文件上传
     * @param context
     * @param files
     * @param url
     * @param callback
     */
    public static void multiFileUpload(Context context, Map<String, String> params, String name, Map<String,File> files, String url, Callback callback)
    {
        OkHttpUtils.post().files(name,files)
                .url(url)
                .params(params)//
                .build()//
                .execute(callback);
    }
//    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
//    public static void doPostFile(Context context, String url, Map<String, String> map, List<ImageBean> files, List<ImageBean> files2, Callback callback){
//        try {
//            Log.e("发送的参数",map+"");
//            Map<String, String> object = new HashMap<String, String>();
//            object.put("Content-Type",
//                    "application/json");
//            object.put("charset", "utf-8");
//            object.put("token", User.TOKEN);
//
//            File sdcache = context.getExternalCacheDir();
//            int cacheSize = 10 * 1024 * 1024;
//            //设置超时时间及缓存
//            OkHttpClient.Builder builder = new OkHttpClient.Builder()
//                    .connectTimeout(15, TimeUnit.SECONDS)
//                    .writeTimeout(20, TimeUnit.SECONDS)
//                    .readTimeout(20, TimeUnit.SECONDS)
//                    .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
//            OkHttpClient mOkHttpClient = builder.build();
//            MultipartBody.Builder mbody = new MultipartBody.Builder().setType(MultipartBody.FORM);
//            //  遍历map中所有参数到builder
//            if (map != null) {
//                for (String key : map.keySet()) {
//                    mbody.addFormDataPart(key, map.get(key));
//                }
//            }
//            List<File> fileList = new ArrayList<File>();
//            List<String> filekey = new ArrayList<String>();
//            File img;
//            if (files != null) {
//                for (int i = 0; i < files.size(); i++) {
//                    img = new File(files.get(i).getValuestr());
//                    fileList.add(img);
//                    filekey.add(files.get(i).getKeyname());
//                }
//                int i = 0;
//                for (File file : fileList) {
//                    if (file.exists()) {
//                        Log.e("imageName:", file.getName());//经过测试，此处的名称不能相同，如果相同，只能保存最后一个图片，不知道那些同名的大神是怎么成功保存图片的。
//                        mbody.addFormDataPart(filekey.get(i), file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file));
//                        i++;
//                    }
//                }
//            }
//            if (files2 != null) {
//                List<File> fileList2 = new ArrayList<File>();
//                List<String> filekey2 = new ArrayList<String>();
//                File img2;
//                for (int i = 0; i < files2.size(); i++) {
//                    img2 = new File(files2.get(i).getValuestr());
//                    fileList2.add(img2);
//                    filekey2.add(files2.get(i).getKeyname());
//                }
//                int i = 0;
//                for (File file2 : fileList2) {
//                    if (file2.exists()) {
//                        Log.e("imageName:", file2.getName());//经过测试，此处的名称不能相同，如果相同，只能保存最后一个图片，不知道那些同名的大神是怎么成功保存图片的。
//                        mbody.addFormDataPart(filekey2.get(i), file2.getName(), RequestBody.create(MEDIA_TYPE_PNG, file2));
//                        i++;
//                    }
//                }
//
//            }
//
//
//            RequestBody requestBody = mbody.build();
//            Log.e("身体部分",requestBody.toString());
//            Request request = new Request.Builder()
//                    .header("Authorization", "Client-ID " + "...").addHeader("token",User.TOKEN)
//                    .url(URL+url)
//                    .post(requestBody)
//                    .build();
//
//
//         //   OkHttpUtils.post().url(URL + url).headers(object).params(params).build().execute(callback);
//            mOkHttpClient.newCall(request).enqueue(callback);
//        }catch (Exception e){
//            e.printStackTrace();
//            Log.e("网络请求错误",e+"");
//        }
//    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
        } else {

            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static String getUserAgent(Context context) {
        String userAgent = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(context);
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }


    public static void getShop(TaobaoResponse req){
        TaobaoClient client=new DefaultTaobaoClient(URL,Pamera.APPKEY,Pamera.APP_Secret);

    }




}
