package com.example.group.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.group.R;

/**
 * TabThirdFragment
 *
 * @author lao
 * @date 2019/4/29
 */

public class TabThirdFragment extends Fragment {
    private static final String TAG = "TabThirdFragment";
    protected Context mContext;
    protected View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        mView = inflater.inflate(R.layout.fragment_tab_third, container, false);
        String desc = String.format("我是%s页面，来自%s","页面", getArguments().getString("tag"));
        TextView tv_third = mView.findViewById(R.id.tv_third);
        tv_third.setText(desc);
        return mView;
    }

}
