package com.example.group;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.group.adapter.ClassPagerAdater;
import com.example.group.util.DateUtil;
import com.example.group.util.MenuUtil;

import java.util.ArrayList;

/**
 * DepartmentClassActivity
 *
 * @author lao
 * @date 2019/5/13
 */

public class DepartmentClassActivity extends AppCompatActivity {
    private static final String TAG = "DepartmentClassActivity";
    private ViewPager vp_content;
    private TabLayout tab_title;
    private ArrayList<String> mTitleArray = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_class);
        Toolbar tl_head = findViewById(R.id.tl_head);
        setSupportActionBar(tl_head);
        mTitleArray.add("服装");
        mTitleArray.add("电器");
        vp_content = findViewById(R.id.vp_content);
        initTabLayout();
        initTabViewPager();
    }

    //初始化标签布局
    private void initTabLayout() {
        tab_title = findViewById(R.id.tab_title);
        tab_title.addTab(tab_title.newTab().setText(mTitleArray.get(0)));
        tab_title.addTab(tab_title.newTab().setText(mTitleArray.get(1)));
        //跟vp_content翻页绑定
        tab_title.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vp_content));
    }

    private void initTabViewPager() {
        ClassPagerAdater adater = new ClassPagerAdater(getSupportFragmentManager(), mTitleArray);
        vp_content.setAdapter(adater);
        vp_content.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tab_title.getTabAt(position).select();
            }
        });
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        //显示菜单项序左侧的图标
        MenuUtil.setOverflowIconVisible(featureId, menu);
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //从menu.xml中构建菜单布局
        getMenuInflater().inflate(R.menu.menu_overflow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.menu_refresh) {
            Toast.makeText(this, "当前刷新时间：" + DateUtil.getNowDateTime("yyyy-MM--dd HH:mm:ss"), Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_about) {
            Toast.makeText(this, "这是个分类首页", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_quit) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
