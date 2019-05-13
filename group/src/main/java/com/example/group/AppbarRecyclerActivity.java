package com.example.group;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.example.group.adapter.RecyclerCollapseAdapter;

/**
 * AppbarRecyclerActivity
 *
 * @author lao
 * @date 2019/5/11
 */

public class AppbarRecyclerActivity extends AppCompatActivity {
    private String[] yearArray = {"鼠年", "牛年", "虎年", "兔年", "龙年", "蛇年", "马年", "羊年", "猴年", "鸡年", "狗年", "猪年"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appbar_recycler);
        Toolbar tl_title = findViewById(R.id.tl_title);
        setSupportActionBar(tl_title);
        RecyclerView rv_main = findViewById(R.id.rv_main);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        rv_main.setLayoutManager(manager);
        RecyclerCollapseAdapter adapter = new RecyclerCollapseAdapter(this, yearArray);
        rv_main.setAdapter(adapter);
    }
}
