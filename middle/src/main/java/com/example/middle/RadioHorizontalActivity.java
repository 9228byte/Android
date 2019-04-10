package com.example.middle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class RadioHorizontalActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private TextView tv_sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_horizontal);
        tv_sex = findViewById(R.id.tv_sex);
        RadioGroup rg_sex = findViewById(R.id.rg_sex);
        rg_sex.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rb_male) {
            tv_sex.setText("你是一个帅气的男孩");
        } else if (checkedId == R.id.rb_female) {
            tv_sex.setText("你是一个漂亮的女孩");
        }
    }

    class RadioListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            Toast.makeText(RadioHorizontalActivity.this, "您选中了控件"+checkedId,Toast.LENGTH_LONG).show();
        }
    }
}
