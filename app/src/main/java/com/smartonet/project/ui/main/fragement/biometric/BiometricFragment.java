package com.smartonet.project.ui.main.fragement.biometric;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.provider.Settings;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.smartonet.project.R;
import com.smartonet.project.ui.main.fragement.biometric.dialog.FingerprintDialogFragment;

import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class BiometricFragment extends Fragment {
    private View view;//fragment主界面
    private BiometricPrompt mBiometricPrompt;
    private CancellationSignal mCancellationSignal;
    private BiometricPrompt.AuthenticationCallback mAuthenticationCallback;
    private KeyStore keyStore;;

    private static final String TAG = "BiometricFragment";
    private static final String DEFAULT_KEY_NAME = "default_key";

    //加载哪个界面
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_drawer_layout_biometric, container, false);
        return view;
    }

    //初始化界面
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        supportFingerprint();
    }

    public void supportFingerprint() {
        if (Build.VERSION.SDK_INT < 23) {
            Toast.makeText(getContext(), "您的系统版本过低，不支持指纹功能", Toast.LENGTH_SHORT).show();
        }else if(Build.VERSION.SDK_INT >=28){
            android9();
        } else {
            KeyguardManager keyguardManager = getContext().getSystemService(KeyguardManager.class);
            FingerprintManager fingerprintManager = getContext().getSystemService(FingerprintManager.class);
            if (!fingerprintManager.isHardwareDetected()) {
                Toast.makeText(getContext(), "您的手机不支持指纹功能", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (!keyguardManager.isKeyguardSecure()) {
                Toast.makeText(getContext(), "您还未设置锁屏，请先设置锁屏并添加一个指纹", Toast.LENGTH_SHORT).show();
                Intent intent =  new Intent(Settings.ACTION_SECURITY_SETTINGS);
                startActivity(intent);
                return;
            }
            else if (!fingerprintManager.hasEnrolledFingerprints()) {
                Toast.makeText(getContext(), "您至少需要在系统设置中添加一个指纹", Toast.LENGTH_SHORT).show();
                Intent intent =  new Intent(Settings.ACTION_SECURITY_SETTINGS);
                startActivity(intent);
                return;
            }
            initKey();
            initCipher();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void android9(){
        if(getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)){//指纹
            android9FingerorintManager();
            return;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void android9FingerorintManager(){
        mBiometricPrompt = new BiometricPrompt.Builder(getContext())
                .setTitle("指纹验证")
                .setDescription("描述")
                .setNegativeButton("取消", getContext().getMainExecutor(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.i(TAG, "Cancel button clicked");
                    }
                })
                .build();
        mCancellationSignal = new CancellationSignal();
        mCancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() {
            @Override
            public void onCancel() {
                //指纹点击返回
                Log.i(TAG, "Canceled");
            }
        });
        mAuthenticationCallback = new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                //指纹验证错误
                Log.i(TAG, "onAuthenticationError " + errString);
                Toast.makeText(getContext(), "指纹验证错误", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                //指纹验证成功
                Log.i(TAG, "onAuthenticationSucceeded " + result.toString());
                Toast.makeText(getContext(), "指纹验证成功", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                //指纹验证不正确
                Log.i(TAG, "onAuthenticationFailed ");
                Toast.makeText(getContext(), "指纹验证错误", Toast.LENGTH_SHORT).show();
            }
        };
        mBiometricPrompt.authenticate(mCancellationSignal, getContext().getMainExecutor(), mAuthenticationCallback);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(DEFAULT_KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);
            keyGenerator.init(builder.build());
            keyGenerator.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCipher() {
        try {
            SecretKey key = (SecretKey) keyStore.getKey(DEFAULT_KEY_NAME, null);
            Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/"
                    + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            showFingerPrintDialog(cipher);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressLint("RestrictedApi")
    private void showFingerPrintDialog(Cipher cipher) {
        FingerprintDialogFragment fragment = new FingerprintDialogFragment();
        fragment.setCipher(cipher);
        //设置指纹识别错误回调
        fragment.setCancelClick(new FingerprintDialogFragment.setCancelClick() {
            @Override
            public void back(DialogFragment dialog) {
                dialog.dismiss();
                getActivity().onBackPressed();
            }
        });
        //设置指纹识别通过回调
        fragment.setSuccess(new FingerprintDialogFragment.setSuccess() {
            @Override
            public void back(DialogFragment dialog) {
                dialog.dismiss();
                view.findViewById(R.id.biometric_tips).setVisibility(View.VISIBLE);
            }
        });
        FragmentManager a = getFragmentManager();
        fragment.show(a, "fingerprint");
    }
}
