package com.example.storage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.storage.bean.CartInfo;
import com.example.storage.bean.GoodsInfo;
import com.example.storage.database.CartDBHelper;
import com.example.storage.database.GoodsDBHelper;
import com.example.storage.util.FileUtil;
import com.example.storage.util.SharedUtil;

import java.util.ArrayList;
import java.util.HashMap;


/**
 *  Created by lao on 2019/4/5
 */



public class ShoppingCartActivity extends Activity implements OnClickListener {
    private final static String TAG = "ShoppingCartActivity";
    private ImageView iv_menu;      //图片视图在title中
    private TextView tv_count;
    private TextView tv_total_price;
    private LinearLayout ll_content;
    private LinearLayout ll_cart;
    private LinearLayout ll_empty;
    private int mCount;     //商品数量
    private GoodsDBHelper mGoodsHelper;     //商品数据库帮助器对象
    private CartDBHelper mCartHelper;       //购物车数据库帮助器对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      //去掉标题栏,该方法应该写在setContentView之前
        setContentView(R.layout.activity_shopping_cart);
        iv_menu = findViewById(R.id.iv_menu);       //菜单
        TextView tv_title = findViewById(R.id.tv_title);        //标题
        tv_count = findViewById(R.id.tv_count);
        tv_total_price = findViewById(R.id.tv_total_price);
        ll_content = findViewById(R.id.ll_content);
        ll_cart = findViewById(R.id.ll_cart);
        ll_empty = findViewById(R.id.ll_empty);
        iv_menu.setOnClickListener(this);       //设置菜单监听器
        findViewById(R.id.btn_shopping_channel).setOnClickListener(this);       //手机频道按钮监听器
        findViewById(R.id.btn_settle).setOnClickListener(this);     //结算按钮监听器
        iv_menu.setVisibility(View.VISIBLE);        //设置菜单可见性
        tv_title.setText("购物车");
    }

    //显示购物车图标的数量
    private void showCount(int count) {
        mCount = count;
        Log.d(TAG, "showCount: count=" + count);
        tv_count.setText("" + mCount);
        if (mCount == 0) {
            ll_content.setVisibility(View.GONE);        //隐藏
            ll_cart.removeAllViews();      //该视图为ll_content的子视图
            ll_empty.setVisibility(View.VISIBLE);
        } else {
            ll_content.setVisibility(View.VISIBLE);
            ll_empty.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {       //按钮点击监听
        if (v.getId() == R.id.iv_menu) {        //点击菜单
            openOptionsMenu();
        } else if (v.getId() ==R.id.btn_shopping_channel) { //点击“商场”
            Intent intent = new Intent(this, ShoppingChannelActivity.class);
            startActivity(intent);
        } else if (v.getId()== R.id.btn_settle) {       //点击“结算”
            AlertDialog.Builder  builder = new AlertDialog.Builder(this);
            builder.setTitle("结算商品");
            builder.setMessage("客官抱歉，支付功能尚未开通，请下次再来");
            builder.setPositiveButton("我知道了", null);
            builder.create().show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //从menu_cart.xml中构建菜单界面布局
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;
    }

    //对菜单啊点击事件进行监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {   //菜单栏点击监听
        int id = item.getItemId();
        if (id == R.id.menu_shopping) {     //点击了菜单里“去商场购物”
            Intent intent = new Intent(this, ShoppingChannelActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_clear) {     //点击了菜单里的“清空购物车”
            //清空购物车数据库
            mCartHelper.deleteAll();
            ll_cart.removeAllViews();
            //把最新的商品数量写入共享参数
            SharedUtil.getInstance(this).writeShared("count", "0");
            //显示最新的商品数量
            showCount(0);
            mCartGoods.clear();
            mGoodsMap.clear();
            Toast.makeText(this, "购车车已清空", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menu_return) {        //点击了菜单“返回键”
            finish();
        }
        return true;
    }

    //声明一个根据视图编号查找商品信息的映射
    private HashMap<Integer, CartInfo> mCartGoods = new HashMap<Integer, CartInfo>();
    //声明一个触发上下文菜单的视图对象
    private View mContextView;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //保存该商品行的视图，以便删除商品时一块从列表移除该行
        mContextView = v;
        //从menu_goods.xml中构建菜单界面布局
        getMenuInflater().inflate(R.menu.menu_goods, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        CartInfo info = mCartGoods.get(mContextView.getId());
        int id = item.getItemId();
        if (id == R.id.menu_detail) {//点击了菜单项"查看商品详情"
            //跳转到查看商品详情页面
            goDetail(info.goods_id);
        } else if (id == R.id.menu_delete) {    //点击了“从购物车删除”
            long goods_id = info.goods_id;
            //从购物车删除商品的数据库操作
            mCartHelper.delete("goods_id=" + goods_id);
            //从购物车列表删除该商品行
            ll_cart.removeView(mContextView);
            //更新购物车中的商品数量
            int left_count = mCount - info.count;
            for (int i = 0; i < mCartArray.size(); i++) {
                if (goods_id == mCartArray.get(i).goods_id) {
                    left_count = mCount - mCartArray.get(i).count;
                    mCartArray.remove(i);
                    break;
                }
            }
            //把最新的商品数量写入共享参数
            SharedUtil.getInstance(this).writeShared("count", "" + left_count);
            //显示最新的商品数量
            showCount(left_count);
            Toast.makeText(this, "已从购物车删除" + mGoodsMap.get(goods_id).name, Toast.LENGTH_SHORT).show();
            mGoodsMap.remove(goods_id);
            refreshTotalPrice();
        }
        return true;
    }

    //跳转到商品详情页面
    private void goDetail(long rowid) {
        Intent intent = new Intent(this, ShoppingDetailActivity.class);
        intent.putExtra("goods_id", rowid);     //打包进意图
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //关闭数据库连接
        mGoodsHelper.closeLink();
        mCartHelper.closeLink();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //获取共享参数保存的购物车中的商品数量
        mCount = Integer.parseInt(SharedUtil.getInstance(this).readShared("count", "0"));
        showCount(mCount);
        //获取商品数据库帮助器对象
        mGoodsHelper = GoodsDBHelper.getInstance(this, 1);
        //打开商品数据库的写连接
        mGoodsHelper.openWriteLink();
        //获取购物车数据库帮助器的对象
        mCartHelper = CartDBHelper.getInstance(this, 1);
        //打开购物车数据库的写连接
        mCartHelper.openWriteLink();
        //模拟从网络下载商品图片
        downloadGoods();
        //展示购物车中的商品数量
        showCart();
    }

    //声明一个起始的视图编号
    private int mBeginViewId = 0x7F24FFF0;

    //声明一个购物车的商品信息队列
    private ArrayList<CartInfo> mCartArray = new ArrayList<CartInfo>();

    //声明一个根据商品编号查找商品信息的映射
    private HashMap<Long, GoodsInfo> mGoodsMap = new HashMap<Long, GoodsInfo>();

    //显示商品列表
    private void showCart() {
        //查询购物车数据库中所有的商品记录
        mCartArray = mCartHelper.query("1=1");
        Log.d(TAG, "mCartArray.size()=" + mCartArray.size());
        if (mCartArray == null || mCartArray.size() <= 0) {
            return;
        }
        ll_cart.removeAllViews();
        LinearLayout ll_row = newLinearLayout(LinearLayout.HORIZONTAL, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll_row.addView(newTextView(0, 2, Gravity.CENTER, "图片", Color.BLACK, 15));
        ll_row.addView(newTextView(0, 3, Gravity.CENTER, "名称", Color.BLACK, 15));
        ll_row.addView(newTextView(0, 1, Gravity.CENTER, "数量", Color.BLACK, 15));
        ll_row.addView(newTextView(0, 1, Gravity.CENTER, "单价", Color.BLACK, 15));
        ll_row.addView(newTextView(0, 1, Gravity.CENTER, "总价", Color.BLACK, 15));
        //把标题添加到购物车列表
        ll_cart.addView(ll_row);
        for (int i = 0; i < mCartArray.size(); i++) {
            final CartInfo info = mCartArray.get(i);
            GoodsInfo goods = mGoodsHelper.queryById(info.goods_id);
            Log.d(TAG, "name=" + goods.name + ",price=" + goods.price + ",desc=" + goods.desc);
            mGoodsMap.put(info.goods_id, goods);
            ll_row = newLinearLayout(LinearLayout.HORIZONTAL, LinearLayout.LayoutParams.WRAP_CONTENT);
            ll_row.setId(mBeginViewId + i);
            ImageView iv_thumb = new ImageView(this);
            LinearLayout.LayoutParams iv_params= new LinearLayout.LayoutParams(
              0, LinearLayout.LayoutParams.WRAP_CONTENT, 2);
            iv_thumb.setLayoutParams(iv_params);
            iv_thumb.setScaleType(ImageView.ScaleType.FIT_CENTER);
            iv_thumb.setImageBitmap(MainApplication.getInstance().mIconMap.get(info.goods_id));
            ll_row.addView(iv_thumb);
            //添加商品名称描述
            LinearLayout ll_name = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.MATCH_PARENT, 3);
            ll_name.setLayoutParams(params);
            ll_name.setOrientation(LinearLayout.VERTICAL);
            ll_name.addView(newTextView(-3, 1, Gravity.LEFT, goods.name, Color.BLACK, 17));
            ll_name.addView(newTextView(-3, 1, Gravity.LEFT, goods.desc, Color.GRAY, 12));
            ll_row.addView(ll_name);
            // 添加商品数量、单价和总价
            ll_row.addView(newTextView(1, 1, Gravity.CENTER, "" + info.count, Color.BLACK, 17));
            ll_row.addView(newTextView(1, 1, Gravity.RIGHT, "" + (int) goods.price, Color.BLACK, 15));
            ll_row.addView(newTextView(1, 1, Gravity.RIGHT, "" + (int) (info.count * goods.price), Color.RED, 17));
            //添加点击事件
            ll_row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goDetail(info.goods_id);
                }
            });
            //给商品行注册上下文菜单，防止重复注册，这里先注销再注册
            unregisterForContextMenu(ll_row);
            registerForContextMenu(ll_row);
            mCartGoods.put(ll_row.getId(), info);
            //往购物车列表中添加该商品行
            ll_cart.addView(ll_row);
        }
        //重新计算购物车中商品总金额
        refreshTotalPrice();
    }

    //重新计算购物车中的商品总金额
    private void refreshTotalPrice() {
        int total_price = 0;
        for (CartInfo info : mCartArray) {
            GoodsInfo goods = mGoodsMap.get(info.goods_id);
            total_price += goods.price * info.count;
        }
        tv_total_price.setText("" + total_price);
    }

    //创建线性视图的框架
    private LinearLayout newLinearLayout(int orientation, int height) {
        LinearLayout ll_new = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, height);
        ll_new.setLayoutParams(params);
        ll_new.setOrientation(orientation);
        ll_new.setBackgroundColor(Color.WHITE);
        return ll_new;
    }

    //创建文本视图的模板
    private TextView newTextView(int height, float weight, int gravity, String text, int textColor, int textSize) {
        TextView tv_new = new TextView(this);
        if (height == -3) { //垂直排列
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 0, weight);
            tv_new.setLayoutParams(params);
        } else {    //水平排列
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0, (height == 0) ? LinearLayout.LayoutParams.WRAP_CONTENT : LinearLayout.LayoutParams.MATCH_PARENT,weight);
            tv_new.setLayoutParams(params);
        }
        tv_new.setText(text);
        tv_new.setTextColor(textColor);
        tv_new.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        tv_new.setGravity(Gravity.CENTER | gravity);
        return tv_new;
    }

    private String mFirst = "true";     //是否首次打开

    //模拟网络数据，初始化数据库中的商品信息
    private void downloadGoods() {
        //获取共享参数保存的是否首次打开参数
        mFirst = SharedUtil.getInstance(this).readShared("first", "true");
        //获取当前APP的私有路径
        String path = MainApplication.getInstance().getExternalFilesDir(
                Environment.DIRECTORY_DOWNLOADS).toString() + "/";
        Log.d(TAG, "downloadGoods:path=" + path);
        if (mFirst.equals("true")) {        //首次打开
            ArrayList<GoodsInfo> goodsList = GoodsInfo.getDefaultList();
            for (int i = 0; i < goodsList.size(); i++) {
                GoodsInfo info = goodsList.get(i);
                //往商品数据库中插入一条该商品的记录
                long rowid = mGoodsHelper.insert(info);
                info.rowid = rowid;
                //往全局内存中而入商品小图
                Bitmap thumb = BitmapFactory.decodeResource(getResources(), info.thumb);
                MainApplication.getInstance().mIconMap.put(rowid, thumb);
                String thumb_path = path + rowid + "_s.jpg";
                FileUtil.saveImage(thumb_path, thumb);
                info.thumb_path = thumb_path;
                //往SD卡保存商品大图
                Bitmap pic = BitmapFactory.decodeResource(getResources(), info.pic);
                String pic_path = path + rowid + ".jpg";
                FileUtil.saveImage(pic_path, pic);
                pic.recycle();
                info.pic_path = pic_path;
                //更新商品数据库中所有商品的图片路径
                mGoodsHelper.update(info);
            }
        } else {    //不是首次打开
            //查询商品数据库中所有商品记录
            ArrayList<GoodsInfo> goodsArray = mGoodsHelper.query("1=1");
            for (int i = 0; i < goodsArray.size(); i++) {
                GoodsInfo info = goodsArray.get(i);
                //从指定路径读取图片文件的位图数据
                Bitmap thumb = BitmapFactory.decodeFile(info.thumb_path);
                //把位图d对象保存到应用实例的全局内存中
                MainApplication.getInstance().mIconMap.put(info.rowid, thumb);
            }
        }
        //是否把首次打开写入共享参数
        SharedUtil.getInstance(this).writeShared("first", "false");
    }
}
