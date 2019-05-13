package com.example.group;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

/**
 * AppbarNestedActivity
 *
 * @author lao
 * @date 2019/5/11
 */

public class AppbarNestedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appbar_nested);
        Toolbar tl_title = findViewById(R.id.tl_title);
        setSupportActionBar(tl_title);
    }
}
