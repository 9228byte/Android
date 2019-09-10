package com.example.network.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.network.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class JsonParseActivity extends AppCompatActivity implements OnClickListener {
    private TextView tv_json;
    private String mJsonStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_parse);
        tv_json = findViewById(R.id.tv_json);
        findViewById(R.id.btn_construct_json).setOnClickListener(this);
        findViewById(R.id.btn_parse_json).setOnClickListener(this);
        findViewById(R.id.btn_traverse_json).setOnClickListener(this);
        mJsonStr = getJSonStr();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_construct_json) {
            tv_json.setText(mJsonStr);
        } else if (v.getId() == R.id.btn_parse_json) {
            tv_json.setText(parserJson(mJsonStr));
        } else if (v.getId() == R.id.btn_traverse_json) {
            tv_json.setText(traverseJson(mJsonStr));
        }
    }

    //获取手动构造的json字符串
    private String getJSonStr() {
        String str = "";
        JSONObject obj = new JSONObject();
        try {
            obj.put("name", "address");
            JSONArray array = new JSONArray();
            for (int i = 0; i < 3; i++){
                JSONObject item = new JSONObject();
                item.put("item", "第" + (i+ 1) + "个元素");
                array.put(item);
            }
            obj.put("list", array);
            obj.put("count", array.length());
            obj.put("desc", "这是测试字符串");
            str = obj.toString();
            str = obj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return str;
    }


    //解析json串内部的各个参数
    private String parserJson(String jsonStr) {
        String result = "";
        try {
            JSONObject obj = new JSONObject(jsonStr);
            String name = obj.getString("name");
            String desc = obj.getString("desc");
            int count = obj.getInt("count");
            result = String.format("%sname=%s\n" ,result, name);
            result = String.format("%sdesc=%s\n" ,result, desc);
            result = String.format("%scount=%d\n" ,result, count);
            JSONArray listArray = obj.getJSONArray("list");
            for (int i = 0; i < listArray.length(); i++) {
                JSONObject list_item = listArray.getJSONObject(i);
                String item  = list_item.getString("item");
                result = String.format("%s\titem=%s\n", result, item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    //遍历json串保存的键值对信息先
    private String traverseJson(String jsonStr) {
        String result = "";
        try {
            JSONObject obj = new JSONObject(jsonStr);
            Iterator<String> it = obj.keys();
            while (it.hasNext()) {
                String key = it.next();
                String value = obj.getString(key);
                result = String.format("%skey=%s, value=%s\n", result, key, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
