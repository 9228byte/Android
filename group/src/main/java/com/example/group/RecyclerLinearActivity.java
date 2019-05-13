package com.example.group;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.example.group.adapter.RecyclerLinearAdapter;
import com.example.group.bean.GoodsInfo;
import com.example.group.widget.SpacesItemDecoration;

public class RecyclerLinearActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_linear);
        initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView rv_linear = findViewById(R.id.rv_linear);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        rv_linear.setLayoutManager(manager);
        RecyclerLinearAdapter adapter = new RecyclerLinearAdapter(this, GoodsInfo.getDefaultList());
        adapter.setOnItemClickLintener(adapter);
        adapter.setOnLongItemClickListener(adapter);
        rv_linear.setAdapter(adapter);
        //设置默认动画效果
        rv_linear.setItemAnimator(new DefaultItemAnimator());
        //添加列表项之间的空白装饰
        rv_linear.addItemDecoration(new SpacesItemDecoration(1));
    }
}
