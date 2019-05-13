package com.example.group;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.group.adapter.LinearDynamicAdapter;
import com.example.group.bean.GoodsInfo;
import com.example.group.widget.RecyclerExtras.OnItemClickListener;
import com.example.group.widget.RecyclerExtras.OnItemLongClickListener;
import com.example.group.widget.RecyclerExtras.OnItemDeleteClickListener;
import com.example.group.widget.SpacesItemDecoration;

import java.util.ArrayList;

public class RecyclerDynamicActivity extends AppCompatActivity implements OnClickListener
        , OnItemClickListener, OnItemLongClickListener, OnItemDeleteClickListener {
    private static final String TAG = "RecyclerDynamicActivity";
    private RecyclerView rv_dynamic;
    private LinearDynamicAdapter mAdapter;
    private ArrayList<GoodsInfo> mPublicArray;      //当前公众号信息队列
    private ArrayList<GoodsInfo> mAllArray;         //全部公众号信息队列
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_dynamic);
        findViewById(R.id.btn_recycler_add).setOnClickListener(this);
        initRecyclerDynamic();      //初始化动态线性布局的循环视图

    }

    private void initRecyclerDynamic() {
        rv_dynamic = findViewById(R.id.rv_dynamic);
        //创建一个垂直方向的线性布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        rv_dynamic.setLayoutManager(manager);
        mAllArray = GoodsInfo.getDefaultList();
        mPublicArray = GoodsInfo.getDefaultList();
        //构建公众号列表信息队列
        mAdapter = new LinearDynamicAdapter(this, mPublicArray);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemLongClickListener(this);
        mAdapter.setOnItemDeleteClickListener(this);
        rv_dynamic.setAdapter(mAdapter);
        rv_dynamic.setItemAnimator(new DefaultItemAnimator());
        rv_dynamic.addItemDecoration(new SpacesItemDecoration(1));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_recycler_add) {
            int position = (int) (Math.random()) * 100 % mAllArray.size();
            GoodsInfo old_item = mAllArray.get(position);
            GoodsInfo new_item  = new GoodsInfo(old_item.pic_id, old_item.title, old_item.desc);
            mPublicArray.add(0 , new_item);
            //通知适配器列表在第一项插入数据
            mAdapter.notifyItemInserted(0);
            //让虚幻视图滚动带第一项所在位置
            rv_dynamic.scrollToPosition(0);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        String desc = String.format("您点击了第%d项， 标题是%s", position + 1, mPublicArray.get(position).title);
        Toast.makeText(this, desc, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        GoodsInfo item = mPublicArray.get(position);
        item.bPressed = !item.bPressed;
        mPublicArray.set(position, item);
        //通知适配器列表在第几项发生变更
        mAdapter.notifyItemChanged(position);
    }

    @Override
    public void onItemDeleteClick(View view, int position) {
        mPublicArray.remove(position);
        //通知适配器列表在第几项删除数据
        mAdapter.notifyItemRemoved(position);
    }
}
