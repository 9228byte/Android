package com.example.group;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout.LayoutParams;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.group.adapter.RecyclerCombineAdapter;
import com.example.group.adapter.RecyclerGridAdapter;
import com.example.group.bean.GoodsInfo;
import com.example.group.constant.ImageList;
import com.example.group.util.DateUtil;
import com.example.group.util.MenuUtil;
import com.example.group.util.Utils;
import com.example.group.widget.BannerPager;
import com.example.group.widget.BannerPager.BannerClickListener;
import com.example.group.widget.SpacesItemDecoration;

/**
 * DepartmentHomeActivity
 *
 * @author lao
 * @date 2019/5/13
 */

public class DepartmentHomeActivity extends AppCompatActivity implements BannerClickListener {
    private static final String TAG = "DepartmentHomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_home);
        Toolbar tl_head = findViewById(R.id.tl_head);
        tl_head.setTitle("首页商城");
        setSupportActionBar(tl_head);
        initBannerPager();      //初始化广告轮播条
        initGrid();         //初始化市场网格列表
        initCombine();      //初始化猜你喜欢商品网格
    }

    private void initBannerPager() {
        BannerPager banner = findViewById(R.id.banner_pager);
        //获取线性视图布局参数
        LayoutParams params = (LayoutParams) banner.getLayoutParams();
        params.height = (int) (Utils.getScreenWidth(this) * 250f / 640f);
        banner.setImage(ImageList.getDefault());
        banner.setOnBannerListener(this);
        banner.start();     //设置自动滚动
    }

    private void initGrid() {
        RecyclerView rv_grid = findViewById(R.id.rv_grid);
        GridLayoutManager manager = new GridLayoutManager(this, 5);
        rv_grid.setLayoutManager(manager);
        RecyclerGridAdapter adapter = new RecyclerGridAdapter(this, GoodsInfo.getDefaultGrid());
        adapter.setOnItemClickListener(adapter);
        adapter.setOnItemLongClickListener(adapter);
        rv_grid.setAdapter(adapter);
        //默认动画
        rv_grid.setItemAnimator(new DefaultItemAnimator());
        //空白装饰
        rv_grid.addItemDecoration(new SpacesItemDecoration(1));
    }

    private void initCombine() {
        RecyclerView rv_combine = findViewById(R.id.rv_combine);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                if (i == 0 || i == 1) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });
        rv_combine.setLayoutManager(manager);
        RecyclerCombineAdapter adapter = new RecyclerCombineAdapter(this, GoodsInfo.getDefaultCombine());
        adapter.setOnItemClickListener(adapter);
        adapter.setOnItemLongClickListener(adapter);
        rv_combine.setAdapter(adapter);
        rv_combine.setItemAnimator(new DefaultItemAnimator());
        rv_combine.addItemDecoration(new SpacesItemDecoration(1));
    }

    @Override
    public void onBannerClick(int position) {
        //点击了广告图片
        String desc = String.format("您点击了第%d张图片", position + 1);
        Toast.makeText(this, desc, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        //显示菜单项序左侧的图标
        MenuUtil.setOverflowIconVisible(featureId, menu);
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //从menu.xml中构建菜单布局
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.menu_search) {
            Intent intent = new Intent(this, SearchViewActivity.class);
            intent.putExtra("collapse", false);
            startActivity(intent);
        } else if (id == R.id.menu_refresh) {
            Toast.makeText(this, "当前刷新时间：" + DateUtil.getNowDateTime("yyyy-MM--dd HH:mm:ss"), Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_about) {
            Toast.makeText(this, "这是个商场首页", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_quit) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
