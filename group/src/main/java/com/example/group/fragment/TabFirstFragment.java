package com.example.group.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.group.R;


/**
 * TabFirstFragment
 *
 * @author lao
 * @date 2019/4/29
 */

public class TabFirstFragment extends Fragment {
    private static final String TAG = "TabFirstActivity";
    protected View mView;
    protected Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();   //获取活动也上下文
        //根据布局文件构建生成视图对象
        mView = inflater.inflate(R.layout.fragment_tab_first, container, false);
        String desc = String.format("我是%s页面，来自%s","页面", getArguments().getString("tag"));
        TextView tv_first = mView.findViewById(R.id.tv_first);
        tv_first.setText(desc);
        return mView;
    }
}
