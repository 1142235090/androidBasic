package com.smartonet.project.ui.main.fragement.exit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smartonet.project.R;

import androidx.fragment.app.Fragment;


public class ExitFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_drawer_layout_list_view, container, false);
    }

    public ExitFragment() {
    }
}
