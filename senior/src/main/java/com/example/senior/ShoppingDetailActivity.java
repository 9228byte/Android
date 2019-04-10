package com.example.senior;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.senior.adapter.GoodsAdapter;
import com.example.senior.bean.CartInfo;
import com.example.senior.bean.GoodsInfo;
import com.example.senior.database.CartDBHelper;
import com.example.senior.database.GoodsDBHelper;
import com.example.senior.util.DateUtil;
import com.example.senior.util.SharedUtil;

import org.w3c.dom.Text;

/**
 *  Created by lao on 2019/4/7
 */


public class ShoppingDetailActivity extends AppCompatActivity implements OnClickListener {
    private final static String TAG = "ShopingDetailActivity";
    private TextView tv_title;
    private TextView tv_count;
    private TextView tv_goods_price;
    private TextView tv_goods_desc;
    private ImageView iv_goods_pic;
    private int mCount;     //购物车小图标显示数量
    private long mGoodId;       //当前商品编号
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
        findViewById(R.id.iv_cart).setOnClickListener(this);
        findViewById(R.id.btn_add_cart).setOnClickListener(this);
        mCount = SharedUtil.getInstance(this).readInt("count", 0);
        tv_count.setText("" + mCount);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_cart) {
            Intent intent = new Intent(this, ShoppingCartActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_add_cart) {
            addToCart(mGoodId);
            Toast.makeText(this, "成功添加到购物车", Toast.LENGTH_SHORT).show();
        }
    }

    private void addToCart(long goods_id) {
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
       mGoodsHelper = GoodsDBHelper.getInstance(this, 1);
       mGoodsHelper.openReadLink();
       mCartHelper = CartDBHelper.getInstance(this, 1);
       mCartHelper.openWriteLink();
       showDetail();
    }

    private void showDetail() {
        mGoodId = getIntent().getLongExtra("goods_id", 0L);
        if (mGoodId > 0) {
            GoodsInfo info = mGoodsHelper.queryById(mGoodId);
            tv_title.setText(info.name);
            tv_goods_desc.setText(info.desc);
            tv_goods_price.setText("" + info.price);
            Bitmap pic = BitmapFactory.decodeFile(info.pic_path);
            Log.d(TAG, "showDetail: pic=" + pic);
            iv_goods_pic.setImageBitmap(pic);
        }
    }
}
