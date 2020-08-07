package com.smartonet.project.ui.main.fragement.listview.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartonet.project.R;
import java.util.List;


/**
 * Created by hanzh on 2017/12/5.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<List<String>> groupDate;

    @SuppressLint("HandlerLeak")
    public ExpandableListAdapter(List data, Context mContext) {
        this.mContext = mContext;
        this.groupDate = data;
    }

    @Override
    public int getGroupCount() {
        return groupDate.size();
//        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groupDate.get(groupPosition).size();
    }

    @Override
    public List<String> getGroup(int groupPosition) {
        return groupDate.get(groupPosition);
    }

    @Override
    public String getChild(int groupPosition, int childPosition) {
        return groupDate.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //取得用于显示给定分组的视图. 这个方法仅返回分组的视图对象
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_expandable_list_group, parent, false);
        ImageView closeView = convertView.findViewById(R.id.close);
        ImageView openView = convertView.findViewById(R.id.open);
        if(isExpanded){
            closeView.setVisibility(View.VISIBLE);
            openView.setVisibility(View.GONE);
        }else{
            closeView.setVisibility(View.GONE);
            openView.setVisibility(View.VISIBLE);
        }
        TextView textView = convertView.findViewById(R.id.item_expandable_list_group_text);
        textView.setText("第"+groupPosition+"个分组列表");
        return convertView;
    }

    //组织下拉子菜单的数据，并显示
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_expandable_list, parent, false);
        }
        ((TextView)convertView.findViewById(R.id.item_expandable_list_text)).setText(groupDate.get(groupPosition).get(childPosition));
        return convertView;
    }

    //设置子列表是否可选中
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    //对外暴露的方法，用于更新数据
    public void clear(){
        groupDate.clear();
        notifyDataSetChanged();
    }
}
