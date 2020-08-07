package com.smartonet.project.ui.main.fragement.listview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.smartonet.project.R;
import com.smartonet.project.ui.main.fragement.listview.adapter.ExpandableListAdapter;
import com.smartonet.project.ui.main.fragement.listview.adapter.ListAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class ListViewFragment extends Fragment {
    private View view;//fragment主界面

    //加载哪个界面
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_drawer_layout_list_view, container, false);
        return view;
    }

    //初始化界面
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        //带分组的下拉列表
        expandableList();
        //普通下拉列表
        list();
    }

    //分组下拉列表
    private void expandableList(){
        ExpandableListView expandableListView = view.findViewById(R.id.expandable_list);
        List<List<String>> data = new ArrayList<>();
        for (int i = 0; i < 10 ; i++) {
            List<String> item =  new ArrayList<>();
            for (int k = 0; k < 5; k++) {
                item.add("下拉列表"+i);
            }
            data.add(item);
        }
        ExpandableListAdapter adapter = new ExpandableListAdapter(data,getContext());
        expandableListView.setAdapter(adapter);
    }

    //普通下拉列表
    private void list(){
        ListView listView = view.findViewById(R.id.list_view);
            List<String> item =  new ArrayList<>();
            for (int k = 0; k < 15; k++) {
                item.add("普通列表"+k);
            }
        ListAdapter adapter = new ListAdapter(getActivity(),item);
        listView.setAdapter(adapter);
    }
}
