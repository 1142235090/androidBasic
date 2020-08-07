package com.smartonet.project.core.basic;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * Creat by hanzhao
 * on 2019/12/17
 **/
public class AppApplication  extends Application {
    private static Context sContext;
    public static Boolean isDebug;
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        ApplicationInfo info = sContext.getApplicationInfo();
        isDebug =  (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }
    public static Context getContext() {
        return sContext;
    }
}
