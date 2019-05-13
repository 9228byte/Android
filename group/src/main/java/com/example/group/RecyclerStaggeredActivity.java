package com.example.group;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.LinearLayout;

import com.example.group.adapter.RecyclerStaggeredAdapter;
import com.example.group.bean.GoodsInfo;
import com.example.group.widget.SpacesItemDecoration;

public class RecyclerStaggeredActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_staggered);
        initRecyclerStaggered();
    }

    private void initRecyclerStaggered() {
        RecyclerView rv_staggered = findViewById(R.id.rv_staggered);
        //创建一个垂直方向的瀑布流布局管理器
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3, LinearLayout.VERTICAL);
        rv_staggered.setLayoutManager(manager);
        RecyclerStaggeredAdapter adapter = new RecyclerStaggeredAdapter(this , GoodsInfo.getDefaultStag());
        adapter.setOnItemClickListener(adapter);
        adapter.setOnItemLongClickListener(adapter);
        rv_staggered.setAdapter(adapter);
        rv_staggered.setItemAnimator(new DefaultItemAnimator());
        rv_staggered.addItemDecoration(new SpacesItemDecoration(3));
    }

}
