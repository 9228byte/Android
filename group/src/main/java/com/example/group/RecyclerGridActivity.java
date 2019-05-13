package com.example.group;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.group.adapter.RecyclerGridAdapter;
import com.example.group.bean.GoodsInfo;
import com.example.group.widget.SpacesItemDecoration;

public class RecyclerGridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_grid);
        initRecyclerGrid();
    }

    private void initRecyclerGrid() {
        RecyclerView rv_grid = findViewById(R.id.rv_grid);
        GridLayoutManager manager = new GridLayoutManager(this, 5);
        //设置循环视图布局管理器
        rv_grid.setLayoutManager(manager);
        //构建网格布局适配器
        RecyclerGridAdapter adapter = new RecyclerGridAdapter(this, GoodsInfo.getDefaultGrid());
        adapter.setOnItemClickListener(adapter);
        adapter.setOnItemLongClickListener(adapter);
        rv_grid.setAdapter(adapter);
        rv_grid.setItemAnimator(new DefaultItemAnimator());
        rv_grid.addItemDecoration(new SpacesItemDecoration(1));
    }
}
