package com.example.middle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpinnerIconActivity extends AppCompatActivity {
    private int[] iconArray = {R.drawable.shuixing, R.drawable.jinxing, R.drawable.diqiu,
            R.drawable.huoxing, R.drawable.muxing, R.drawable.tuxing};
    private  String[] starArray = {"水星", "金星", "地球", "火星", "木星", "土星"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_icon);
        initSpinnerForSimpleAdapter();
    }

    private void initSpinnerForSimpleAdapter() {
        //声明一个映像对象的队列，用于保存行星的图标与名称配对信息
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < iconArray.length; i++) {
            Map<String, Object>  item = new HashMap<String, Object>();
            item.put("icon", iconArray[i]);
            item.put("name", starArray[i]);
            list.add(item);
        }
        SimpleAdapter starAdapter = new SimpleAdapter(this, list, R.layout.item_simple, new String[]{
                "icon", "name"}, new int[]{R.id.iv_icon, R.id.tv_name});
        starAdapter.setDropDownViewResource(R.layout.item_simple);
        Spinner sp = findViewById(R.id.sp_icon);
        sp.setPrompt("请选择行星");
        sp.setAdapter(starAdapter);
        sp.setSelection(0);
        sp.setOnItemSelectedListener(new MySelectedListener());
    }

    class MySelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(SpinnerIconActivity.this, "您选择的是" + starArray[position], Toast.LENGTH_LONG).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
