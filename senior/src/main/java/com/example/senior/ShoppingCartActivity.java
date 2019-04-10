package com.example.senior;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.example.senior.adapter.CartAdapter;
import com.example.senior.bean.CartInfo;
import com.example.senior.bean.GoodsInfo;
import com.example.senior.database.CartDBHelper;
import com.example.senior.database.GoodsDBHelper;
import com.example.senior.util.FileUtil;
import com.example.senior.util.SharedUtil;

import java.util.ArrayList;


/**
 *  Created by lao on 2019/4/7
 */


public class ShoppingCartActivity extends Activity implements
        OnClickListener, OnItemClickListener, AdapterView.OnItemLongClickListener {
    private final static String TAG = "ShoppingCartActivity";
    private ImageView iv_menu;
    private TextView tv_count;
    private TextView tv_total_price;
    private LinearLayout ll_content;
    private LinearLayout ll_empty;
    private ListView lv_cart;
    private int mCount;
    private GoodsDBHelper mGoodsHelper;
    private CartDBHelper mCartHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        iv_menu = findViewById(R.id.iv_menu);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_count = findViewById(R.id.tv_count);
        tv_total_price = findViewById(R.id.tv_total_price);
        ll_content = findViewById(R.id.ll_content);
        ll_empty = findViewById(R.id.ll_empty);
        lv_cart = findViewById(R.id.lv_cart);
        iv_menu.setOnClickListener(this);
        findViewById(R.id.btn_shopping_channel).setOnClickListener(this);
        findViewById(R.id.btn_settle).setOnClickListener(this);
        iv_menu.setVisibility(View.VISIBLE);
        tv_title.setText("购物车");
    }

    private void showCount(int count) {
        mCount = count;
        tv_count.setText("" + mCount);;
        if (mCount == 0) {
            ll_content.setVisibility(View.GONE);
            ll_empty.setVisibility(View.VISIBLE);
        } else {
            ll_content.setVisibility(View.VISIBLE);
            ll_empty.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_menu) {
            openOptionsMenu();      //打开菜单
        } else if (v.getId() == R.id.btn_shopping_channel) {
            Intent intent = new Intent(this, ShoppingChannelActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_settle) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("结算商品");
            builder.setMessage("抱歉，尚未开通支付，请下次在来");
            builder.setPositiveButton("我知道了", null);
            builder.create().show();
        }
    }
    private ArrayList<CartInfo> mCartArray = new ArrayList<CartInfo>();      //购物车中的商品信息队列
    private CartInfo mCurrentGood;      //声明当前的商品对象
    private View mCurrentView;      //声明一个当前视图的对象

    //商品项的点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mCurrentGood = mCartArray.get(position);
        goDetail(mCurrentGood.goods_id);        //跳转到商品详情页
    }

    //商品项长按事件
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        mCurrentGood = mCartArray.get(position);
        //保存当前长按的列表项视图
        mCurrentView = view;
        //延迟100毫秒后执行任务mPopupMenu，为了留出事件让长按事件走完流程，避免长按监听器冲突
        mHandler.postDelayed(mPopupMenu, 100);
        return true;
    }

    private Handler mHandler = new Handler();       //声明一个处理器对象
    //定义一个上下文菜单的弹出任务
    private Runnable mPopupMenu = new Runnable() {
        @Override
        public void run() {
            //取消lv_cart的长按监听器
            lv_cart.setOnItemLongClickListener(null);
            //注册列表项视图的上下文菜单
            registerForContextMenu(mCurrentView);       //为当前列表视图注册上下文菜单
            //为该列表项视图弹出上下文菜单
            openContextMenu(mCurrentView);
            //注销列表项视图的上下文菜单
            unregisterForContextMenu(mCurrentView);
            //重新设置iv_cart的长按监听器
            lv_cart.setOnItemLongClickListener(ShoppingCartActivity.this);
        }
    };

    //菜单购物车布局
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //从menu_cart.xml中构建菜单界面布局
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;
    }

    //菜单布局中设置监听器
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_shopping) {
            Intent intent = new Intent(this, ShoppingChannelActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_clear) {
            mCartHelper.deleteAll();
            SharedUtil.getInstance(this).writeInt("count", 0);
            showCount(0);
            ll_content.setVisibility(View.GONE);
            Toast.makeText(this, "购物车已清空", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menu_return) {
            finish();
        }
        return true;
    }

    //构建商品布局
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_goods, menu);
    }

    //商品项上下文菜单选择监听器
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_detail) {
            Intent intent = new Intent(this, ShoppingChannelActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_delete) {
            mCartHelper.delete("goods_id=" + mCurrentGood.goods_id);
            int left_count = mCount - mCurrentGood.count;
            SharedUtil.getInstance(this).writeInt("count", left_count);
            showCount(left_count);
            Toast.makeText(this, "已从购物车中删除", Toast.LENGTH_SHORT).show();
            showCart();
        }
        return true;
    }

    public void goDetail(long rowid) {
        Intent intent = new Intent(this, ShoppingDetailActivity.class);
        intent.putExtra("goods_id", rowid);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGoodsHelper.closeLink();
        mCartHelper.closeLink();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mCount = SharedUtil.getInstance(this).readInt("count", 0);
        showCount(mCount);
        mGoodsHelper = GoodsDBHelper.getInstance(this, 1);
        mGoodsHelper.openWriteLink();
        mCartHelper = CartDBHelper.getInstance(this, 1);
        mCartHelper.openReadLink();
        mFirst = SharedUtil.getInstance(this).readString("first", "true");
        downloadGoods(this, mFirst, mGoodsHelper);
        SharedUtil.getInstance(this).writeString("first", "false");
        showCart();
    }

    private void showCart() {
        mCartArray = mCartHelper.query("1=1");
        if (mCartArray == null || mCartArray.size() <= 0) {
            return;
        }
        for (int i = 0; i < mCartArray.size(); i++) {
            CartInfo info = mCartArray.get(i);
            GoodsInfo goods = mGoodsHelper.queryById(info.goods_id);
            info.goods = goods;
            mCartArray.set(i, info);
        }
        CartAdapter adapter = new CartAdapter(this, mCartArray);
        lv_cart.setAdapter(adapter);
        lv_cart.setOnItemClickListener(this);
        lv_cart.setOnItemLongClickListener(this);
        refreshTotalPrice();

    }

    private void refreshTotalPrice() {
        int total_price = 0;
        for (CartInfo info : mCartArray) {
            total_price += info.goods.price * info.count;
        }
        tv_total_price.setText("" + total_price);

    }

    private String mFirst = "true";

    public static void downloadGoods(Context ctx, String isFirst, GoodsDBHelper helper) {
        String path = MainApplication.getInstance().getExternalFilesDir(
                Environment.DIRECTORY_DOWNLOADS).toString() + "/";
        if (isFirst.equals("true")) {
            ArrayList<GoodsInfo>  goodsList = GoodsInfo.getDefaultList();
            for (int i =0; i< goodsList.size(); i++) {
                GoodsInfo info = goodsList.get(i);
                long rowid = helper.insert(info);
                info.rowid = rowid;
                Bitmap thumb = BitmapFactory.decodeResource(ctx.getResources(), info.thumb);
                MainApplication.getInstance().mIconMap.put(rowid, thumb);
                String thumb_path = path + rowid + "_s.jpg";
                FileUtil.saveImage(thumb_path, thumb);
                info.thumb_path = thumb_path;
                //往SD卡保存商品大图
                Bitmap pic = BitmapFactory.decodeResource(ctx.getResources(), info.pic);
                String pic_path = path + rowid + ".jpg";
                FileUtil.saveImage(pic_path, pic);
                pic.recycle();
                info.pic_path = pic_path;       //添加大图路径
                helper.update(info);
            }
        } else {
            ArrayList<GoodsInfo> goodsArray = helper.query("1=1");
            for (int i = 0; i< goodsArray.size(); i++) {
                GoodsInfo info = goodsArray.get(i);
                Log.d(TAG, "rowid=" + info.rowid + ",thumb_path=" + info.thumb_path);
                Bitmap thumb = BitmapFactory.decodeFile(info.thumb_path);
                MainApplication.getInstance().mIconMap.put(info.rowid, thumb);
            }
        }

    }



}
