package com.smartonet.project.ui.main.fragement.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.smartonet.project.R;
import com.smartonet.project.core.camera.scanner.ScannerActivity;
import com.smartonet.project.core.util.AndroidCommonUtils;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class QrcodeFragment extends Fragment {
    private View view;//fragment主界面
    private Button nextBtn;//开启下一次扫描
    private TextView contentView;//fragment主界面

    //加载哪个界面
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_drawer_layout_qrcode, container, false);
        return view;
    }

    //初始化界面
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        contentView = view.findViewById(R.id.fragment_qrcode_content);
        nextBtn = view.findViewById(R.id.btn_next_scanner);
        //开启扫描
        Intent intent = new Intent(getContext(), ScannerActivity.class);
        startActivityForResult(intent, 1);
        setFunction();
    }

    private void setFunction(){
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开启扫描
                Intent intent = new Intent(getContext(), ScannerActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    /**
     * 扫描二维码
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        // 扫描二维码/条码回传
        if(requestCode == 1 ) {
            if (data != null) {
                String content = data.getStringExtra("string");
                AndroidCommonUtils.makeSnackbar(view,content);
                contentView.setText(content);
            }
        }
    }
}
