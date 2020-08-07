package com.smartonet.project.core.util;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.smartonet.project.R;

import java.lang.reflect.Field;
import java.util.Locale;

import androidx.appcompat.app.AlertDialog;

/**
 * Creat by hanzhao
 * on 2019/10/28
 **/
public class AndroidCommonUtils {

    /**
     * 给TextView增加下划线
     * @param params
     */
    public static void addBottomLine(TextView... params){
        for(TextView tvTest:params){
            tvTest.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
            tvTest.getPaint().setAntiAlias(true);//抗锯齿
        }
    }

    /**
     * 提示框
     * @param view
     * @param content
     */
    public static void makeSnackbar(View view, String content){
        Snackbar.make(view, content, Snackbar.LENGTH_LONG )
                .setAction("Action", null).show();
    }

    public static void makeDialog(Context context,String title, String message, String positiveButton,DialogInterface.OnClickListener positiveListener,String negativeButton,DialogInterface.OnClickListener negativeListener){
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButton, positiveListener)
                .setNegativeButton(negativeButton,negativeListener)
                .create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.click_blue));
        alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.click_blue));
    }


    /**
     * 代码设置光标颜色
     * @param editText 你使用的EditText
     * @param color    光标颜色
     */
    public static void setCursorDrawableColor(Context context,EditText editText, int color) {
        try {
            Field fCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");//获取这个字段
            fCursorDrawableRes.setAccessible(true);//代表这个字段、方法等等可以被访问
            int mCursorDrawableRes = fCursorDrawableRes.getInt(editText);

            Field fEditor = TextView.class.getDeclaredField("mEditor");
            fEditor.setAccessible(true);
            fEditor.set(editText, R.drawable.cursor);
            Object editor = fEditor.get(editText);

            Class<?> clazz = editor.getClass();
            Field fCursorDrawable = clazz.getDeclaredField("mCursorDrawable");
            fCursorDrawable.setAccessible(true);

            Drawable[] drawables = new Drawable[2];
            drawables[0] = context.getResources().getDrawable(mCursorDrawableRes);
            drawables[1] = context.getResources().getDrawable(mCursorDrawableRes);
            drawables[0].setColorFilter(color, PorterDuff.Mode.SRC_IN);//SRC_IN 上下层都显示。下层居上显示。
            drawables[1].setColorFilter(color, PorterDuff.Mode.SRC_IN);
            fCursorDrawable.set(editor, drawables);
        } catch (Throwable ignored) {
        }
    }


    private static TextToSpeech textToSpeech;
    /**
     * TTS人声朗读文字
     */
    public static void speakChinese(final Context context, final String str){
        textToSpeech = new TextToSpeech(context,new TextToSpeech.OnInitListener(){
            @Override
            public void onInit(int status) {
            }
        });
        textToSpeech.setLanguage(Locale.CHINESE);//设置成中文
        if (textToSpeech != null) {
            // 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
            textToSpeech.setPitch(1f);
            //设定语速 ，默认1.0正常语速
            textToSpeech.setSpeechRate(0.8f);
//            朗读，注意这里三个参数的added in API level 4   四个参数的added in API level 21
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Vibrator vibrator = (Vibrator)context.getSystemService(context.VIBRATOR_SERVICE);
                    long[] pattern = {100, 500, 100, 500, 100, 500, 100, 500, 100, 500, 100, 500};
                    vibrator.vibrate(pattern,-1);
                    //朗读，注意这里三个参数的added in API level 4   四个参数的added in API level 21
                    textToSpeech.speak(str, TextToSpeech.QUEUE_ADD, null);
                }
            }, 3000);
        }
    }

    /**
     * 停止TTS人声朗读文字
     */
    public static void shutdownSpeakChinese(){
        if(textToSpeech!=null){
            textToSpeech.stop(); // 不管是否正在朗读TTS都被打断
            textToSpeech.shutdown(); // 关闭，释放资源
            textToSpeech=null;
        }
    }

    /**
     * 根据资源图片名字获取图片
     * @param paramContext
     * @param paramString
     * @return
     */
    public static Bitmap readBitmap(Context paramContext, String paramString)
    {
        int i = paramContext.getResources().getIdentifier(paramString, "drawable", paramContext.getPackageName());
        BitmapFactory.Options localOptions = new BitmapFactory.Options();
        localOptions.inScaled = false;
        localOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap localBitmap1;
        try
        {
            Bitmap localBitmap2 = BitmapFactory.decodeResource(paramContext.getResources(), i, localOptions);
            localBitmap1 = localBitmap2;

        }
        catch (Exception localException)
        {
            localBitmap1 = BitmapFactory.decodeResource(paramContext.getResources(), i);
        }
        return localBitmap1;
    }
}
