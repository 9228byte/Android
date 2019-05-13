package com.example.group;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.group.fragment.TabFirstFragment;
import com.example.group.fragment.TabSecondFragment;
import com.example.group.fragment.TabThirdFragment;

public class TabFragmentActivity extends AppCompatActivity {
    private static final String TAG = "TabFragmentActivity";
    private FragmentTabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_fragment);
        Bundle bundle = new Bundle();
        bundle.putString("tag", TAG);
        tabHost = findViewById(android.R.id.tabhost);
        //把实际内容安装到碎片也框架
        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        //往标签栏添加第一个便签
        tabHost.addTab(getTabView(R.string.menu_first, R.drawable.tab_first_selector), TabFirstFragment.class, bundle);
        tabHost.addTab(getTabView(R.string.menu_second, R.drawable.tab_second_selector), TabSecondFragment.class, bundle);
        tabHost.addTab(getTabView(R.string.menu_third, R.drawable.tab_third_selector), TabThirdFragment.class, bundle);
        //不显示个便签栏之间的分隔线
        tabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        Log.d(TAG, "onCreate: ");
    }


    //根据字符串和图片资源编号，获得对应的而标签规格
    private TabHost.TabSpec getTabView(int textId, int imageId) {
        //根据资源编号获得字符串对象
        String text = getResources().getString(textId);
        //根据资源编号获得图形对象
        Drawable drawable = getResources().getDrawable(imageId);
        //设置图形的四周边界.这是必须设置图片大小否则无法心事图标
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        //根据布局人间构建标签对象
        View item_tabbar = getLayoutInflater().inflate(R.layout.item_tabbar, null);
        TextView tv_item = item_tabbar.findViewById(R.id.tv_item_tabbar);
        tv_item.setText(text);
        //在文字上方显示标签的图标
        tv_item.setCompoundDrawables(null, drawable, null, null);
        //生成并返回该标签按钮对应的标签规格
        return tabHost.newTabSpec(text).setIndicator(item_tabbar);
    }
}
