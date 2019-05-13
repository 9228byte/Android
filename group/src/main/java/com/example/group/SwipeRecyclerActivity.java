package com.example.group;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.group.adapter.LinearDynamicAdapter;
import com.example.group.bean.GoodsInfo;
import com.example.group.widget.RecyclerExtras.OnItemClickListener;
import com.example.group.widget.RecyclerExtras.OnItemLongClickListener;
import com.example.group.widget.RecyclerExtras.OnItemDeleteClickListener;
import com.example.group.widget.SpacesItemDecoration;

import java.util.ArrayList;

/**
 * SwipeRecyclerActivity
 *
 * @author lao
 * @date 2019/5/11
 */

public class SwipeRecyclerActivity extends AppCompatActivity implements OnRefreshListener,
        OnItemClickListener, OnItemLongClickListener, OnItemDeleteClickListener {
    private SwipeRefreshLayout srl_dynamic;
    private RecyclerView rv_dynamic;
    private LinearDynamicAdapter mAdapter;      //线性适配器对象
    private ArrayList<GoodsInfo> mPublicArray;
    private ArrayList<GoodsInfo> mAllArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_recycler);
        srl_dynamic = findViewById(R.id.srl_dynamic);
        srl_dynamic.setOnRefreshListener(this);
        srl_dynamic.setColorSchemeResources(R.color.red, R.color.orange, R.color.blue);
        initRecyclerDynamic();
    }

    private void initRecyclerDynamic() {
        rv_dynamic = findViewById(R.id.rv_dynamic);
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        rv_dynamic.setLayoutManager(llm);
        mAllArray = GoodsInfo.getDefaultList();
        mPublicArray = GoodsInfo.getDefaultList();
        mAdapter = new LinearDynamicAdapter(this, mPublicArray);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemLongClickListener(this);
        mAdapter.setOnItemDeleteClickListener(this);
        rv_dynamic.setAdapter(mAdapter);
        rv_dynamic.setItemAnimator(new DefaultItemAnimator());
        rv_dynamic.addItemDecoration(new SpacesItemDecoration(1));
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(mRefresh, 2000);
    }

    private Handler mHandler = new Handler();
    private Runnable mRefresh = new Runnable() {
        @Override
        public void run() {
            //结束下拉
            srl_dynamic.setRefreshing(false);
            int position = (int) (Math.random() * 100 % mAllArray.size());
            GoodsInfo old_item = mAllArray.get(position);
            GoodsInfo new_item = new GoodsInfo(old_item.pic_id, old_item.title, old_item.desc);
            mPublicArray.add(0, new_item);
            mAdapter.notifyItemInserted(0);
            //滚动到第一项所在位置
            rv_dynamic.scrollToPosition(0);
        }
    };

    @Override
    public void onItemClick(View view, int position) {
        String desc = String.format("您点击了第%d项，标题是%s", position + 1, mPublicArray.get(position).title);
        Toast.makeText(this, desc, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        GoodsInfo item = mPublicArray.get(position);
        item.bPressed = !item.bPressed;
        mPublicArray.set(position, item);
        //通知第几项发生变更
        mAdapter.notifyItemChanged(position);
    }

    @Override
    public void onItemDeleteClick(View view, int position) {
        mPublicArray.remove(position);
        //通知适配器列表第几项删除数据
        mAdapter.notifyItemRemoved(position);
    }
}
