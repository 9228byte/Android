package com.example.storage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Map;

public class ShareReadActivity extends AppCompatActivity {
    private TextView tv_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_read);
        tv_share = findViewById(R.id.tv_share);
        readSharePreferences();
    }

    private void readSharePreferences() {
        SharedPreferences shared = getSharedPreferences("share", MODE_PRIVATE);
        String desc = "共享参数信息如下：";
        //获取共享参数中保存的所有映射配对信息
        Map<String, Object> mapParam = (Map<String, Object>) shared.getAll();
        //遍历该映射对象，并将配对信息形成描述文字
        for (Map.Entry<String ,Object> item_map : mapParam.entrySet()) {
            String key = item_map.getKey();
            Object value = item_map.getValue();
            if (value instanceof  String) {
                desc = String.format("%s\n  %s的取值为%s", desc, key, shared.getString(key,""));
            } else if (value instanceof Integer) {
                desc = String.format("%s\n  %s的取值为%d", desc, key, shared.getInt(key,0));
            } else if (value instanceof  Float) {
                desc = String.format("%s\n  %s的取值为%f", desc, key, shared.getFloat(key,0.0f));
            } else if (value instanceof Boolean) {
                desc = String.format("%s\n  %s的取值为%b", desc, key, shared.getBoolean(key,false));
            } else if (value instanceof Long) {
                desc = String.format("%s\n  %s的取值为%d", desc, key, shared.getLong(key,0L));
            } else {
                desc = String.format("%s\n  %s参数%s的取值为未知类型", desc, key, shared.getString(key,""));
            }
        }
        if (mapParam.size() <= 0) {
            desc = "共享参数中保存的信息为空";
        }
        tv_share.setText(desc);
    }
}
