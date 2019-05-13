package com.example.group;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TabHost;

/**
 * TabHostActivity
 *
 * @author lao
 * @date 2019/4/27
 */

public class TabHostActivity extends TabActivity implements OnClickListener {
    private static final String TAG = "TabHostActivity";
    private Bundle mBundle = new Bundle();
    private TabHost tab_host;
    private LinearLayout ll_first, ll_second, ll_third;
    private String FIRST_TAG = "first";
    private String SECOND_TAG = "second";
    private String THIRD_TAG = "third";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_host);
        mBundle.putString("tag", TAG);
        ll_first = findViewById(R.id.ll_first);
        ll_second = findViewById(R.id.ll_second);
        ll_third = findViewById(R.id.ll_third);
        ll_first.setOnClickListener(this);
        ll_second.setOnClickListener(this);
        ll_third.setOnClickListener(this);
        //获取系统自带标签栏,起始就是id为android：id/tabhost
        tab_host = getTabHost();
        tab_host.addTab(getNewTab(FIRST_TAG, R.string.menu_first, R.drawable.tab_first_selector, TabFirstActivity.class));
        tab_host.addTab(getNewTab(SECOND_TAG, R.string.menu_second, R.drawable.tab_second_selector, TabSecondActivity.class));
        tab_host.addTab(getNewTab(THIRD_TAG, R.string.menu_third, R.drawable.tab_third_selector, TabThirdActivity.class));
        changeContainerView(ll_first);      //默认显示第一个视图的东西
    }

    private TabHost.TabSpec getNewTab(String spec, int label, int icon, Class<?> cls) {
        Intent intent = new Intent(this, cls).putExtras(mBundle);
        //生成并返回新的标签规格（包括内容意图，标签文字和标签图标
        return tab_host.newTabSpec(spec).setContent(intent).setIndicator(getString(label), getResources().getDrawable(icon));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_first || v.getId() == R.id.ll_second || v.getId() == R.id.ll_third) {
            changeContainerView(v);
        }
    }

    //内容视图改变为展示指定的视图
    private void changeContainerView(View v) {
        ll_first.setSelected(false);
        ll_second.setSelected(false);
        ll_third.setSelected(false);
        v.setSelected(true);
        if (v == ll_first) {
            tab_host.setCurrentTabByTag(FIRST_TAG);
        } else if (v == ll_second) {
            tab_host.setCurrentTabByTag(SECOND_TAG);
        } else if (v == ll_third) {
            tab_host.setCurrentTabByTag(THIRD_TAG);
        }
    }

}
