package com.smartonet.project.ui.main.fragement.listview.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.smartonet.project.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * Created by hanzh on 2018/3/8.
 */

public class ListAdapter extends ArrayAdapter {
    private int resourceId;
    private List<String> mData = new ArrayList();
    private Activity mActivity;

    @SuppressLint("HandlerLeak")
    public ListAdapter(@NonNull Activity activity, @NonNull List objects) {
        super(activity, R.layout.item_list_view, objects);
        mActivity=activity;
        mData = objects;
        this.resourceId=R.layout.item_list_view;
    }

    @Override
    public int getCount() {
        if(mData==null||mData.size()<1){
            return 0;
        }else{
            return mData.size();
        }
    }

    @Override
    public View getView(final int position, View convertView , ViewGroup parent){
        //根据R资源id获取到界面显示控件
        View view  = LayoutInflater.from(getContext()).inflate(resourceId, null);
        //获取控件
        TextView contentView  = view.findViewById(R.id.item_list_view_text);
        contentView.setText(mData.get(position));
        //返回界面
        return view;
    }

    @Override
    public void clear(){
        mData.clear();
        notifyDataSetChanged();
    }
}
