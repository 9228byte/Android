package com.example.group;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group.util.DateUtil;
import com.example.group.util.MenuUtil;

/**
 * OverflowMenuActivity
 *
 * @author lao
 * @date 2019/5/2
 */

public class OverflowMenuActivity extends AppCompatActivity {
    private TextView tv_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overflow_menu);
        Toolbar tl_head = findViewById(R.id.tl_head);
        tl_head.setTitle("溢出菜单页面");
        setSupportActionBar(tl_head);
        tv_desc = findViewById(R.id.tv_desc);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        //显示菜单左侧的图标
        MenuUtil.setOverflowIconVisible(featureId, menu);
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //从menu_overflow.xml中构建布局
        getMenuInflater().inflate(R.menu.menu_overflow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.menu_refresh) {
            tv_desc.setText("当前刷新时间：" + DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
            return true;
        } else if (id == R.id.menu_about) {
            Toast.makeText(this, "这是工具栏的演示demo", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_quit) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
