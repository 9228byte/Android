package com.example.senior;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.example.senior.adapter.GoodsAdapter;
import com.example.senior.bean.CartInfo;
import com.example.senior.bean.GoodsInfo;
import com.example.senior.database.CartDBHelper;
import com.example.senior.database.GoodsDBHelper;
import com.example.senior.adapter.GoodsAdapter.addCartListener;
import com.example.senior.util.DateUtil;
import com.example.senior.util.SharedUtil;

import java.util.ArrayList;

/**
 *  Created by lao on 2019/4/7
 */


public class ShoppingChannelActivity extends AppCompatActivity implements
        OnClickListener, addCartListener {
    private final static String TAG = "ShoppingChanelActivity";
    private TextView tv_count;
    private GridView gv_channel;        //声明一个网格视图对象
    private int mCount;
    private GoodsDBHelper mGoodsHelper;
    private CartDBHelper mCartHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_channel);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_count = findViewById(R.id.tv_count);
        gv_channel = findViewById(R.id.gv_channel);
        findViewById(R.id.iv_cart).setOnClickListener(this);
        tv_title.setText("手机商场");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_cart) {
            Intent intent = new Intent(this, ShoppingCartActivity.class);
            startActivity(intent);
        }
    }

    //把指定编号的商品添加进购物车
    @Override
    public void addToCart(long goods_id) {
        mCount++;
        tv_count.setText("" + mCount);
        SharedUtil.getInstance(this).writeInt("count", mCount);
        CartInfo info = mCartHelper.queryByGoodsId(goods_id);
        if (info != null) {
            info.count++;
            info.update_time = DateUtil.getNowDateTime("");
            mCartHelper.update(info);
        } else {
            info = new CartInfo();
            info.goods_id = goods_id;
            info.count = 1;
            info.update_time = DateUtil.getNowDateTime("");
            mCartHelper.insert(info);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCartHelper.closeLink();
        mGoodsHelper.closeLink();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCount = SharedUtil.getInstance(this).readInt("count", 0);
        tv_count.setText("" + mCount);
        mGoodsHelper = GoodsDBHelper.getInstance(this, 1);
        mGoodsHelper.openReadLink();
        mCartHelper = CartDBHelper.getInstance(this, 1);
        mCartHelper.openWriteLink();
        showGoods();
    }

    private void showGoods() {
        //判断全局内存中的图标映射是否为空
        if (MainApplication.getInstance().mIconMap.size() <= 0) {
            ShoppingCartActivity.downloadGoods(this, "false", mGoodsHelper);
        }
        ArrayList<GoodsInfo> goodsArray = mGoodsHelper.query("1=1");
        GoodsAdapter adapter = new GoodsAdapter(this, goodsArray, this);
        gv_channel.setAdapter(adapter);
        gv_channel.setOnItemClickListener(adapter);
    }
}
