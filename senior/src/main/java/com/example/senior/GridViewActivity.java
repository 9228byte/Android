package com.example.senior;

import android.bluetooth.le.AdvertiseData;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import com.example.senior.adapter.PlanetListAdapter;
import com.example.senior.bean.Planet;
import com.example.senior.util.Utils;

import java.util.ArrayList;

public class GridViewActivity extends AppCompatActivity {
    private final static String TAG = "GridViewActivity";
    private GridView gv_planet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        ArrayList<Planet> planetList = Planet.getDefaultList();
        //构建行星适配器
        PlanetListAdapter adapter = new PlanetListAdapter(this, planetList);
        //从布局视图中获取名叫gv_plant的网格视图
        gv_planet = findViewById(R.id.gv_planet);
        gv_planet.setAdapter(adapter);      //设置适配器
        gv_planet.setOnItemClickListener(adapter);      //点击适配器，在适配器类中已实现该方法
        gv_planet.setOnItemLongClickListener(adapter);      //长按适配器,在适配器类中已经实现该方法
        initDividerSpinner();
    }

    private String[] dividerArray = {
            "不显示分隔线",
            "只显示内部分隔线(NO_STRETCH)",
            "只显示内部分隔线(CULMN_WIDTH)",
            "只显示内部分隔线(STRETCH_SPACING)",
            "只显示内部分隔线(SPACING_UNIFORM)",
            "显示全部分隔线(看我用padding大法)"
    };

    //选择分隔线类型
    private void initDividerSpinner() {
        ArrayAdapter<String> dividerAdapter = new ArrayAdapter<String >(this,
                R.layout.item_select, dividerArray);
        Spinner sp_grid = findViewById(R.id.sp_grid);
        sp_grid.setPrompt("请选择分隔线显示方式");
        sp_grid.setAdapter(dividerAdapter);
        sp_grid.setOnItemSelectedListener(new DeviderSelectedListener());
        sp_grid.setSelection(0);
    }

    class DeviderSelectedListener implements OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int dividerPad = Utils.dip2px(GridViewActivity.this, 2);        //定义而间隔宽度为2dp
            gv_planet.setBackgroundColor(Color.RED);
            gv_planet.setHorizontalSpacing(dividerPad);     //设置水平方向空白
            gv_planet.setVerticalSpacing(dividerPad);       //设置垂直方向空白
            gv_planet.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);        //设置拉伸模式
            gv_planet.setColumnWidth(250);      //设置每列宽度为250
            gv_planet.setPadding(0, 0 , 0 , 0);     //设置四周空白
            if (position == 0) {        //不显示分隔线
                gv_planet.setBackgroundColor(Color.WHITE);
                gv_planet.setHorizontalSpacing(0);
                gv_planet.setVerticalSpacing(0);
            } else if (position == 1) {     //只显示内部分隔线
                gv_planet.setStretchMode(GridView.NO_STRETCH);
            } else if (position == 2) {
                gv_planet.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
            } else if (position == 3) {
                gv_planet.setStretchMode(GridView.STRETCH_SPACING);
            } else if (position ==4) {
                gv_planet.setStretchMode(GridView.STRETCH_SPACING_UNIFORM);
            } else if (position ==5) {          //显示全部分隔线padding的方法
                gv_planet.setPadding(dividerPad, dividerPad, dividerPad, dividerPad);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
