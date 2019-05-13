package com.example.group;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * SwipeRefreshActivity
 *
 * @author lao
 * @date 2019/5/11
 */

public class SwipeRefreshActivity extends AppCompatActivity implements OnRefreshListener {
    private TextView tv_simple;
    private SwipeRefreshLayout srl_simple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_refresh);
        tv_simple = findViewById(R.id.tv_simple);
        srl_simple = findViewById(R.id.srl_simple);
        //设置下拉刷新监听器
        srl_simple.setOnRefreshListener(this);
        //设置下拉刷新布局的进度圆圈颜色
        srl_simple.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
    }

    @Override
    public void onRefresh() {
        tv_simple.setText("正在刷新");
        mHandler.postDelayed(mRefresh, 2000);
    }

    private Handler mHandler = new Handler();

    private Runnable mRefresh = new Runnable() {
        @Override
        public void run() {
            tv_simple.setText("刷新完成");
            srl_simple.setRefreshing(false);
        }
    };
}
