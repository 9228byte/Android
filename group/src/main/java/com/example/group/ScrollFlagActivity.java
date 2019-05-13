package com.example.group;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout.LayoutParams;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.group.adapter.RecyclerCollapseAdapter;

/**
 * ScrollFlagActivity
 *
 * @author lao
 * @date 2019/5/11
 */

public class ScrollFlagActivity extends AppCompatActivity {
    private static final String TAG = "ScrollFlagActivity";
    private CollapsingToolbarLayout ctl_title;
    private String[] yearArray = {"鼠年", "牛年", "虎年", "兔年", "龙年", "蛇年", "马年", "羊年", "猴年", "鸡年", "狗年", "猪年"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_flag);
        Toolbar tl_title = findViewById(R.id.tl_title);
        tl_title.setBackgroundColor(Color.YELLOW);
        setSupportActionBar(tl_title);
        ctl_title = findViewById(R.id.ctl_title);
        ctl_title.setTitle("滚动标志");
        initFlagSpinner();
        RecyclerView rv_main = findViewById(R.id.rv_main);
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        rv_main.setLayoutManager(llm);
        RecyclerCollapseAdapter adapter = new RecyclerCollapseAdapter(this, yearArray);
        rv_main.setAdapter(adapter);
    }

    private String[] descArray = {
      "scroll",
      "scroll|enterAlways",
      "scroll|exitUntilCollapsed",
      "scroll|enterAlways|enterAlways|enterAlwaysCollapsed",
            "scroll|snap"};

    private int [] flagArray = {
            LayoutParams.SCROLL_FLAG_SCROLL,
            LayoutParams.SCROLL_FLAG_SCROLL | LayoutParams.SCROLL_FLAG_ENTER_ALWAYS,
            LayoutParams.SCROLL_FLAG_SCROLL | LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED,
            LayoutParams.SCROLL_FLAG_SCROLL | LayoutParams.SCROLL_FLAG_ENTER_ALWAYS | LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED,
            LayoutParams.SCROLL_FLAG_SCROLL | LayoutParams.SCROLL_FLAG_SNAP};

    private void initFlagSpinner() {
        Log.d(TAG, "initFlagSpinner: ");
        ArrayAdapter<String> flagAdapter = new ArrayAdapter<String>(this, R.layout.item_select, descArray);
        flagAdapter.setDropDownViewResource(R.layout.item_dropdown);
        Spinner sp_flag = findViewById(R.id.sp_flag);
        sp_flag.setPrompt("请选择滚动标志");
        sp_flag.setAdapter(flagAdapter);
        sp_flag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LayoutParams params = (LayoutParams) ctl_title.getLayoutParams();
                //设置布局参数中的滚动标志位
                params.setScrollFlags(flagArray[position]);
                ctl_title.setLayoutParams(params);
                Log.d(TAG, "onItemSelected: ");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp_flag.setSelection(0);
    }
}
