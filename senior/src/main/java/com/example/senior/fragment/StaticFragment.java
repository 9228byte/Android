package com.example.senior.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.senior.R;



/**
 * Created by lao on 2019/4/7
 */

public class StaticFragment extends Fragment implements OnClickListener {
    private static final String TAG = "StaticFragment";
    protected View mView;
    protected Context mContext;

    //创建碎片布局
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        mContext = getActivity();       //获取活动页面的上下文
        //从布局文件中生成视图对象
        mView = inflater.inflate(R.layout.fragment_static, container, false);
        TextView tv_adv = mView.findViewById(R.id.tv_adv);
        ImageView iv_adv = mView.findViewById(R.id.iv_adv);
        tv_adv.setOnClickListener(this);
        iv_adv.setOnClickListener(this);
        Log.d(TAG, "onCreateView: ");
        return mView;
    }

    @Override
    public void onAttach(Context context) {     //把碎片贴到页面上
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {//页面创建
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {        //在活动页面创建之后
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
    }

    @Override
    public void onStart() {     //页面启动
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onResume() {    //页面恢复
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onPause() {     //页面暂停
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onStop() {      //页面停止
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onDestroyView() {       //销毁碎片视图
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
    }

    @Override
    public void onDestroy() {       //页面销毁
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onDetach() {        //把碎片从页面上撕下来
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_adv) {
            Toast.makeText(mContext, "您点击了广告文本", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.iv_adv) {
            Toast.makeText(mContext, "您点击了广告图片", Toast.LENGTH_SHORT).show();
        }



    }
}
