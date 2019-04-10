package com.example.storage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.storage.bean.UserInfo;
import com.example.storage.database.UserDBHelper;

import java.util.ArrayList;


public class SQLiteReadActivity extends AppCompatActivity implements OnClickListener{
    private UserDBHelper mHpler;
    private TextView tv_sqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_read);
        tv_sqlite = findViewById(R.id.tv_sqlite);
        findViewById(R.id.btn_delete).setOnClickListener(this);
    }

    //读取数据库中保存的所有记录
    private void readSQLite() {
        if (mHpler == null) {
            showToast("数据库为空");
            return;
        }
        //执行数据库帮助器的查询操作
        ArrayList<UserInfo> userArray = mHpler.qurey("1=1");
        String desc = String.format("数据库查询到%s条记录，详情如下：", userArray.size());
        for (int i = 0; i < userArray.size(); i++) {
            UserInfo userInfo = userArray.get(i);
            desc = String.format("%s\n 第%s条记录如下：", desc, i + 1);
            desc = String.format("%s\n 姓名为%s", desc, userInfo.name);
            desc = String.format("%s\n 年龄为%s", desc, userInfo.age);
            desc = String.format("%s\n 身高为%s", desc, userInfo.height);
            desc = String.format("%s\n 体重为%s", desc, userInfo.weight);
            desc = String.format("%s\n 更新时间为%s", desc, userInfo.update_time);
        }
        if (userArray.size() <= 0) {
            desc = "数据库查询记录为空";
        }
        tv_sqlite.setText(desc);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //获取数据库帮助类的实例
        mHpler = UserDBHelper.getInstance(this, 2);
        //打开数据库帮助器实例
        mHpler.openReadLink();
        readSQLite();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //关闭数据库连接
        mHpler.closeLink();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_delete) {
            //关闭数据库连接
            mHpler.closeLink();
            //打开数据库帮助器的写连接
            mHpler.openWriteLink();
            //删除所有记录
            mHpler.deleteAll();
            //关闭数据库连接
            mHpler.closeLink();
            //打开数据库帮助器读连接
            mHpler.openReadLink();
            readSQLite();
        }
    }

    private  void showToast(String desc) {
        Toast.makeText(this, desc, Toast.LENGTH_SHORT).show();
    }
}
