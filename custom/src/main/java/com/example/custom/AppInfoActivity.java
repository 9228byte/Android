package com.example.custom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.custom.adapter.AppInfoAdapter;
import com.example.custom.bean.AppInfo;
import com.example.custom.util.AppUtil;

import java.util.ArrayList;

/**
 * AppInfoActivity
 *
 * @author lao
 * @date 2019/4/22
 */

public class AppInfoActivity extends AppCompatActivity {
    private static final String TAG = "AppInfoActivity";
    private ListView lv_appinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);
        lv_appinfo = findViewById(R.id.lv_appinfo);
        initTypeSpinner();
    }

    private void initTypeSpinner() {
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this,
                R.layout.item_select, typeArray);
        Spinner sp_list = findViewById(R.id.sp_type);
        sp_list.setPrompt("请选择应用类型");
        sp_list.setAdapter(typeAdapter);
        sp_list.setOnItemSelectedListener(new TypeSelectedListener());
        sp_list.setSelection(0);
    }

    private String typeArray[] = {"所有应用", "联网应用"};
    class TypeSelectedListener implements OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ArrayList<AppInfo> appinfoList = AppUtil.getAppInfo(AppInfoActivity.this, position);
            AppInfoAdapter adapter = new AppInfoAdapter(AppInfoActivity.this, appinfoList);
            lv_appinfo.setAdapter(adapter);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

}
