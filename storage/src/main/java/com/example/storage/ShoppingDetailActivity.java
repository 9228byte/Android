package com.example.storage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.storage.bean.CartInfo;
import com.example.storage.bean.GoodsInfo;
import com.example.storage.database.CartDBHelper;
import com.example.storage.database.GoodsDBHelper;
import com.example.storage.util.DateUtil;
import com.example.storage.util.SharedUtil;

/**
 *  Created by lao on 2019/4/5
 */

public class ShoppingDetailActivity extends Activity implements OnClickListener {
    private TextView tv_title;
    private TextView tv_count;
    private TextView tv_goods_price;     //价格
    private TextView tv_goods_desc; //商品详细信息
    private ImageView iv_goods_pic;     //大图
    private int mCount;     //购物车图标显示数量
    private long mGoodsId;  //当前商品编号
    private GoodsDBHelper mGoodsHelper;
    private CartDBHelper mCartHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_detail);
        tv_title = findViewById(R.id.tv_title);
        tv_count = findViewById(R.id.tv_count);
        tv_goods_price = findViewById(R.id.tv_goods_price);
        tv_goods_desc = findViewById(R.id.tv_goods_desc);
        iv_goods_pic = findViewById(R.id.iv_goods_pic);
        findViewById(R.id.iv_cart).setOnClickListener(this);        //跳转到购物车
        findViewById(R.id.btn_add_cart).setOnClickListener(this);       //添加到购物车
        //获取共享参数保存的购物车中的商品数量
        mCount = Integer.parseInt(SharedUtil.getInstance(this).readShared("count", "0"));
        tv_count.setText("" + mCount);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_cart) {
            Intent intent = new Intent(this, ShoppingCartActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_add_cart) {
            //把商品添加到购物车
            addToCart(mGoodsId);   //传入商品id编号
            Toast.makeText(this, "成功添加至购物车", Toast.LENGTH_SHORT).show();
        }
    }

    //把指定编号的商品添加到购物车数据库中
    private void addToCart(long goods_id) {
        mCount++;
        tv_count.setText("" + mCount);
        //把购物车中的商品数量写入共享参数
        SharedUtil.getInstance(this).writeShared("count", "" + mCount);
        //根据商品编号查询购物车中的商品记录
        CartInfo info = mCartHelper.queryByGoodsId(goods_id);       //查看是否有同一编号的商品
        if (info != null) { //购物车中已存在该商品
            //数量加一
            info.count++;
            info.update_time = DateUtil.getNowDateTime("");
            //更新购物车数据库中的商品信息记录
            mCartHelper.update(info);
        } else {    //购物车中不存在该商品记录
            info = new CartInfo();
            info.goods_id = goods_id;
            info.count = 1;
            info.update_time = DateUtil.getNowDateTime("");
            //往购物车中添加一条新的商品记录
            mCartHelper.insert(info);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoodsHelper = GoodsDBHelper.getInstance(this, 1);
        mGoodsHelper.openReadLink();
        mCartHelper = CartDBHelper.getInstance(this, 1);
        mCartHelper.openWriteLink();
        showDetail();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGoodsHelper.closeLink();
        mCartHelper.closeLink();
    }

    private void showDetail() {
        //获取页面传来的商品编号
        mGoodsId = getIntent().getLongExtra("goods_id", 0L);
        if (mGoodsId > 0) {
            //根据商品编号查询商品数据库中的商品记录
            GoodsInfo info = mGoodsHelper.queryById(mGoodsId);
            tv_title.setText(info.name);
            tv_goods_desc.setText(info.desc);
            tv_goods_price.setText("" + info.price);
            //从指定路径读取文件的位图数据
            Bitmap pic = BitmapFactory.decodeFile(info.pic_path);
            iv_goods_pic.setImageBitmap(pic);
        }
    }
}
