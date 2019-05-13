package com.example.group;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.group.adapter.RecyclerCombineAdapter;
import com.example.group.bean.GoodsInfo;
import com.example.group.widget.SpacesItemDecoration;

public class RecyclerCombineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_combine);
        initRecyclerCombine();
    }

    private void initRecyclerCombine() {
        RecyclerView rv_combine = findViewById(R.id.rv_combine);
        //建立一个四列的网格布局管理器
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        //设置网格布局管理器的占位规则
        //以下占位规则的意思是：第一项和第二项占两列，其他项占一列；
        //如果网格的列数为四，那么第一项和第二项平分第一行，第二行开始每行有四项
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {

                if (i == 0 || i == 1) { //为第一项或者第二项
                    return 2;
                } else {    //其他项
                    return 1;
                }
            }
        });

        //设置循环布局管理器
        rv_combine.setLayoutManager(manager);
        RecyclerCombineAdapter adapter = new RecyclerCombineAdapter(this, GoodsInfo.getDefaultCombine());
        adapter.setOnItemClickListener(adapter);
        adapter.setOnItemLongClickListener(adapter);
        rv_combine.setAdapter(adapter);
        rv_combine.setItemAnimator(new DefaultItemAnimator());
        rv_combine.addItemDecoration(new SpacesItemDecoration(1));
    }
}
