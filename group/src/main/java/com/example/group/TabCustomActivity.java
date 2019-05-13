package com.example.group;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group.adapter.GoodsPagerAdapter;
import com.example.group.util.DateUtil;
import com.example.group.util.MenuUtil;

import java.util.ArrayList;

/**
 * TabCustomActivity
 *
 * @author lao
 * @date 2019/5/4
 */

public class TabCustomActivity extends AppCompatActivity {
    private static final String TAG = "TabCustomActivity";
    private ViewPager vp_content;
    private TabLayout tab_title;
    private ArrayList<String> mTitleArray = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_custom);
        Toolbar tl_head = findViewById(R.id.tl_head);
        setSupportActionBar(tl_head);
        mTitleArray.add("商品");
        mTitleArray.add("详情");
        vp_content = findViewById(R.id.vp_content);
        initTabLayout();
        initViewPager();
    }

    private void initTabLayout() {
        tab_title = findViewById(R.id.tab_title);
        //给tab_title添加指定布局标签
        tab_title.addTab(tab_title.newTab().setCustomView(R.layout.item_toolbar1));
        TextView tv_toolbar1 = findViewById(R.id.tv_toolbar1);
        tv_toolbar1.setText(mTitleArray.get(0));
        tab_title.addTab(tab_title.newTab().setCustomView(R.layout.item_toolbar2));
        TextView tv_toolbar2 = findViewById(R.id.tv_toolbar2);
        tv_toolbar2.setText(mTitleArray.get(1));
        //给tab_title添加标签选中监听器，该监听器默认绑定里翻页视图vp_content
        tab_title.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vp_content));
    }

    private void initViewPager() {
        GoodsPagerAdapter adapter = new GoodsPagerAdapter(getSupportFragmentManager(), mTitleArray);
        vp_content.setAdapter(adapter);
        vp_content.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tab_title.getTabAt(position).select();
            }
        });
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        MenuUtil.setOverflowIconVisible(featureId, menu);
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_overflow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.menu_refresh) {
            Toast.makeText(this, "当前刷新时间:" + DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"), Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_about) {
            Toast.makeText(this, "这个是标签布局的演示demo", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_quit) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
