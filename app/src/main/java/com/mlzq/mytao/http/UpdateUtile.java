package com.mlzq.mytao.http;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.mlzq.nubiolib.Dialog.ToastUtils;
import com.mlzq.nubiolib.bean.AppBean;
import com.mlzq.nubiolib.http.DownloadUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import okhttp3.Call;

/**
 * Created by Dev on 2017/11/20.
 */


public class UpdateUtile {
    public static void getServiceVersion(final Context context){
        OkHttpUtils.get().url(MyUrl.update).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                AppBean bean = new GsonBuilder().serializeNulls().create().fromJson(response, AppBean.class);
                try {
                    int localcode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
                    int code=Integer.parseInt(bean.getVersion());
                    if (code>localcode){
                        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Mytao");
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        if (!isdownApp) {
                            downFile(context, bean.getInstall_url());
                        }
                    }


                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }


            }
        });


    }
    public static int PROGRESS_NUMBER = 0;
    static boolean isdownApp=false;//是否正在下载app
    public static void downFile(final Context context, final String url) {
        ToastUtils.showToast(context,"系统正在升级中... 开始下载");//下载进度发送广播
        DownloadUtil.getInstance().download(url, Environment.getExternalStorageDirectory().getAbsolutePath() + "/Mytao", new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(String path) {
                openFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Mytao/MyTao.apk"),context);
                isdownApp=false;
            }

            @Override
            public void onDownloading(int progress) {
                isdownApp=true;
                if (progress == PROGRESS_NUMBER) {
                    return;
                }
                L.e("下载进度: " + progress + "%");
                PROGRESS_NUMBER = progress;
                if (PROGRESS_NUMBER % 3 == 0)
                    ToastUtils.showToast(context,"升级中请勿退出app... 当前进度:"+ PROGRESS_NUMBER);//下载进度发送广播

            }

            @Override
            public void onDownloadFailed() {
                isdownApp=false;
                ToastUtils.showToast(context,"升级失败:");//下载进度发送广播
            }
        });




    }
    public static void openFile(File file,Context context) {
        Log.e("进入安装","进入安装");
        Intent intent = new Intent();
        intent.addFlags(268435456);
        intent.setAction("android.intent.action.VIEW");
        String type = getMIMEType(file);
        intent.setDataAndType(Uri.fromFile(file), type);
        try {
            context.startActivity(intent);
        } catch (Exception var5) {
            var5.printStackTrace();
            Toast.makeText(context, "没有找到打开此类文件的程序", Toast.LENGTH_SHORT).show();
        }finally {
            UpdateUtile.deleteDir(file);
        }

    }
    public static String getMIMEType(File var0) {
        String var1 = "";
        String var2 = var0.getName();
        String var3 = var2.substring(var2.lastIndexOf(".") + 1, var2.length()).toLowerCase();
        var1 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(var3);
        return var1;
    }
    /**删除文件夹及其目录下的文件**/
    public  static boolean deleteDir( File dir){
        Log.e("文件夹","删除"+dir+"");
        boolean isdel=false;
        if (dir==null||!dir.exists()||!dir.isDirectory()){
            return false;
        }
        for (File file:dir.listFiles()) {
            if (file.isFile()){
                file.delete();
            }else if (file.isDirectory()){
                  deleteDir(file);
            }
        }
        isdel=dir.delete();
        Log.e("文件夹","删除成功"+dir+"");
        return isdel;

    }
}
