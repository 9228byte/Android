package com.example.senior;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.view.ViewGroup.LayoutParams;

import com.example.senior.adapter.PlanetListAdapter;
import com.example.senior.bean.Planet;

import java.util.ArrayList;


public class ListViewActivity extends AppCompatActivity {
    private final static String TAG = "ListViewActivity";
    private ListView lv_planet;     //声明一个列表视图对象
    private Drawable drawable;      //声明一个图形对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        ArrayList<Planet> planetList = Planet.getDefaultList();
        //构建一个行星队列适配器
        PlanetListAdapter adapter = new PlanetListAdapter(this, planetList);
        lv_planet = findViewById(R.id.lv_planet);
        //设置适配器
        lv_planet.setAdapter(adapter);
        //设置列表点击监听器
        lv_planet.setOnItemClickListener(adapter);
        //长按监听器
        lv_planet.setOnItemLongClickListener(adapter);
        //从资源文件中获取分隔线的图形对象
        drawable = getResources().getDrawable(R.drawable.divider_red2);     //分隔线指定绘制图形
        //初始化分隔线下拉框
        initDividerSpinner();
    }

    private String[] dividerArray = {
            "不显示分隔线(分隔线高度为0)",
            "不显示分隔线(分隔线为null)",
            "只显示内部分隔线(先设置分隔线高度)",
            "只显示内部分隔线(后设置分隔线高度)",
            "显示底部分隔线(高度是wrap_content)",
            "显示底部分隔线(高度是match_parent)",
            "显示顶部分隔线(别瞎折腾了，显示不了)",
            "显示全部分隔线(看我用padding大法)"
    };

    //初始化分隔线显示方式的下拉框
    private void initDividerSpinner() {
        ArrayAdapter<String> dividerAdapter = new ArrayAdapter<String>(this,
                R.layout.item_select, dividerArray);
        Spinner sp_list = findViewById(R.id.sp_list);
        sp_list.setPrompt("请选择分隔线显示方式");
        sp_list.setAdapter(dividerAdapter);
        sp_list.setOnItemSelectedListener(new DividerSelectedListener());
        sp_list.setSelection(0);
    }

    class DividerSelectedListener implements OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int dividerHeight = 5;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            lv_planet.setDivider(drawable);     //设置lv_planet的分隔线
            lv_planet.setDividerHeight(dividerHeight);  //分隔线高度
            lv_planet.setPadding(0, 0, 0, 0);   //设置四周空白
            lv_planet.setBackgroundColor(Color.TRANSPARENT);
            if (position == 0) {        //不显示分隔线（分隔线高度为0）
                lv_planet.setDividerHeight(0);
            } else if (position == 1) {     //不显示分隔线（分隔线为null）
                lv_planet.setDivider(null);     //，指定绘制图形，如果设置为null，取消分隔线
                lv_planet.setDividerHeight(dividerHeight);
            } else if (position == 2) {     //只显示内部分隔线（先设置分隔线高度）
                lv_planet.setDividerHeight(dividerHeight);
                lv_planet.setDivider(drawable);
            } else if (position == 3) {     //只显示内部分隔线（后设置分隔线高度
                lv_planet.setDivider(drawable);
                lv_planet.setDividerHeight(dividerHeight);
            } else if (position == 4) {    //显示底部分隔线（高度为wrap_content)
                lv_planet.setFooterDividersEnabled(true);
            } else if (position == 5) {     //显示底部分隔线（高度是match_parent)
                params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1);
                lv_planet.setFooterDividersEnabled(true);
            } else if (position == 6) {     //显示顶部分隔线（别瞎折腾，显示不了）
                params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0 ,1);
                lv_planet.setFooterDividersEnabled(true);
                lv_planet.setHeaderDividersEnabled(true);
            } else if (position == 7) {     //显示全部分隔线（padding大法）
                lv_planet.setDivider(null);
                lv_planet.setDividerHeight(dividerHeight);
                lv_planet.setPadding(0, dividerHeight, 0, dividerHeight);
                lv_planet.setBackgroundDrawable(drawable);
            }
            lv_planet.setLayoutParams(params);      //设置布局参数
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
