package com.example.group;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group.util.DateUtil;
import com.example.group.util.MenuUtil;

/**
 * SearchViewActivity
 *
 * @author lao
 * @date 2019/5/2
 */

public class SearchViewActivity extends AppCompatActivity {
    private static final String TAG = "SearchViewActivity";
    private TextView tv_desc;
    private SearchView.SearchAutoComplete sac_key;  //声明一个搜索自动完成的编辑框对象.
    private String[] hintArray = {"iphone", "iphone8", "iphone8 plus", "iphone7", "iphone 7 plus"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        Toolbar tl_head  = findViewById(R.id.tl_head);
        tl_head.setTitle("搜索框页面");
        setSupportActionBar(tl_head);
        tv_desc = findViewById(R.id.tv_desc);
    }


    @SuppressLint("RestrictedApi")
    private void initSearchView(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.menu_search);
        //从菜单项中获取搜索框对象
        SearchView searchView = (SearchView)  menuItem.getActionView();
        //设置搜索框默认自动缩小为图标
        searchView.setIconifiedByDefault(getIntent().getBooleanExtra("collapse", true));
        //设置是否显示搜索按钮。搜索按钮只是一个箭头图标
        searchView.setSubmitButtonEnabled(true);
        //从系统管理器中获取搜索管理器
        SearchManager sm = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        //创建搜索结果页面的组件名称对象
        ComponentName cn = new ComponentName(this, SearchResultActivity.class);
        //从结果页面中注册的activity节点获取相关搜索信息，即searchable.xml定义的搜索控件
        SearchableInfo info = sm.getSearchableInfo(cn);
        if (info == null) {
            Log.d(TAG, "initSearchView: ");
            return;
        }
        //设置搜索框的搜索信息
        searchView.setSearchableInfo(info);
        //从搜索框获取名叫search_src_text的自动完成编辑框
        sac_key = searchView.findViewById(R.id.search_src_text);
        sac_key.setTextColor(Color.WHITE);
        sac_key.setHintTextColor(Color.WHITE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //搜索关键字完成输入
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //搜索关键词发生变化
                doSearch(s);
                return true;
            }
        });
        Bundle bundle = new Bundle();
        bundle.putString("hi", "hello");
        searchView.setAppSearchData(bundle);
    }

    //自动匹配相关的关键词列表
    private void doSearch(String text) {
        Log.d(TAG, "doSearch: ");
        if (text.indexOf("i") == 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.search_list_auto, hintArray);
            sac_key.setAdapter(adapter);
            sac_key.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    sac_key.setText(((TextView) view).getText());
                }
            });
        }
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        Log.d(TAG, "onMenuOpened: ");
        //显示菜单项左侧的图标
        MenuUtil.setOverflowIconVisible(featureId, menu);
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: ");
        getMenuInflater().inflate(R.menu.menu_search, menu);
        initSearchView(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.menu_refresh) {
            tv_desc.setText("当前刷新时间：" + DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss"));
            return true;
        } else if (id == R.id.menu_about) {
            Toast.makeText(this, "这是工具栏的演示demo", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_quit) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
