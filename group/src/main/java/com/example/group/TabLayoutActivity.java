package com.example.group;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.group.adapter.GoodsPagerAdapter;
import com.example.group.util.DateUtil;
import com.example.group.util.MenuUtil;

import java.util.ArrayList;

public class TabLayoutActivity extends AppCompatActivity implements OnTabSelectedListener {
    private static final String TAG = "TabLayoutActivity";
    private ViewPager vp_content;
    private TabLayout tab_title;      //标签布局对象
    private ArrayList<String> mTitleArray = new ArrayList<String>();    //标题文字队列

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        Toolbar tl_head = findViewById(R.id.tl_head);
        setSupportActionBar(tl_head);
        mTitleArray.add("商品");
        mTitleArray.add("详情");
        initTabLayout();
        initTabViewPager();
    }

    private void initTabLayout(){
        tab_title =  findViewById(R.id.tab_title);
        tab_title.addTab(tab_title.newTab().setText(mTitleArray.get(0)));
        tab_title.addTab(tab_title.newTab().setText(mTitleArray.get(1)));
        tab_title.addOnTabSelectedListener(this);
    }

    private void initTabViewPager() {
        vp_content = findViewById(R.id.vp_content);
        GoodsPagerAdapter adapter = new GoodsPagerAdapter(getSupportFragmentManager(), mTitleArray);
        vp_content.setAdapter(adapter);
        vp_content.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tab_title.getTabAt(position).select();
            }
        });
    }

    //在标签选选中时触发
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        //让vp_content显示指定位置的页面
        vp_content.setCurrentItem(tab.getPosition());
    }

   //在标签取消选中时触发
    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    //在标签重复选中时触发
    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        //显示菜单项最左侧的图标
        MenuUtil.setOverflowIconVisible(featureId, menu);
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //构建菜单布局
        getMenuInflater().inflate(R.menu.menu_overflow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.menu_refresh) {
            Toast.makeText(this, "当前刷新时间：" + DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"), Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_about) {
            Toast.makeText(this, "这是一个便签布局的演示demo", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_quit) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
