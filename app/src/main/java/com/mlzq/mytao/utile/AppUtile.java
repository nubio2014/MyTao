package com.mlzq.mytao.utile;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

/**
 * Created by Dev on 2018/10/17.
 * desc :
 */

public class AppUtile {
    public static boolean isAppInstalled(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void runApp(String packageName,Context context){
        Intent intent = new Intent();
        PackageManager packageManager =context.getPackageManager();
        intent = packageManager.getLaunchIntentForPackage(packageName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TOP) ;
        context.startActivity(intent);

    }

}
