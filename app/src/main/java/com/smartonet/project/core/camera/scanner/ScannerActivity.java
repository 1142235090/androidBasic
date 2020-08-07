package com.smartonet.project.core.camera.scanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartonet.project.R;
import com.smartonet.project.core.camera.CameraUtils;
import com.smartonet.project.core.util.CommonUtil;

import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;
import cn.bingoogolapple.qrcode.core.BarcodeType;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;


/**
 * Creat by zhaohan
 * 所以如果需要特殊使用条形码请在intent中填入all类型为boolean，true为开启，默认为关闭状态
 * 多次扫描时，传入keepScanner（Boolean类型），然后每当扫描完成一次之后，会将结果发送到ScannerActivity广播result中
 * 当需要再次扫描则向ScannerActivity广播startScanner发送任意字符串
 * 关闭则向ScannerActivity广播close发送任意字符串
 * 注意：result 2已经被占用不可使用2
 */
public class ScannerActivity extends AppCompatActivity implements QRCodeView.Delegate,View.OnClickListener {
    private static final String TAG = ScannerActivity.class.getSimpleName();

    private ZXingView mZXingView;
    private TextView tipsTv;
    private TextView flashLightTv;
    private ImageView flashLightIv;
    private Boolean isLight = false;
    private Boolean isRegister = false;
    private Boolean keppScanner;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        //判断是否有权限
        if(!CameraUtils.getPermission(this,this)){
            finish();
        }
        //隐藏标题栏
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        //绑定闪光灯事件
        flashLightTv = findViewById(R.id.flashLightTv);//用于更改文字
        flashLightIv = findViewById(R.id.flashLightIv);//用于更改图标
        tipsTv = findViewById(R.id.tv_scanner_tips);//用于显示提示信息
        findViewById(R.id.flashLightLayout).setOnClickListener(this);
        //绑定返回事件
        findViewById(R.id.backIv).setOnClickListener(this);
        mZXingView = findViewById(R.id.zxingview);
        mZXingView.setDelegate(this);
        setScanner();//设置识别的类型
        keppScanner = getIntent().getBooleanExtra("keepScanner",false);
        String tips = getIntent().getStringExtra("tips");
        if(!CommonUtil.stringIsEmpty(tips)){
            tipsTv.setText(tips);
            tipsTv.setVisibility(View.VISIBLE);
        }
        //注册广播
        IntentFilter filter = new IntentFilter("ScannerActivity");
        registerReceiver(broadcastReceiver, filter);
        isRegister=true;
    }

    private void setScanner(){
        Intent intent = getIntent();
        if(intent.getBooleanExtra("all",false)){
            mZXingView.changeToScanBarcodeStyle(); // 切换成扫描条码样式
            mZXingView.setType(BarcodeType.ALL, null); // 识别所有类型的码
            mZXingView.getScanBoxView().setOnlyDecodeScanBoxArea(false); // 识别整个屏幕中的码
        }else{
            mZXingView.changeToScanQRCodeStyle(); // 切换成扫描二维码样式
            mZXingView.getScanBoxView().setOnlyDecodeScanBoxArea(true); // 仅识别扫描框中的码
        }
        mZXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
    }

    @Override
    protected void onStart() {
        super.onStart();

        mZXingView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
//        mZXingView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT); // 打开前置摄像头开始预览，但是并未开始识别

        mZXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
    }

    @Override
    protected void onStop() {
        mZXingView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mZXingView.onDestroy(); // 销毁二维码扫描控件
        super.onDestroy();
    }

    //开启震动
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(100);
    }

    //返回的数据以string 写入到了intent中，取值时直接在intent中根据string为key取值图片中的string字符串
    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();
        if(!keppScanner){
            Intent intent = getIntent();
            intent.putExtra("string", result);
            setResult(RESULT_OK, intent);
            this.finish();
        }else{
            //更改提示
            tipsTv.setText("请准备扫描下一个器件！");
            //发送广播
            Intent receiver = new Intent("ScannerActivity");
            receiver.putExtra("result",result);
            sendBroadcast(receiver);
        }
    }

    @Override
    public void finish(){
        if(isRegister){
            unregisterReceiver(broadcastReceiver);
        }
        super.finish();
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
        // 这里是通过修改提示文案来展示环境是否过暗的状态，接入方也可以根据 isDark 的值来实现其他交互效果
        String tipText = mZXingView.getScanBoxView().getTipText();
        String ambientBrightnessTip = "\n环境过暗，请打开闪光灯";
        if (isDark) {
            if (!tipText.contains(ambientBrightnessTip)) {
                mZXingView.getScanBoxView().setTipText(tipText + ambientBrightnessTip);
            }
        } else {
            if (tipText.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip));
                mZXingView.getScanBoxView().setTipText(tipText);
            }
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.flashLightLayout:
                if(isLight){
                    mZXingView.openFlashlight(); // 打开闪光灯
                    flashLightIv.setImageResource(R.drawable.ic_open);
                    flashLightTv.setText("关闭闪光灯");
                    isLight=false;
                }else{
                    mZXingView.closeFlashlight(); // 关闭闪光灯
                    flashLightIv.setImageResource(R.drawable.ic_close);
                    flashLightTv.setText("打开闪光灯");
                    isLight=true;
                }
                break;
            case R.id.backIv:
                finish();
                break;
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, final Intent intent) {
            //开启再次扫描
            if (!CommonUtil.stringIsEmpty(intent.getStringExtra("startScanner"))) {
                Timer searchTimer = new Timer();
                searchTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        tipsTv.setText(intent.getStringExtra("startScanner"));
                        mZXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
                    }
                },1500);//延时1.5s执行
            }
            //关闭摄像头
            if (!CommonUtil.stringIsEmpty(intent.getStringExtra("close"))) {
                finish();
            }
        }
    };
}
