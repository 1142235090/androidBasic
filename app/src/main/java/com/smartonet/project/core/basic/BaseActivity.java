package com.smartonet.project.core.basic;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.smartonet.project.core.ioc.ActivityAutowaireUtils;
import com.smartonet.project.ui.main.activity.MainAvtivity;

import androidx.appcompat.app.AppCompatActivity;


/**
 * 使用了自定义的ioc，只限于xml界面上的，自动生成的界面不可以
 * 主要是成员变量View视图 例子：
 * @AutowaireView(R.id.btn1)
 * private Button mBtn1;
 * 写在方法上的点击事件：
 * @OnClick(value = {R.id.btn1})
 * 写在方法上的长摁事件
 * @OnLongClick(value = {R.id.btn1})
 */
public abstract class BaseActivity extends AppCompatActivity {

    //判断是否被回收
//    public static int flag = -1;
    public static Context thisContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (flag == -1) {//flag为-1说明程序被杀掉
//            protectApp();
//            return;
//        }
        ActivityAutowaireUtils.inject(this);
        onCreateView();
        initView();
        setFunction();
        thisContext=this;
        if(AppApplication.isDebug) {
            ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            //最大分配内存
            int memory = activityManager.getMemoryClass();
            System.out.println("memory: " + memory);
            //最大分配内存获取方法2
            float maxMemory = (float) (Runtime.getRuntime().maxMemory() * 1.0 / (1024 * 1024));
            //当前分配的总内存
            float totalMemory = (float) (Runtime.getRuntime().totalMemory() * 1.0 / (1024 * 1024));
            //剩余内存
            float freeMemory = (float) (Runtime.getRuntime().freeMemory() * 1.0 / (1024 * 1024));
            Log.w("最大内存", maxMemory + "");
            Log.w("当前分配内存内存", totalMemory + "");
            Log.w("剩余内存", freeMemory + "");
            Log.w("统计",getCurrentMeminfo());
        }
    }

    //重启app
    protected void protectApp() {
        Intent intent = new Intent(this, MainAvtivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//清空栈里所有activty
        startActivity(intent);
        finish();
    }

    // import android.annotation.TargetApi;
// import android.app.ActivityManager;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private String getCurrentMeminfo() {
        StringBuffer sb = new StringBuffer();
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        sb.append("剩余内存："+(mi.availMem/1024/1024)+"MB\n");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            sb.append("总内存： " + (mi.totalMem/1024/1024) + "MB\n");
        }
        sb.append("内存是否过低：" + mi.lowMemory);
        return sb.toString();
    }

    //设置界面
    public abstract void onCreateView();
    //初始化界面
    public abstract void initView();
    //设置事件
    public abstract void setFunction();
}
