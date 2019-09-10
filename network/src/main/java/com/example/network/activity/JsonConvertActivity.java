package com.example.network.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.network.R;
import com.example.network.bean.UserInfo;
import com.google.gson.Gson;

/**
 * JsonConvertActivity
 *
 * @author lao
 * @date 2019/5/18
 */

public class JsonConvertActivity extends AppCompatActivity implements OnClickListener {
    private TextView tv_json;
    private UserInfo mUser = new UserInfo();
    private String mJsonStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_convert);
        mUser.name = "李四";
        mUser.age = 25;
        mUser.height = 175L;
        mUser.weight = 50.0f;
        mUser.married = true;
        mJsonStr = new Gson().toJson(mUser);
        tv_json = findViewById(R.id.tv_json);
        findViewById(R.id.btn_origin_json).setOnClickListener(this);
        findViewById(R.id.btn_convert_json).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_origin_json) {
            mJsonStr = new Gson().toJson(mUser);
            tv_json.setText("jso串内容如下:\n" + mJsonStr);
        } else if (v.getId() == R.id.btn_convert_json) {
            UserInfo newUser = new Gson().fromJson(mJsonStr, UserInfo.class);
            String desc = String.format("\n\t姓名=%s\n\t年龄=%d\n\t身高=%d\n\t体重=%f\n\t婚否=%b",
                    newUser.name, newUser.age, newUser.height, newUser.weight, newUser.married);
            tv_json.setText("从json串解析而来的用户信息：" + desc);
        }
    }
}
