package com.smartonet.project.core.camera;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class CameraUtils {
    /** 获取权限*/
    public static Boolean getPermission(Context mContext,Activity mActivity) {
        if (Build.VERSION.SDK_INT>22){
            if (ContextCompat.checkSelfPermission(mContext,
                    Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                //先判断有没有权限 ，没有就在这里进行权限的申请
                ActivityCompat.requestPermissions(mActivity,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
                return false;
            }
        }
        return true;
    }
}
