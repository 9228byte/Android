package com.example.senior;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.senior.adapter.PlanetListAdapter;
import com.example.senior.bean.Planet;

import java.util.ArrayList;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class BaseAdapterActivity extends AppCompatActivity {
    private ArrayList<Planet> planetList;       //声明一个行星队列

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_adapter);
        initPlanetSpinner();
    }

    private void initPlanetSpinner() {
        //获取默认的行星队列
        planetList = Planet.getDefaultList();
        //构建一个行星列表的适配器
        PlanetListAdapter adapter = new PlanetListAdapter(this, planetList);
        //从布局文件中获取下拉框
        Spinner sp = findViewById(R.id.sp_planet);
        sp.setPrompt("请选择行星");
        sp.setAdapter(adapter);
        sp.setSelection(0);
        sp.setOnItemSelectedListener(new MyselectedListener());
    }

    private class MyselectedListener implements OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(BaseAdapterActivity.this, "您的选择是" + planetList.get(position).name, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
